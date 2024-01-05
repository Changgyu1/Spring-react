import React from "react";
import axios from "axios";
import NaverLogin from "react-naver-login";

export default function NaverLoginButton() {
  const handleNaverLogin = async () => {
    try {
      const response = await axios.get(
        "http://localhost:3000/login/oauth2/code/naver",
        { withCredentials: true }
      );
      console.log("FrontEnd에서 제공되는 콘솔 로드");
      console.log(response.data);
    } catch (error) {
      console.error("Naver Login Error : ", error);
    }
  };
  return (
    <div>
      <button onClick={handleNaverLogin}>Naver Login</button>
    </div>
  );
}
