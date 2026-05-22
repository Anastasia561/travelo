import {useMutation} from "@tanstack/react-query";
import useAxiosPrivate from "../../../hooks/auth/useAxiosPrivate.jsx";

export const useCreateReservation = () => {
    const axiosPrivate = useAxiosPrivate();

    return useMutation({
        mutationFn: async (formData) => {
            const postBody = {
                tripId: formData.tripId,
                seatIds: formData.seatIds,
                promeCode: formData.promeCode?.trim() === "" ? null : formData.promeCode,
                loyaltyPoints: formData.loyaltyPoints
            };

            const res = await axiosPrivate.post("/reservations", postBody);
            return res.data.data;
        }
    });
};