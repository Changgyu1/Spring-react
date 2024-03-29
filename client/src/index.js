import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import KakaoApp from "./KakaoApp";
import reportWebVitals from "./reportWebVitals";
import GoogleLogin from "./GoogleLogin";
import NaverLoginButton from "./NaverLoginButton";
import NaverApp from "./NaverApp";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <NaverLoginButton />
    <NaverApp />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
