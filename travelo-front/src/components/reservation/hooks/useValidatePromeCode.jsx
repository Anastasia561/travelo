import {useMutation} from "@tanstack/react-query";
import useAxiosPrivate from "../../../hooks/auth/useAxiosPrivate.jsx";

export const useValidatePromoCode = () => {
    const axiosPrivate = useAxiosPrivate();

    return useMutation({
        mutationFn: async (promoCode) => {
            try {
                const res = await axiosPrivate.get(`/discounts/${promoCode}`);
                return res.data.data;
            } catch (error) {
                if (error.response && error.response.data) {
                    throw error.response.data;
                }
                throw error;
            }
        }
    });
};