import {useState} from 'react';
import SeatSelectionStep from './steps/SeatSelectionStep';
import {useParams} from "react-router-dom";

const ReservationForm = () => {
    const {tripId} = useParams();
    const [step, setStep] = useState(1);
    const [formData, setFormData] = useState({
        seatIds: [],
        tripId: '',
        promeCode: '',
        loyaltyPoints: 0,
    });

    const updateFormData = (newData) => {
        setFormData(prev => ({...prev, ...newData}));
    };

    const nextStep = () => setStep(prev => prev + 1);
    // const prevStep = () => setStep(prev => prev - 1);
    //
    // const handleCheckout = async () => {
    //     const expirationTime = new Date(Date.now() + 10 * 60 * 1000);
    //
    //     const payload = {
    //         ...formData,
    //         tripId,
    //         expiresAt: expirationTime.toISOString(),
    //     };
    //
    //     try {
    //         // API call to record info, update seat status, and adjust availability count
    //         // await api.post('/api/reservations', payload);
    //         // window.location.href = paymentGatewayUrl;
    //         alert(`Redirecting to payment gateway. Seats locked until: ${expirationTime.toLocaleTimeString()}`);
    //     } catch (error) {
    //         console.error("Reservation failed", error);
    //     }
    // };

    return (
        <div className="multi-step-container">
            <div className="steps-header">Step {step} of 4</div>

            {step === 1 && (
                <SeatSelectionStep
                    tripId={tripId}
                    formData={formData}
                    updateFormData={updateFormData}
                    onNext={nextStep}
                />
            )}
            {/*{step === 2 && (*/}
            {/*    <CustomerInfo*/}
            {/*        formData={formData}*/}
            {/*        updateFormData={updateFormData}*/}
            {/*        onNext={nextStep}*/}
            {/*        onBack={prevStep}*/}
            {/*    />*/}
            {/*)}*/}
            {/*{step === 3 && (*/}
            {/*    <Discounts*/}
            {/*        formData={formData}*/}
            {/*        updateFormData={updateFormData}*/}
            {/*        onNext={nextStep}*/}
            {/*        onBack={prevStep}*/}
            {/*    />*/}
            {/*)}*/}
            {/*{step === 4 && (*/}
            {/*    <Summary*/}
            {/*        formData={formData}*/}
            {/*        onCheckout={handleCheckout}*/}
            {/*        onBack={prevStep}*/}
            {/*    />*/}
            {/*)}*/}
        </div>
    );
}

export default ReservationForm;