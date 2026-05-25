package pl.edu.travelo.application.discount;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.travelo.application.customer.CustomerService;
import pl.edu.travelo.application.discount.dto.DiscountResponseDto;
import pl.edu.travelo.domain.enums.AgeGroup;
import pl.edu.travelo.domain.model.Discount;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final LimitedDiscountRepository limitedDiscountRepository;
    private final RegularDiscountRepository regularDiscountRepository;
    private final DiscountMapper discountMapper;
    private final CustomerService customerService;

    public DiscountServiceImpl(DiscountRepository discountRepository,
                               LimitedDiscountRepository limitedDiscountRepository,
                               RegularDiscountRepository regularDiscountRepository,
                               DiscountMapper discountMapper,
                               CustomerService customerService) {
        this.discountRepository = discountRepository;
        this.limitedDiscountRepository = limitedDiscountRepository;
        this.regularDiscountRepository = regularDiscountRepository;
        this.discountMapper = discountMapper;
        this.customerService = customerService;
    }

    @Override
    public DiscountResponseDto getByPromeCodeRelevant(String promeCode, long customerId) {
        findByPromeCode(promeCode);

        return getRelevantDiscount(customerId).stream()
                .filter(d -> d.getPromoCode().equals(promeCode))
                .findFirst()
                .map(discountMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "This discount code is not available for your account tier"));
    }

    @Override
    public Discount findByPromeCode(String promeCode) {
        return discountRepository.findDiscountByPromeCode(promeCode)
                .orElseThrow(() -> new EntityNotFoundException("Discount not found"));
    }

    private AgeGroup determineAgeGroup(int age) {
        if (age < 18) {
            return AgeGroup.KID;
        } else if (age < 60) {
            return AgeGroup.TEENAGER;
        } else {
            return AgeGroup.SENIOR;
        }
    }

    private Set<Discount> getRelevantDiscount(long customerId) {
        LocalDate birthDate = customerService.findById(customerId).getBirthDate();

        LocalDateTime now = LocalDateTime.now();
        int age = Period.between(birthDate, now.toLocalDate()).getYears();
        AgeGroup ageGroup = determineAgeGroup(age);

        return Stream.concat(
                limitedDiscountRepository.findAllByDateOrAge(now, ageGroup).stream(),
                regularDiscountRepository.findAllByDayOrAge(now.toLocalDate().getDayOfWeek(), ageGroup).stream()
        ).collect(Collectors.toSet());
    }
}
