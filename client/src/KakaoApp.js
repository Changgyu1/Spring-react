// ab6166690ed6fe2f9419122d74f59cef
import React from "react";
import KakaoLogin from "react-kakao-login";

const KakaApp = () => {
  const KakaoLoginSuccess = (res) => {
    console.log(res);
  };
  const KakaoLoginFailure = (err) => {
    console.log(err);
  };

  return (
    <div>
      <KakaoLogin
        token="ab6166690ed6fe2f9419122d74f59cef"
        onSuccess={KakaoLoginSuccess}
        onFailure={KakaoLoginFailure}
        //getProfile={true}
        render={(pros) => <button onClick={pros.onClick}>카카오 로그인</button>}
      />
    </div>
  );
};
export default KakaApp;
