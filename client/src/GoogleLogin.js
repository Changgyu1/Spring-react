//App.js
//npm install @react-oauth/google@latest
import { GoogleLogin } from "@react-oauth/google";
import { GoogleOAuthProvider } from "@react-oauth/google";

const GoogleLoginButton = () => {
  const clientId =
    "796954935449-mjah6u4uubmbf22oi87unqdh0ag5b1ik.apps.googleusercontent.com";
  return (
    <>
      <GoogleOAuthProvider clientId={clientId}>
        <GoogleLogin
          onSuccess={(res) => {
            console.log(res);
          }}
          onFailure={(err) => {
            console.log(err);
          }}
        />
      </GoogleOAuthProvider>
    </>
  );
};

export default GoogleLoginButton;
