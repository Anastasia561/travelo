import {useState} from "react";
import ListContainer from "./common/ListContainer.jsx";
import Pagination from "../common/Pagination.jsx";
import DataCard from "./common/DataCard.jsx";
import {useDestinations} from "./hooks/useDestinations.jsx";
import {Link, useParams} from "react-router-dom";

const DestinationList = () => {
    const {vehicleId} = useParams();
    const [currentPage, setCurrentPage] = useState(1);
    const pageSize = 3;

    const {data, isLoading, isError} = useDestinations(currentPage - 1, pageSize, vehicleId);

    if (isError) return <div className="alert alert-danger m-4">Failed to load destinations</div>;

    const destinations = data?.destinations || [];
    const totalPages = data?.totalPages || 0;

    const displayTitle = vehicleId && data?.vehicleNumber && data?.vehicleType
        ? `Destinations for ${data.vehicleNumber} (${data.vehicleType?.toLowerCase().replace('_', ' ')})`
        : "All Destinations";

    return (
        <ListContainer title={displayTitle}>

            <div className="row row-cols-1 g-4 mt-2">
                {isLoading ? (
                    <div className="text-center p-5">Searching...</div>
                ) : destinations.length > 0 ? (
                    destinations.map(d => (
                        <DataCard
                            key={d.id}
                            name={d.name}
                            details={[
                                {label: 'Description', value: d.description},
                                {label: 'Location', value: `${d.cityName} (${d.countryName})`}
                            ]}
                            renderActions={() => (
                                <Link
                                    to={`/destinations/${d.id}/vehicles`}
                                    className="btn btn-outline-primary btn-sm text-center"
                                >
                                    List vehicles
                                </Link>
                            )}
                        />
                    ))
                ) : (
                    <div className="text-center p-5 text-muted">No destinations matched your search.</div>
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

export default DestinationList;