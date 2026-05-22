import useAxiosPrivate from "../../../hooks/auth/useAxiosPrivate.jsx";
import {useQuery} from "@tanstack/react-query";

export const usePoints = () => {
    const axiosPrivate = useAxiosPrivate();

    return useQuery({
        queryKey: ["points"],
        queryFn: async () => {
            const res = await axiosPrivate.get('/customers/points');

            if (res.data.error) throw new Error(res.data.error);
            return res.data.data;
        },
        keepPreviousData: true,
    });
};