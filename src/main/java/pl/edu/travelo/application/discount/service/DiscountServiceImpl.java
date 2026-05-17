package pl.edu.travelo.application.discount.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.travelo.application.discount.dto.DiscountResponseDto;
import pl.edu.travelo.application.discount.mapper.DiscountMapper;
import pl.edu.travelo.application.discount.repository.DiscountRepository;
import pl.edu.travelo.application.discount.repository.LimitedDiscountRepository;
import pl.edu.travelo.application.discount.repository.RegularDiscountRepository;
import pl.edu.travelo.domain.enums.AgeGroup;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final LimitedDiscountRepository limitedDiscountRepository;
    private final RegularDiscountRepository regularDiscountRepository;
    private final DiscountMapper discountMapper;

    public DiscountServiceImpl(DiscountRepository discountRepository,
                               LimitedDiscountRepository limitedDiscountRepository,
                               RegularDiscountRepository regularDiscountRepository,
                               DiscountMapper discountMapper) {
        this.discountRepository = discountRepository;
        this.limitedDiscountRepository = limitedDiscountRepository;
        this.regularDiscountRepository = regularDiscountRepository;
        this.discountMapper = discountMapper;
    }

    @Override
    public boolean validateByPromeCode(String promeCode) {
        if (discountRepository.findDiscountByPromeCode(promeCode).isEmpty()) {
            throw new EntityNotFoundException("Discount not found");
        }
        return true;
    }

    @Override
    public Set<DiscountResponseDto> getRelevantDiscounts(int age) {
        LocalDateTime now = LocalDateTime.now();
        AgeGroup ageGroup = determineAgeGroup(age);

        return Stream.concat(
                limitedDiscountRepository.findAllByDateOrAge(now, ageGroup)
                        .stream().map(discountMapper::toDtoFromLimited),
                regularDiscountRepository.findAllByDayOrAge(now.toLocalDate().getDayOfWeek(), ageGroup)
                        .stream().map(discountMapper::toDtoFromRegular)
        ).collect(Collectors.toSet());
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
}
