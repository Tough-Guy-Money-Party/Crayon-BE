package com.yoyomo.global.config.kakao;

import com.yoyomo.domain.user.application.dto.res.UserResponse;
import com.yoyomo.global.config.kakao.dto.KakaoInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Service
public class KakaoService {
    private final String CLIENT_ID;
    private final String REDIRECT_URL;
    public KakaoService(@Value("${spring.application.kakao-client-id}") String clientId, @Value("${spring.application.kakao-redirect-uri}") String redirectUrl) {
        this.CLIENT_ID = clientId;
        this.REDIRECT_URL = redirectUrl;
    }

    public String getKakaoAccessToken (String code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + CLIENT_ID);
            sb.append("&redirect_uri=" + REDIRECT_URL);
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            log.info("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            log.info("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            log.info("access_token : " + access_Token);
            log.info("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }
    public KakaoInfoResponse getInfo(String token) throws Exception {
        String reqURL = "https://kapi.kakao.com/v2/user/me";

        URL url = new URL(reqURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Bearer " + token);

        int responseCode = conn.getResponseCode();
        log.info("responseCode : " + responseCode);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }
        log.info("response body : " + result);

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(result);

        int id = element.getAsJsonObject().get("id").getAsInt();
        boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
        String email = "";
        if(hasEmail){
            email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
        }
        String name = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();

        log.info("id : " + id);
        log.info("email : " + email);
        log.info("nickname : " + name);
        br.close();

        KakaoInfoResponse kakaoInfoResponse = KakaoInfoResponse.builder()
                .email(email)
                .name(name)
                .build();

        return kakaoInfoResponse;
    }
}
