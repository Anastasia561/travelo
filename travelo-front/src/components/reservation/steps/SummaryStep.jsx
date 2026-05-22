import dayjs from "dayjs";

export default function SummaryStep({reservationData, onConfirm}) {
    const {
        reservationNumber,
        trip,
        seats,
        totalPrice
    } = reservationData || {};

    return (
        <div className="checkout-summary-wrapper animate-fade-in">
            <div className="text-center mb-4">
                <h3 className="fw-bold text-dark">Reservation Details</h3>
                <p className="text-muted small">Please review your itinerary and seat allocations before proceeding to
                    secure payment.</p>
            </div>

            <div className="row g-4">
                <div className="col-lg-7">
                    <div className="card shadow-sm border-0 mb-4 bg-light">
                        <div className="card-body p-4">
                            <div className="d-flex justify-content-between align-items-center mb-3">
                                <h5 className="fw-bold mb-0 text-dark">Route Details</h5>
                                <span
                                    className="badge bg-primary px-2 py-1.5">{trip?.vehicleType?.replace('_', ' ')}</span>
                            </div>

                            <div className="row g-3">
                                <div className="col-6">
                                    <label className="text-uppercase small fw-bold text-muted d-block">From</label>
                                    <span className="fs-5 fw-semibold text-dark">{trip?.startCity}</span>
                                </div>
                                <div className="col-6">
                                    <label className="text-uppercase small fw-bold text-muted d-block">To</label>
                                    <span className="fs-5 fw-semibold text-primary">{trip?.destination?.name}</span>
                                    <small className="d-block text-muted text-truncate">
                                        {trip?.destination?.cityName}, {trip?.destination?.countryName}
                                    </small>
                                </div>

                                <hr className="my-2 opacity-10"/>

                                <div className="col-6">
                                    <label className="text-uppercase small fw-bold text-muted d-block">Departure</label>
                                    <span className="small fw-medium text-dark">
                                        {dayjs(trip?.departureTime).format('DD MMM • HH:mm')}
                                    </span>
                                </div>
                                <div className="col-6">
                                    <label className="text-uppercase small fw-bold text-muted d-block">Arrival</label>
                                    <span className="small fw-medium text-dark">
                                        {dayjs(trip?.arrivalTime).format('DD MMM • HH:mm')}
                                    </span>
                                </div>

                                <hr className="my-2 opacity-10"/>

                                <div className="col-6">
                                    <label className="text-uppercase small fw-bold text-muted d-block">Vehicle
                                        ID</label>
                                    <span
                                        className="font-monospace fw-bold badge bg-secondary-subtle text-secondary-emphasis">
                                        {trip?.vehicleNumber}
                                    </span>
                                </div>

                                <div className="col-6">
                                    <label className="text-uppercase small fw-bold text-muted d-block">Reference
                                        Token</label>
                                    <span className="font-monospace text-dark small text-break fw-semibold d-block">
                                        {reservationNumber}
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div className="card shadow-sm border-0 bg-white">
                        <div className="card-body p-4">
                            <h5 className="fw-bold mb-3">Allocated Seats List</h5>
                            <div className="d-flex flex-wrap gap-2">
                                {Array.from(seats || []).map((seat, index) => (
                                    <div key={index}
                                         className="border rounded px-3 py-2 bg-light text-center min-w-[70px]">
                                        <div className="text-uppercase text-muted fw-bold"
                                             style={{fontSize: '0.65rem'}}>
                                            Row {seat.row}
                                        </div>
                                        <div className="fs-5 fw-bold text-dark">#{seat.seatNumber}</div>
                                    </div>
                                ))}
                            </div>
                        </div>
                    </div>
                </div>

                <div className="col-lg-5">
                    <div className="card border-2 border-primary border-opacity-25 shadow-sm sticky-top bg-white"
                         style={{top: '24px'}}>
                        <div className="card-body p-4">
                            <h5 className="fw-bold pb-2 mb-3 border-bottom text-dark">Billing Overview</h5>

                            <div className="d-flex justify-content-between my-2 small text-muted">
                                <span>Ticket Pool Quantity:</span>
                                <span className="fw-medium">{seats?.size || seats?.length || 0}</span>
                            </div>

                            <div className="d-flex justify-content-between my-2 small text-muted">
                                <span>Processing Fees / Tax:</span>
                                <span className="text-success fw-medium">INCLUDED</span>
                            </div>

                            <hr className="my-3 opacity-25"/>

                            <div className="p-3 bg-light rounded mb-4">
                                <div className="d-flex justify-content-between align-items-center">
                                    <div>
                                        <span className="fw-bold text-dark d-block">Total:</span>
                                    </div>
                                    <span className="fw-extrabold fs-2 text-primary">
                                        {totalPrice?.toFixed(2) || "0.00"} PLN
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div className="d-flex justify-content-end mt-5 border-top pt-3">
                <button
                    type="button"
                    className="btn btn-success btn-lg px-5 shadow text-uppercase fw-extrabold text-white"
                    onClick={onConfirm}
                >
                    Confirm & Authorize Payment →
                </button>
            </div>
        </div>
    );
}