import {Routes, Route} from 'react-router-dom'
import Layout from "./components/layout/Layout.jsx";
import Missing from "./pages/Missing.jsx";
import Providers from "./context/Providers.jsx";
import TripList from "./components/listing/TripList.jsx";
import ReservationForm from "./components/reservation/ReservationForm.jsx";

function App() {

    return (
        <Providers>
            <Routes>
                <Route path="/" element={<Layout/>}>
                    <Route index element={<TripList/>}/>
                    <Route path="/book/:tripId" element={<ReservationForm/>}/>
                </Route>

                <Route path="*" element={<Missing/>}/>
            </Routes>
        </Providers>
    )
}

export default App;
