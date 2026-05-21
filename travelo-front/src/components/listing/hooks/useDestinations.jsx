import useAxiosPrivate from "../../../hooks/auth/useAxiosPrivate.jsx";
import {useQuery} from "@tanstack/react-query";

export const useDestinations = (page = 0, size = 5, vehicleId = null) => {
    const axiosPrivate = useAxiosPrivate();

    return useQuery({
        queryKey: ["destinations", {page, size, vehicleId}],
        queryFn: async () => {
            if (vehicleId) {
                const res = await axiosPrivate.get(`/vehicles/${vehicleId}`);
                if (res.data.error) throw new Error(res.data.error);

                return {
                    destinations: res.data.data?.destinations || [],
                    totalPages: 1,
                    vehicleNumber: res.data.data?.number,
                    vehicleType: res.data.data?.type,
                };
            }

            const res = await axiosPrivate.get("/destinations", {
                params: {page, size},
            });
            if (res.data.error) throw new Error(res.data.error);

            return {
                destinations: res.data.data?.content || [],
                totalPages: res.data.data?.totalPages || 0,
                vehicleNumber: null,
                vehicleType: null,
            };
        },
        keepPreviousData: true,
    });
};