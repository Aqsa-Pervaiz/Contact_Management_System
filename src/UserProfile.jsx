import React, { useState, useEffect } from "react";
import "./UserProfile.css";

const UserProfile = ({ onLogout }) => {
  const [user, setUser] = useState(null);
  const [showChangePasswordModal, setShowChangePasswordModal] = useState(false);
  const [passwordData, setPasswordData] = useState({
    current: "",
    new: "",
    confirm: "",
  });
  useEffect(() => {
    const savedUser = JSON.parse(localStorage.getItem("user"));
    if (!savedUser) return;
    fetch(`http://localhost:8081/${savedUser.email}`)
      .then((res) => res.json())
      .then((data) => setUser(data))
      .catch((err) => console.error("Error fetching user:", err));
  }, []);
  const handleLogout = () => {
    localStorage.removeItem("user");
    onLogout();
  };
  const handleChangePassword = async () => {
    if (passwordData.new !== passwordData.confirm) {
      alert("New password does not match");
      return;
    }
    try {
      const res = await fetch("http://localhost:8081/user/change-password", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          email: user.email,
          currentPassword: passwordData.current,
          newPassword: passwordData.new,
        }),
      });
      const text = await res.text();
      alert(text);

      setPasswordData({ current: "", new: "", confirm: "" });
      setShowChangePasswordModal(false);
    } 
    catch (error) {
      console.error("Error changing password", error);
      alert("Something went wrong! Please try again.");
    }
  };
  const handleCancel = () => {
    setPasswordData({ current: "", new: "", confirm: "" });
    setShowChangePasswordModal(false);
  };
  return (
    <div className="user-profile">
      <h2>User Profile</h2>
      <div className="user-details">
        <p><strong>Name:</strong> {user.name}</p>
        <p><strong>Email:</strong> {user.email || "-"}</p>
        <p><strong>Phone:</strong> {user.phone || "-"}</p>
      </div>
      <div className="user-actions">
        <button onClick={() => setShowChangePasswordModal(true)}> Change Password
        </button>
        <button onClick={handleLogout}>Logout</button>
      </div>
      {showChangePasswordModal && (
        <div className="modal">
          <div className="modal-content">
          <h3>Change Password</h3>
          <form>
              <div>
                <label>Current Password:</label>
                <input type="password" value={passwordData.current} onChange={(e) => setPasswordData({ ...passwordData, current: e.target.value, }) } />
              </div>
              <div>
                <label>New Password:</label>
                <input  type="password" value={passwordData.new} onChange={(e) => setPasswordData({ ...passwordData, new: e.target.value }) }
                />
              </div>

              <div>
                <label>Confirm New Password:</label>
                <input type="password" value={passwordData.confirm} onChange={(e) => setPasswordData({ ...passwordData, confirm: e.target.value, }) } />
              </div>
            </form>
            <div className="modal-actions">
              <button type="button" onClick={handleChangePassword}>  Reset </button>
              <button type="button" onClick={handleCancel}>  Cancel </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default UserProfile;
