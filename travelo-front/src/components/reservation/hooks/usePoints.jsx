import axios from "../../../api/axios.js"
import {useQuery} from "@tanstack/react-query";

export const usePoints = () => {

    return useQuery({
        queryKey: ["points"],
        queryFn: async () => {
            const res = await axios.get('/customers/points');

            if (res.data.error) throw new Error(res.data.error);
            return res.data.data;
        },
        keepPreviousData: true,
    });
};