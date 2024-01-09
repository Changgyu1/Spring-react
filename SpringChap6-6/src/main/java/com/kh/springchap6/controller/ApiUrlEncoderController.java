package com.kh.springchap6.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class ApiUrlEncoderController {

    @GetMapping("/allow_info/basic")
    public String allowBasic() {
        StringBuffer result = new StringBuffer();
        try {
            StringBuilder urlBuilder = new StringBuilder("http://시작하는 주소 작성"); /*URL*/
            urlBuilder.append("?ServiceKey=발급받은키"); /*Service Key*/
            urlBuilder.append("&pageNo=1"); /*페이지 번호*/
            urlBuilder.append("&numOfRows=10"); /*한 페이지 결과수*/
            urlBuilder.append("&type=json"); /*결과 json 포맷*/
            
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line).append("\n");
            }
            rd.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}