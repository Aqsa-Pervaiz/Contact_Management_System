import React, { useState } from 'react'
import './LoginPage.css'

const LoginPage = ({onLogin}) => {
  const [action, setAction] = useState("");
  const [loginData, setLoginData] = useState({ identifier: "", password: "" });
  const [signupData, setSignupData] = useState({
    name: "",
    email: "",
    phone: "",
    password: "",
  });
  const registerLink = () => setAction("active");
  const loginLink = () => setAction("");
  const handleLogin = async (e) => {
  e.preventDefault();
  try {
    const res = await fetch("http://localhost:8081/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        email: loginData.identifier.includes("@") ? loginData.identifier : "",
        phone: !loginData.identifier.includes("@") ? loginData.identifier : "",
        password: loginData.password,
      }),
    });
    const data = await res.json();
    const identifier = loginData.identifier;
    const userData = {
      name: data.user?.name || "User",
      email: identifier.includes("@") ? identifier : data.user?.email || "",
      phone: !identifier.includes("@") ? identifier : data.user?.phone || "",
    };
    localStorage.setItem("user", JSON.stringify(userData));
    onLogin(userData);
  }
   catch (error) {
    console.error("Error during login:", error);
    alert("Login failed. Please try again.");
  }
};
  const handleSignup = async (e) => {
  e.preventDefault();

  if (!signupData.email && !signupData.phone) {
    alert("Please enter either an email or a phone number");
    return;
  }

  try {
    const res = await fetch("http://localhost:8081/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(signupData),
    });

    const data = await res.json();
    if (res.ok) {
      alert(data.message || "Signup successful!");
      setAction(""); 
    } else {
      alert(data.message || "Signup failed. Please try again.");
    }

  } catch (error) {
    console.error("Error signingup:", error);
    alert("Signup failed. Please try again later.");
  }
};

  return (
    <div className={`main ${action}`}>
      <div className='login'>
        <form onSubmit={handleLogin}>
          <h1>Sign in</h1>
        <div className='input'>
          <input type="text" placeholder="Email or Phone Number" value={loginData.identifier} onChange={(e) => setLoginData({ ...loginData, identifier: e.target.value })} required/></div>

        <div className='input'>
          <input type="password" placeholder='Password'value={loginData.password} onChange={(e) => setLoginData({ ...loginData, password: e.target.value })} required /></div>
        <button type='submit'>Login</button>
        <div className='register'>
          Don't have an account? <a href='#' onClick={registerLink}>Sign up</a></div>

        </form>  
      </div>
    
      <div className='signUp'>
        <form onSubmit={handleSignup}>
          <h1>Sign up</h1>
          <div className='input'>
            <input type="text" placeholder='Enter your name'value={signupData.name}onChange={(e) => setSignupData({ ...signupData, name: e.target.value })}required  /> </div>
          <div className='input'>
            <input type="text" placeholder='Enter your email address'value={signupData.email} onChange={(e) => setSignupData({ ...signupData, email: e.target.value })} /></div>
          <div className='or'>
            <p>or</p></div>
          <div className='input'>
            <input type="text" placeholder='Enter your phone number' value={signupData.phone}onChange={(e) => setSignupData({ ...signupData, phone: e.target.value })} /></div>
          <div className='input'>
            <input type="text" placeholder='Enter your password' value={signupData.password}onChange={(e) => setSignupData({ ...signupData, password: e.target.value })}required  /></div>
          <div className='checkbox'>
            <label>
              <input type="checkbox" required/> I agree to the terms and conditions</label></div>
          <div>
            <button type='submit'>Login
          </button>
           </div> 
          <div className='loginPage'>
            Already have an account? <a href='#' onClick={loginLink}>Sign in</a>
          </div>

        </form>  
      </div>
    </div>
  )
}

export default LoginPage
