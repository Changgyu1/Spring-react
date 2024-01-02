import React, { useState, useEffect } from "react";
import axios from "axios";
function App() {
  const [data, setData] = useState("");
  const [data1, setData1] = useState("");
  // fetchData라는 이름을 정의해서 try-catch 문을 사용해 비동기 작업 중
  // 발생하는 에러를 잡아내고 콘솔에 메세지를 출력하는 것
  // 간접적으로 호출
  useEffect(() => {
    const fetchData = async () => {
      try {
        const res = await axios.get("http://localhost:8080/api/hello", {
          withCredentials: true,
        });
        setData1(res.data);
      } catch (error) {
        console.log(error);
      }
    };
    fetchData();
  }, []);

  //(위에랑 비슷한 코드)
  //useEffect 안에서 직접 비동기 작업을 수행
  //간단하게 catch문을 사용해서 에러를 처리하고 콘솔에 에러 메세지를 출력
  useEffect(() => {
    axios
      .get("http://localhost:8080/api/java", { withCredentials: true })
      // response = res 는 같은 의미이며, 안에 변수 값은
      // 정해진 변수값은 없지만 되도록이면 res response라는 이름을 사용하면 좋음
      .then((res) => {
        setData(res.data);
      })
      .catch((error) => {
        console.log("데이터 없음", error);
      });
  }, []);

  return (
    <div>
      <h1> API 호출 확인 </h1>
      <p>{data}</p>
      <p>{data1}</p>
    </div>
  );
}

export default App;
