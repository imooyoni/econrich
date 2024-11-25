package com.demo.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Service
@Slf4j
public class OpenApiService {

    @Value("${commonapi.service-key}")
    private String serviceKey;

    @Value("${commonapi.base-url}")
    private String baseUrl;

    public String getOpenApiData(int currentPage , int perPage ) {
        try {
            StringBuilder urlBuilder = new StringBuilder(baseUrl);
            urlBuilder.append("?" + URLEncoder.encode("currentPage", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(currentPage), "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("perPage", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(perPage), "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);

            URL url = new URL(urlBuilder.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            BufferedReader br;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            conn.disconnect();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
