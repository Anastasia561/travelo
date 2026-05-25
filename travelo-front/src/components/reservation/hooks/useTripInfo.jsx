import axios from "../../../api/axios.js"
import {useQuery} from "@tanstack/react-query";

export const useTripInfo = (tripId) => {

    return useQuery({
        queryKey: ["tripInfo", tripId],
        queryFn: async () => {
            const res = await axios.get(`/trips/${tripId}`);

            if (res.data.error) throw new Error(res.data.error);
            return res.data.data;
        },
        keepPreviousData: true,
    });
};