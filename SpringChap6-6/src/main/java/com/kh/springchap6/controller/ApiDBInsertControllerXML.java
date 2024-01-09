package com.kh.springchap6.controller;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ApiDBInsertControllerXML {
	
	@GetMapping("/map_info/add")
	public String DBInsert() {
		// 데이터를 시작하기 전에는 StringBuilder
		
		StringBuilder result = new StringBuilder();
		try {
            String apiUrl = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidFcst";
            String apiKey = "sVqf0xn0EkNRVacsp6xQ4CIMI2aF%2FH%2B%2FWoeRXTUCwL4PeRG6IVQmUBiAA3dgcita9y9CECq%2FJZzz%2B7rUp3s0iw%3D%3D";
            String numOfRows = "1";
            String pageNo = "10";
            String dataType = "xml";
            String stnId = "108";
            String tmFc = "202401090600";
            String encodedApiKey = URLEncoder.encode(apiKey, "UTF-8");
            String encodedUrl = String.format("%s?serviceKey=%s&numOfRows=%s&pageNo=%s&dataType=%s&stnId=%s&tmFc=%s",
                    apiUrl, encodedApiKey, numOfRows, pageNo, dataType, stnId, tmFc);
            
            URL url = new URL(encodedUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            
            String line;
            while((line = reader.readLine()) != null) {
            	result.append(line);
            }
            reader.close();
            connection.disconnect();
            // 데이터베이스
            insertIntoOracleOB(result.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	private void insertIntoOracleOB(String data) {
		String jdbcUrl = "jdbc:oracle:thin:localhost/XE";
		String username = "khcafe";
		String password = "kh1234";
		
		try(Connection connection = DriverManager.getConnection(jdbcUrl, username, password)){
			String sql = "INSERT INTO culture (id, data) VALUES(culture_seq.NEXTVAL, ?)";
			
			try(PreparedStatement ps = connection.prepareStatement(sql)){
				ps.setString(1, data);
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

}
