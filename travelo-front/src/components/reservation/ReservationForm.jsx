import {useState} from 'react';
import SeatSelectionStep from './steps/SeatSelectionStep';
import DiscountSelectionStep from './steps/DiscountSelectionStep';
import {useParams} from "react-router-dom";
import {useCreateReservation} from "./hooks/useCreateReservation.jsx";
import SummaryStep from "./steps/SummaryStep.jsx";

const ReservationForm = () => {
    const {tripId} = useParams();
    const [step, setStep] = useState(1);
    const [formData, setFormData] = useState({
        seatIds: [],
        tripId: tripId ? Number(tripId) : null,
        promeCode: '',
        loyaltyPoints: 0,
        total: 0,
    });

    const [reservationResponse, setReservationResponse] = useState(null);
    const reservationMutation = useCreateReservation();

    const updateFormData = (newData) => {
        setFormData(prev => ({...prev, ...newData}));
    };

    const handleDiscountStepSubmit = () => {
        reservationMutation.mutate(formData, {
            onSuccess: (responseDataDTo) => {
                setReservationResponse(responseDataDTo);
                setStep(3);
            },
            onError: (err) => {
                alert(err?.response?.data?.error?.message);
            }
        });
    };

    return (
        <div className="multi-step-container">

            {step === 1 && (
                <SeatSelectionStep
                    tripId={tripId}
                    formData={formData}
                    updateFormData={updateFormData}
                    onNext={() => setStep(2)}
                />
            )}
            {step === 2 && (
                <DiscountSelectionStep
                    formData={formData}
                    updateFormData={updateFormData}
                    onBack={() => setStep(1)}
                    onNext={handleDiscountStepSubmit}
                />
            )}
            {step === 3 && reservationResponse && (
                <SummaryStep
                    reservationData={reservationResponse}
                    onConfirm={() => {
                        alert('Proceeding to payment gateway');
                    }}
                />
            )}
        </div>
    );
}

export default ReservationForm;