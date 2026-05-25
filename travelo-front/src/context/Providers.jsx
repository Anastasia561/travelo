import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import {Toaster} from 'react-hot-toast';
import {toastOptions} from "./toastOptions.js";

const queryClient = new QueryClient();

const Providers = ({children}) => {
    return (
        <QueryClientProvider client={queryClient}>
            <Toaster {...toastOptions} />
            {children}
        </QueryClientProvider>
    );
};

export default Providers;