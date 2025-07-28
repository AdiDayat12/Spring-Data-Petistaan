package com.abhishekvermaa10.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@Getter
@Setter
public class TransliterationService {

    @Value("${app.transliterate.apiType}") // diambil dari application-dev.yml atau application-prod.yml
    private String apiType;

    public String transliterate(String input, String isoCode) {
        if ("dummy".equalsIgnoreCase(apiType)) {
            return "[DUMMY] " + input;
        }

        // Ini akan dipakai di PROD (asli)
        return callRealApi(input, isoCode);
    }

    private String callRealApi(String input, String isoCode) {
        try {
            String itcCode = isoCode + "-t-i0-und";
            String encodedInput = URLEncoder.encode(input, StandardCharsets.UTF_8);
            String urlStr = "https://inputtools.google.com/request?itc=" + itcCode + "&text=" + encodedInput;

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String response = reader.lines().reduce("", (a, b) -> a + b);
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode root = mapper.readTree(response);

                    if (root.get(0).asText().equals("SUCCESS") &&
                            root.get(1).isArray() &&
                            root.get(1).size() > 0 &&
                            root.get(1).get(0).get(1).isArray() &&
                            root.get(1).get(0).get(1).size() > 0) {

                        return root.get(1).get(0).get(1).get(0).asText();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return input;
    }
}

