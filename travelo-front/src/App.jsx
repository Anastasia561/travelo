import {Routes, Route} from 'react-router-dom'
import Layout from "./components/layout/Layout.jsx";
import Login from "./pages/Login.jsx";
import Unauthorized from "./pages/Unauthorized.jsx";
import Missing from "./pages/Missing.jsx";
import RequireAuth from "./components/auth/RequireAuth.jsx";
import PersistLogin from "./components/auth/PersistLogin.jsx";
import ProtectedLayout from "./components/layout/ProtectedLayout.jsx";
import Providers from "./context/Providers.jsx";
import TripList from "./components/listing/TripList.jsx";
import Home from "./pages/Home.jsx";
import DestinationList from "./components/listing/DestinationList.jsx";
import VehicleList from "./components/listing/VehicleList.jsx";
import ReservationForm from "./components/reservation/ReservationForm.jsx";

function App() {

    return (
        <Providers>
            <Routes>
                <Route path="/" element={<Layout/>}>
                    <Route index element={<Login/>}/>
                    <Route path="unauthorized" element={<Unauthorized/>}/>
                </Route>

                <Route path="/" element={<PersistLogin/>}>
                    <Route element={<ProtectedLayout/>}>

                        <Route element={<RequireAuth allowedRoles={["ROLE_USER"]}/>}>
                            <Route path="trips" element={<TripList/>}/>
                            <Route path="home" element={<Home/>}/>
                            <Route path="destinations" element={<DestinationList/>}/>
                            <Route path="vehicles" element={<VehicleList/>}/>

                            <Route path="/destinations/:destinationId/vehicles" element={<VehicleList/>}/>
                            <Route path="/vehicles/:vehicleId/destinations" element={<DestinationList/>}/>
                            <Route path="/book/:tripId" element={<ReservationForm/>}/>
                        </Route>

                    </Route>
                </Route>

                <Route path="*" element={<Missing/>}/>
            </Routes>
        </Providers>
    )
}

export default App;
