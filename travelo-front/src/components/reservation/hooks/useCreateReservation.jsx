import {useMutation} from "@tanstack/react-query";
import axios from "../../../api/axios.js"

export const useCreateReservation = () => {

    return useMutation({
        mutationFn: async (formData) => {
            const postBody = {
                tripId: formData.tripId,
                seatIds: formData.seatIds,
                promeCode: formData.promeCode?.trim() === "" ? null : formData.promeCode,
                loyaltyPoints: formData.loyaltyPoints
            };

            const res = await axios.post("/reservations", postBody);
            return res.data.data;
        }
    });
};