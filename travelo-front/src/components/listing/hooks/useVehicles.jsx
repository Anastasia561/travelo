import useAxiosPrivate from "../../../hooks/auth/useAxiosPrivate.jsx";
import { useQuery } from "@tanstack/react-query";

export const useVehicles = (page = 0, size = 5, destinationId = null) => {
    const axiosPrivate = useAxiosPrivate();

    return useQuery({
        queryKey: ["vehicles", { page, size, destinationId }],
        queryFn: async () => {
            if (destinationId) {
                const res = await axiosPrivate.get(`/destinations/${destinationId}`);
                if (res.data.error) throw new Error(res.data.error);

                return {
                    vehicles: res.data.data?.vehicles || [],
                    totalPages: 1,
                    destinationName: res.data.data?.name
                };
            }

            const res = await axiosPrivate.get("/vehicles", {
                params: { page, size },
            });
            if (res.data.error) throw new Error(res.data.error);

            return {
                vehicles: res.data.data?.content || [],
                totalPages: res.data.data?.totalPages || 0,
                destinationName: null
            };
        },
        keepPreviousData: true,
    });
};