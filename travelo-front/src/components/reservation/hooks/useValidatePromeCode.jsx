import {useMutation} from "@tanstack/react-query";
import axios from "../../../api/axios.js"

export const useValidatePromoCode = () => {

    return useMutation({
        mutationFn: async (promoCode) => {
            try {
                const res = await axios.get(`/discounts/${promoCode}`);
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