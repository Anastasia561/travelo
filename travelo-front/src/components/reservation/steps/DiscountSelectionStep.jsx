import {useState, useEffect} from 'react';
import {usePoints} from "../hooks/usePoints.jsx";
import {useValidatePromoCode} from "../hooks/useValidatePromeCode.jsx";

export default function DiscountSelection({formData, updateFormData, onNext, onBack}) {

    const [promoInput, setPromoInput] = useState(formData.promoCode || "");
    const [promoError, setPromoError] = useState("");
    const [promoSuccess, setPromoSuccess] = useState(!!formData.promoCode);

    const [showLoyaltyPrompt, setShowLoyaltyPrompt] = useState(formData.useLoyaltyPoints !== undefined && formData.useLoyaltyPoints !== null);
    const [loyaltyOption, setLoyaltyOption] = useState(formData.useLoyaltyPoints ?? null);

    const {data: userAvailablePoints} = usePoints();
    const pointsAvailable = userAvailablePoints ?? 0;
    const plnPerPoint = 0.01;
    const loyaltyReductionValue = pointsAvailable * plnPerPoint;

    const baseTicketTotal = formData.total || 0.00;

    const [appliedDiscount, setAppliedDiscount] = useState(formData.appliedDiscountAmount || 0.00);
    const promoMutation = useValidatePromoCode();

    useEffect(() => {
        updateFormData({
            promeCode: promoSuccess ? promoInput.trim() : "",
            loyaltyPoints: loyaltyOption === true ? pointsAvailable : 0
        });
    }, [promoSuccess, promoInput, loyaltyOption, appliedDiscount]);

    const handleApplyPromoCode = () => {
        const cleanedCode = promoInput.trim();

        if (!cleanedCode) {
            setPromoError("Please enter a code or proceed below if you do not have one.");
            setShowLoyaltyPrompt(true);
            return;
        }

        promoMutation.mutate(cleanedCode, {
            onSuccess: (discountDto) => {
                setPromoError("");
                setPromoSuccess(true);
                setLoyaltyOption(false);

                setAppliedDiscount(discountDto.amount || 0.00);
                setShowLoyaltyPrompt(false);
            },
            onError: (error) => {
                setPromoSuccess(false);
                setAppliedDiscount(0.00);
                const serverErrorMessage = error.error?.message;

                setPromoError(serverErrorMessage);
                setShowLoyaltyPrompt(true);
            }
        });
    };

    const handleLoyaltyChoice = (choice) => {
        setLoyaltyOption(choice);
        if (choice === true) {
            setPromoInput("");
            setPromoSuccess(false);
            setPromoError("");
            setAppliedDiscount(loyaltyReductionValue);
        } else {
            setAppliedDiscount(0.00);
        }
    };

    const handleClearPromo = () => {
        setPromoInput("");
        setPromoSuccess(false);
        setAppliedDiscount(0.00);
        setLoyaltyOption(null);
    };

    const handleCheckoutClick = () => {
        if (promoSuccess) {
            onNext();
            return;
        }

        if (!showLoyaltyPrompt) {
            setShowLoyaltyPrompt(true);
            setPromoError("Please review the loyalty account status below before continuing.");
            return;
        }

        if (loyaltyOption === null) {
            setPromoError("You must acknowledge your loyalty selection option before proceeding.");
            return;
        }

        onNext();
    };

    const computedDiscountAmount = promoSuccess
        ? (appliedDiscount * baseTicketTotal)
        : appliedDiscount;

    const finalTotal = Math.max(0, baseTicketTotal - computedDiscountAmount);

    return (
        <div className="discount-selection-wrapper">
            <div className="text-center mb-4">
                <h3 className="fw-bold text-dark">Select Your Savings</h3>
                <p className="text-muted small">Apply a promotional discount code or leverage your customer loyalty
                    account points.</p>
            </div>

            <div className="row g-4">
                <div className="col-md-7">
                    <div className="card p-4 bg-light border shadow-sm mb-4">
                        <h5 className="mb-3 fw-bold">Option 1: Promo Code</h5>

                        <div className="mb-3">
                            <label className="form-label small fw-bold text-muted">ENTER PROMOCODE</label>
                            <div className="input-group">
                                <input
                                    type="text"
                                    className={`form-control form-control-lg ${promoError ? 'is-invalid' : ''} ${promoSuccess ? 'is-valid' : ''}`}
                                    placeholder="e.g. TRAVELO2026"
                                    value={promoInput}
                                    onChange={(e) => {
                                        setPromoInput(e.target.value);
                                        setPromoError("");
                                    }}
                                    disabled={promoSuccess || loyaltyOption === true}
                                />
                                {promoSuccess ? (
                                    <button className="btn btn-outline-danger" type="button" onClick={handleClearPromo}>
                                        Remove
                                    </button>
                                ) : (
                                    <button
                                        className="btn btn-primary px-4 fw-bold"
                                        type="button"
                                        onClick={handleApplyPromoCode}
                                        disabled={loyaltyOption === true || promoMutation.isPending}
                                    >
                                        {promoMutation.isPending ? "Applying..." : "Apply"}
                                    </button>
                                )}
                            </div>

                            {promoError && (
                                <div className="invalid-feedback d-block mt-2 fw-bold">
                                    {promoError}
                                </div>
                            )}

                            {promoSuccess && (
                                <div className="valid-feedback d-block mt-2 fw-bold text-success">
                                    Code applied successfully!
                                </div>
                            )}
                        </div>
                    </div>

                    {showLoyaltyPrompt && (
                        <div
                            className={`card p-4 border shadow-sm transition-all ${
                                loyaltyOption === true || (pointsAvailable === 0 && loyaltyOption === false)
                                    ? 'border-success bg-success bg-opacity-10'
                                    : 'bg-light'
                            }`}
                        >
                            <div className="d-flex align-items-start gap-3">
                                <div className="flex-grow-1">
                                    <h5 className="mb-3 fw-bold">Option 2: Loyalty Points Balance</h5>

                                    {pointsAvailable > 0 ? (
                                        <p className="text-muted small mb-3">
                                            You have <strong>{pointsAvailable.toLocaleString()}</strong> active points
                                            available.
                                            Deducting points yields a conversion scale reduction of <span
                                            className="fw-bold text-dark">0.01 PLN</span> per point.
                                        </p>
                                    ) : (
                                        <p className="text-muted small mb-3">
                                            Your current loyalty balance is <strong>0 points</strong>. You can
                                            accumulate points automatically
                                            on future traveled itineraries to redeem discounts later at a rate of 0.01
                                            PLN per point.
                                        </p>
                                    )}

                                    <div className="d-flex align-items-center justify-content-between flex-wrap gap-2">
                                        <span className="small fw-bold text-muted text-uppercase">
                                            {loyaltyOption === null ? "Please acknowledge:" : "Status Summary:"}
                                        </span>

                                        {pointsAvailable > 0 ? (
                                            <div className="btn-group shadow-sm" role="group">
                                                <button
                                                    type="button"
                                                    className={`btn btn-sm px-4 fw-bold ${loyaltyOption === true ? 'btn-success' : 'btn-outline-success'}`}
                                                    onClick={() => handleLoyaltyChoice(true)}
                                                    disabled={promoSuccess}
                                                >
                                                    Yes (-{loyaltyReductionValue.toFixed(2)} PLN)
                                                </button>
                                                <button
                                                    type="button"
                                                    className={`btn btn-sm px-4 fw-bold ${loyaltyOption === false ? 'btn-secondary' : 'btn-outline-secondary'}`}
                                                    onClick={() => handleLoyaltyChoice(false)}
                                                    disabled={promoSuccess}
                                                >
                                                    No, Don't Use Points
                                                </button>
                                            </div>
                                        ) : (
                                            <button
                                                type="button"
                                                className={`btn btn-sm px-4 fw-bold shadow-sm ${loyaltyOption === false ? 'btn-success' : 'btn-outline-primary'}`}
                                                onClick={() => handleLoyaltyChoice(false)}
                                                disabled={promoSuccess}
                                            >
                                                {loyaltyOption === false ? "✓ Agreed & Verified" : "Acknowledge & Continue"}
                                            </button>
                                        )}
                                    </div>
                                </div>
                            </div>
                        </div>
                    )}
                </div>

                <div className="col-md-5">
                    <div className="card p-3 bg-white border border-secondary border-opacity-25 shadow-sm sticky-top"
                         style={{top: '20px'}}>
                        <h4 className="fw-bold pb-2 mb-3 border-bottom text-primary" style={{fontSize: '1.1rem'}}>
                            Reservation Summary
                        </h4>

                        <div className="small text-muted mb-3 lh-sm">
                            <div className="mb-1"><strong>Selected seats
                                quantity:</strong> {formData.selectedSeats?.length || 0}</div>
                        </div>

                        <hr className="my-2 text-muted opacity-25"/>

                        <div className="d-flex justify-content-between my-2 small">
                            <span className="text-muted">Base Tickets Price:</span>
                            <span className="fw-semibold">{baseTicketTotal.toFixed(2)} PLN</span>
                        </div>

                        {appliedDiscount > 0 && (
                            <div className="d-flex justify-content-between my-2 small text-danger fw-bold">
                                <span>
                                    {promoSuccess ? `Promo Code Deduction (${promoInput.toUpperCase()}):` : 'Loyalty Points Applied:'}
                                </span>
                                <span>-{computedDiscountAmount.toFixed(2)} PLN</span>
                            </div>
                        )}

                        <hr className="my-2 border-dark"/>

                        <div className="d-flex justify-content-between align-items-center mb-4">
                            <span className="fw-bold text-dark">Total Due:</span>
                            <span className="fw-extrabold fs-4 text-primary">{finalTotal.toFixed(2)} PLN</span>
                        </div>
                    </div>
                </div>
            </div>

            <div className="d-flex justify-content-between mt-5 border-top pt-3">
                <button
                    type="button"
                    className="btn btn-outline-secondary btn-lg px-4 shadow-sm"
                    onClick={onBack}
                >
                    ← Back to Layout
                </button>
                <button
                    type="button"
                    className={`btn btn-lg px-5 shadow text-uppercase fw-bold ${(!promoSuccess && showLoyaltyPrompt && loyaltyOption === null) ? 'btn-secondary opacity-75' : 'btn-primary'}`}
                    onClick={handleCheckoutClick}
                >
                    Go to Checkout →
                </button>
            </div>
        </div>
    );
}