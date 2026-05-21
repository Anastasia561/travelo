import useAxiosPrivate from "../../../hooks/auth/useAxiosPrivate.jsx";
import {useQuery} from "@tanstack/react-query";

export const useTripInfo = (tripId) => {
    const axiosPrivate = useAxiosPrivate();

    return useQuery({
        queryKey: ["tripInfo", tripId],
        queryFn: async () => {
            const res = await axiosPrivate.get(`/trips/${tripId}`);

            if (res.data.error) throw new Error(res.data.error);
            return res.data.data;
        },
        keepPreviousData: true,
    });
};