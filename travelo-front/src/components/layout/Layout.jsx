import {Outlet} from 'react-router-dom';
import NavBar from "./navbar/NavBar.jsx";
import Footer from "./footer/Footer.jsx";

const Layout = () => {
    return (
        <div className="app-layout">
            <NavBar/>
            <main className="content">
                <Outlet/>
            </main>
            <Footer/>
        </div>
    )
}

export default Layout;