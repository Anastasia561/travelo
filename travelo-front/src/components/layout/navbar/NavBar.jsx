import {NavLink, useNavigate} from "react-router-dom";
import "./NavBar.css";
import logo from "/airplane.png";
import useLogout from "../../../hooks/auth/useLogout.jsx";
import useAuth from "../../../hooks/auth/useAuth.jsx";

const NavBar = () => {
    const logout = useLogout();
    const navigate = useNavigate();
    const {auth} = useAuth();

    const navLinks = [
        {to: "/home", label: "Home", roles: ["ROLE_USER"]},
        {to: "/hh", label: "Trips", roles: ["ROLE_USER"]},
        {to: "/hh", label: "Vehicles", roles: ["ROLE_USER"]},
        {to: "/hh", label: "Destinations", roles: ["ROLE_USER"]}
    ];

    const handleLogout = async () => {
        await logout();
        navigate("/");
    };

    return (
        <div className="header">

            <NavLink to="/home" className="header-left">
                <img src={logo} alt="Logo" className="logo"/>
                <h1 className="title">Travelo</h1>
            </NavLink>

            <nav className="header-nav">

                <div className="nav-links">
                    {navLinks
                        .filter(link => link.roles.includes(auth?.role))
                        .map(link => (
                            <NavLink key={link.to} to={link.to} className="nav-item">
                                {link.label}
                            </NavLink>
                        ))}
                </div>

                <div className="nav-actions">
                    <button onClick={handleLogout} className="nav-item logout-btn">
                        <i className="fas fa-sign-out-alt"></i> Log out
                    </button>
                </div>

            </nav>
        </div>
    );
};

export default NavBar;