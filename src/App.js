import React, { useState } from "react";
import Navbar from "./Navbar";
import ContactManagement from "./ContactManagementScreen";
import UserProfile from "./UserProfile";
import LoginPage from "./LoginPage";

function App() {
  const [activePage, setActivePage] = useState("login");
  const [user, setUser] = useState(null);
  const handleLogin = (loggedInUser) => {
    setUser(loggedInUser);
    setActivePage("contacts");
  };

  const handleLogout = () => {
    setUser(null);
    setActivePage("login");
  };
  return (
    <div 
    style={{ minHeight: "100vh", minWidth: "100vw", backgroundColor: "#d3d3d3" }}
    >
      {activePage !== "login" && <Navbar setActivePage={setActivePage} />}

      {activePage === "login" && <LoginPage onLogin={handleLogin} />}
      {activePage === "contacts" && <ContactManagement />}
      {activePage === "profile" && (<UserProfile user={user} onLogout={handleLogout} />
      )}
    </div>
  );
}

export default App;
