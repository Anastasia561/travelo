import {NavLink} from "react-router-dom";
import "./NavBar.css";
import logo from "/airplane.png";

const NavBar = () => {

    const navLinks = [
        {to: "/", label: "Trips"},
    ];

    return (
        <div className="header">

            <NavLink className="header-left" to={"/"}>
                <img src={logo} alt="Logo" className="logo"/>
                <h1 className="title">Travelo</h1>
            </NavLink>

            <nav className="header-nav">

                <div className="nav-links">
                    {navLinks
                        .map(link => (
                            <NavLink key={link.to} to={link.to} className="nav-item">
                                {link.label}
                            </NavLink>
                        ))}
                </div>

                <div className="nav-actions">
                    <button className="nav-item logout-btn">
                        <i className="fas fa-sign-out-alt"></i> Log out
                    </button>
                </div>

            </nav>
        </div>
    );
};

export default NavBar;