import React from "react";
import './Navbar.css';

function Navbar({ setActivePage }) {
  return (
    <nav className="navbar">
      <div className="navbar-left">
        <h2>Contact Management System</h2>
      </div>
      <div className="navbar-right">
        <button onClick={() => setActivePage("contacts")}>Contacts</button>
        <button onClick={() => setActivePage("profile")}>Profile</button>
        <button onClick={() => setActivePage("login")}>Logout</button>
      </div>
    </nav>
  );
}

export default Navbar;
