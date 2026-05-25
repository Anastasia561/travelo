import axios from "../../../api/axios.js";
import {useQuery} from "@tanstack/react-query";

export const useTrips = (page = 0, size = 5) => {

    return useQuery({
        queryKey: ["trips", page, size],
        queryFn: async () => {
            const res = await axios.get("/trips", {
                params: {page, size},
            });

            if (res.data.error) throw new Error(res.data.error);
            return res.data.data;
        },
        keepPreviousData: true,
    });
};