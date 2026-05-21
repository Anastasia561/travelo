import {useState} from "react";
import {Link, useParams} from "react-router-dom";
import ListContainer from "./common/ListContainer.jsx";
import Pagination from "../common/Pagination.jsx";
import DataCard from "./common/DataCard.jsx";
import {useVehicles} from "./hooks/useVehicles.jsx";

const VehicleList = () => {
    const {destinationId} = useParams();
    const [currentPage, setCurrentPage] = useState(1);
    const pageSize = 3;

    const {data, isLoading, isError} = useVehicles(currentPage - 1, pageSize, destinationId);

    if (isError) return <div className="alert alert-danger m-4">Failed to load vehicles</div>;

    const vehicles = data?.vehicles || [];
    const totalPages = data?.totalPages || 0;

    const displayTitle = destinationId && data?.destinationName
        ? `Vehicles for ${data.destinationName}`
        : "All Vehicles";

    return (
        <ListContainer title={displayTitle}>
            <div className="row row-cols-1 g-4 mt-2">
                {isLoading ? (
                    <div className="text-center p-5">Searching...</div>
                ) : vehicles.length > 0 ? (
                    vehicles.map(v => (
                        <DataCard
                            key={v.id}
                            name={v.number}
                            details={[
                                {label: 'Type', value: v.type},
                                {label: 'Max seats', value: v.maxRow * v.rowWidth}
                            ]}
                            renderActions={() => (
                                <>
                                    <Link
                                        to={`/vehicles/${v.id}/destinations`}
                                        className="btn btn-outline-primary btn-sm text-center"
                                    >
                                        List destinations
                                    </Link>
                                </>
                            )}
                        />
                    ))
                ) : (
                    <div className="text-center p-5 text-muted">No vehicles matched your search.</div>
                )}
            </div>

            {!destinationId && (
                <Pagination
                    currentPage={currentPage}
                    totalPages={totalPages}
                    onPageChange={(page) => setCurrentPage(page)}
                />
            )}
        </ListContainer>
    );
};

export default VehicleList;