package com.kh.springchap6.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RestController
public class ApiController {

    @GetMapping("/api/get")
    public String allowBasic() {
        StringBuilder result = new StringBuilder();

        try {
            String apiUrl = "http://apis.data.go.kr/B550928/dissForecastInfoSvc/getDissForecastInfo";
            String apiKey = "65liqYkK/8aC6Rh048XPwFgemJxH+MbwP3xXZX99CvFgz0Fu4q+dhC5NIQlLfrLVjpnKo8xX6yALLyPhICI0HQ==";
            String numOfRows = "10";
            String pageNo = "1";
            String responseType = "xml";
            String dissCd = "1";
            String znCd = "11";

            String encodedApiKey = URLEncoder.encode(apiKey, "UTF-8");
            String encodedUrl = String.format("%s?serviceKey=%s&numOfRows=%s&pageNo=%s&type=%s&dissCd=%s&znCd=%s",
                    apiUrl, encodedApiKey, numOfRows, pageNo, responseType, dissCd, znCd);

            URL url = new URL(encodedUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}