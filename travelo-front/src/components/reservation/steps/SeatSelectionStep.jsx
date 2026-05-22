import {useState, useEffect} from 'react';
import {useTripInfo} from "../hooks/useTripInfo.jsx";

export default function SeatSelection({formData, updateFormData, onNext}) {
    const tripId = formData.tripId;
    const {data, isLoading, isError} = useTripInfo(tripId);
    const [selectedLocalSeats, setSelectedLocalSeats] = useState(formData.seatIds || []);

    useEffect(() => {
        updateFormData({seatIds: selectedLocalSeats});
    }, [selectedLocalSeats]);

    if (isLoading) {
        return (
            <div className="text-center py-5">
                <div className="spinner-border text-primary" role="status"></div>
                <p className="mt-2 text-muted">Loading coach layout...</p>
            </div>
        );
    }

    if (isError || !data) {
        return <div className="alert alert-danger m-4">Failed to load trip info</div>;
    }

    const vehicleInfo = data.vehicleInfo;
    const currency = data.currency || "PLN";

    const sortedSeats = [...vehicleInfo.seats].sort((a, b) => {
        if (a.row !== b.row) return a.row - b.row;
        return a.seatNumber - b.seatNumber;
    });

    const handleSeatClick = (seat) => {
        if (seat.isBooked) return;

        setSelectedLocalSeats((prevSelected) => {
            if (prevSelected.includes(seat.id)) {
                return prevSelected.filter(id => id !== seat.id);
            } else {
                return [...prevSelected, seat.id];
            }
        });
    };

    const currentTotal = selectedLocalSeats.reduce((sum, seatId) => {
        const foundSeat = vehicleInfo.seats.find(s => s.id === seatId);
        return sum + (foundSeat ? foundSeat.price : 0);
    }, data.price);

    const handleProceed = () => {
        updateFormData({
            seatIds: selectedLocalSeats,
            total: currentTotal
        });
        onNext();
    };

    return (
        <div className="seat-selection-wrapper">
            <div className="text-center mb-4">
                <h3 className="fw-bold text-dark">Select Your Seats</h3>
                <p className="text-muted mb-4">
                    Vehicle Type: <strong>{vehicleInfo.type.replace('_', ' ')} (#{vehicleInfo.number})</strong>
                </p>
            </div>

            <div className="d-flex flex-wrap gap-3 mb-4 p-3 bg-light rounded small border">
                <div className="d-flex align-items-center gap-2">
                    <span className="d-inline-block rounded bg-primary" style={{width: '20px', height: '20px'}}></span>
                    <span>Available</span>
                </div>
                <div className="d-flex align-items-center gap-2">
                    <span className="d-inline-block rounded bg-success" style={{width: '20px', height: '20px'}}></span>
                    <span>Your Selection</span>
                </div>
                <div className="d-flex align-items-center gap-2">
                    <span className="d-inline-block rounded bg-secondary opacity-50"
                          style={{width: '20px', height: '20px'}}></span>
                    <span>Already Reserved</span>
                </div>
            </div>

            <div className="mx-auto" style={{width: '80%', maxWidth: '900px'}}>

                <div className="d-flex align-items-stretch gap-2 mb-4">

                    <div
                        className="bg-dark text-white rounded-start d-flex align-items-center justify-content-center px-2 small fw-bold"
                        style={{
                            writingMode: 'vertical-lr',
                            transform: 'rotate(180deg)',
                            letterSpacing: '2px',
                            whiteSpace: 'nowrap'
                        }}
                    >
                        FRONT OF BUS / DRIVER ▲
                    </div>

                    <div
                        className="d-grid gap-2 p-3 bg-light border border-start-0 rounded-end flex-grow-1"
                        style={{
                            gridTemplateRows: `repeat(${vehicleInfo.rowWidth || 4}, 1fr)`,
                            gridAutoFlow: 'column',
                            overflowX: 'auto',
                            paddingBottom: '10px'
                        }}
                    >
                        {sortedSeats.map((seat) => {
                            const isSelected = selectedLocalSeats.includes(seat.id);

                            let btnStyle = "btn-primary";
                            if (seat.isBooked) btnStyle = "btn-secondary opacity-25 text-decoration-line-through";
                            else if (isSelected) btnStyle = "btn-success";

                            const isBelowAisleSide = seat.seatNumber === 3;

                            return (
                                <button
                                    key={seat.id}
                                    type="button"
                                    className={`btn p-2 d-flex flex-column align-items-center justify-content-center fw-bold ${btnStyle}`}
                                    style={{
                                        height: '55px',
                                        minWidth: '55px',
                                        marginTop: isBelowAisleSide ? '16px' : '0px'
                                    }}
                                    onClick={() => handleSeatClick(seat)}
                                    disabled={seat.isBooked}
                                    title={`Row ${seat.row} Seat ${seat.seatNumber}`}
                                >
                                    <span style={{fontSize: '10px', opacity: 0.8}}>R{seat.row}</span>
                                    <span style={{fontSize: '13px'}}>#{seat.seatNumber}</span>
                                </button>
                            );
                        })}
                    </div>
                </div>

            </div>

            <div className="alert alert-info py-2 px-3 shadow-sm d-flex justify-content-between align-items-center">
                <div>
                    Selected Seats: {selectedLocalSeats.length > 0 ? (
                    <strong className="text-success">
                        {selectedLocalSeats
                            .map(seatId => {
                                const seat = vehicleInfo.seats.find(s => s.id === seatId);
                                return seat ? `#${seat.seatNumber}-R${seat.row}` : null;
                            })
                            .filter(Boolean)
                            .join(', ')
                        }
                    </strong>
                ) : (
                    <em className="text-muted">None</em>
                )}
                </div>
                <div className="fw-bold fs-5">
                    Total: {currentTotal.toFixed(2)} {currency}
                </div>
            </div>

            <div className="d-flex justify-content-end mt-4">
                <button
                    type="button"
                    className="btn btn-primary btn-lg px-5 shadow"
                    onClick={handleProceed}
                    disabled={selectedLocalSeats.length === 0}
                >
                    Proceed to Customer Info →
                </button>
            </div>
        </div>
    );
}