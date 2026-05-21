import {useState} from 'react';
import dayjs from 'dayjs';
import ListContainer from "./common/ListContainer.jsx";
import {useTrips} from "./hooks/useTrips.jsx";
import Pagination from "../common/Pagination.jsx";
import {useNavigate} from "react-router-dom";

const TripList = () => {
    const [currentPage, setCurrentPage] = useState(1);
    const pageSize = 3;
    const navigate = useNavigate();

    const {data, isLoading, isError} = useTrips(currentPage - 1, pageSize);

    if (isError) return <div className="alert alert-danger m-4">Failed to load trips</div>;

    const trips = data?.content || [];
    const totalPages = data?.totalPages || 0;

    return (
        <ListContainer title="Available Trips">
            <div className="row row-cols-1 g-4 mt-2">
                {isLoading ? (
                    <div className="text-center p-5">
                        <div className="spinner-border text-primary mb-2" role="status"></div>
                        <div>Searching for best routes...</div>
                    </div>
                ) : trips.length > 0 ? (
                    trips.map(t => {
                        let statusBadge = null;
                        if (t.isCancelled) {
                            statusBadge =
                                <span className="badge bg-danger-subtle text-danger px-2 py-1">Cancelled</span>;
                        } else if (t.isFull) {
                            statusBadge =
                                <span className="badge bg-warning-subtle text-warning px-2 py-1">Full</span>;
                        } else if (t.availablePlaceCount <= 3) {
                            statusBadge = <span
                                className="badge bg-info-subtle text-info px-2 py-1">{t.availablePlaceCount} seats left!</span>;
                        }

                        const isBookable = !t.isCancelled && !t.isFull;

                        return (
                            <div key={t.id}
                                 className={`card shadow-sm border-start ${t.isCancelled ? 'border-danger border-3' : 'border-primary border-3'} mb-3`}>
                                <div className="card-body p-4">

                                    <div className="d-flex justify-content-between align-items-center mb-3">
                                        <span className="text-muted small fw-semibold text-uppercase tracking-wider">
                                            🚌 {t.vehicleType || 'Bus'}
                                        </span>
                                        {statusBadge}
                                    </div>

                                    <div className="row align-items-center mb-4">
                                        <div className="col-md-4">
                                            <h5 className="mb-1 fw-bold">{t.startCityName}</h5>
                                            <p className="text-muted small mb-0">
                                                {t.departureTime ? dayjs(t.departureTime).format('DD MMM • HH:mm') : '---'}
                                            </p>
                                        </div>

                                        <div className="col-md-4 text-center my-2 my-md-0 position-relative">
                                            <div className="text-muted">➡️</div>
                                        </div>

                                        <div className="col-md-4 text-md-end">
                                            <h5 className="mb-1 fw-bold">{t.destinationName}</h5>
                                            <h5 className="mb-1 text-muted">{t.destinationCityName}</h5>
                                            <p className="text-muted small mb-0">
                                                {t.arrivalTime ? dayjs(t.arrivalTime).format('DD MMM • HH:mm') : '---'}
                                            </p>
                                        </div>
                                    </div>

                                    <div className="d-flex justify-content-between align-items-center pt-3 border-top">
                                        <div>
                                            <span className="text-muted small d-block">Price per trip</span>
                                            <span className="fs-4 fw-extrabold text-success">
                                                {t.price} <span
                                                className="small fs-6 fw-normal text-muted">{t.currency}</span>
                                            </span>
                                        </div>

                                        <div>
                                            <span className="text-muted small d-block">Available seats</span>
                                            <span
                                                className={`fs-5 text-muted ${t.availablePlaceCount === 0 ? 'text-danger' : 'text-dark'}`}>
                                                    {t.availablePlaceCount}
                                                </span>
                                        </div>

                                        <button
                                            className={`btn btn-lg px-4 ${isBookable ? 'btn-primary' : 'btn-secondary disabled'}`}
                                            disabled={!isBookable}
                                            onClick={() => {
                                                return navigate(`/book/${t.id}`);
                                            }}
                                        >
                                            {t.isCancelled ? 'Unavailable' : t.isFull ? 'Sold Out' : 'Book Ticket'}
                                        </button>
                                    </div>

                                </div>
                            </div>
                        );
                    })
                ) : (
                    <div className="text-center p-5 text-muted">
                        <p className="fs-5 mb-1">No trips found</p>
                    </div>
                )}
            </div>

            <Pagination
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={(page) => setCurrentPage(page)}
            />
        </ListContainer>
    );
};

export default TripList;