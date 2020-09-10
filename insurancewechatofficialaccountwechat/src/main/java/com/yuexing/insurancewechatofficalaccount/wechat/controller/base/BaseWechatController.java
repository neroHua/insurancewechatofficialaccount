package com.yuexing.insurancewechatofficalaccount.wechat.controller.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseWechatController {

    protected static Logger logger = LoggerFactory.getLogger(BaseWechatController.class);
    private final String tokenURL = "cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    @Value("${yuexing.wechat.appId:null}")
    protected String appId;
    @Value("${yuexing.wechat.secret:null}")
    protected String secret;
    @Value("${yuexing.wechat.encryptToken:null}")
    protected String encryptToken;
    @Value("${yuexing.auth.endpoint:null}")
    protected String authEndpoint;
    @Value("${yuexing.api.endpoint:null}")
    protected String apiEndpoint;
    protected OkHttpClient client = new OkHttpClient();
    private String accessToken;
    private Date accessTokenCreatedAt;

    protected String requestAndExtract(String url, String property) throws IOException {
        logger.info("Token Url: " + tokenURL);

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            String json = response.body().string();
            Pattern p = Pattern.compile("\"" + property + "\":\"(.+?)\"");
            Matcher m = p.matcher(json);
            if (m.find()) {
                return m.group(1);
            }
        }

        return null;
    }


    protected String requestAccessToken() throws IOException {
        return requestAndExtract(apiEndpoint + String.format(tokenURL, appId, secret), "access_token");
    }

    protected synchronized String getAccessToken() {
        try {
            if (accessToken == null) {
                accessToken = requestAccessToken();
                accessTokenCreatedAt = new Date();
            } else {
                Date now = new Date();
                if (now.getTime() - accessTokenCreatedAt.getTime() > 7000000) {
                    accessToken = requestAccessToken();
                    accessTokenCreatedAt = new Date();
                }
            }
        } catch (IOException e) {
            accessToken = null;
        } finally {
            return accessToken;
        }
    }


    protected <T> T doExecuteGetRequest(String url) throws Exception {
        Request request = new Request.Builder().url(url).build();
        Response tokenResponse = client.newCall(request).execute();
        if (tokenResponse.isSuccessful()) {
            String result = tokenResponse.body().string();
            logger.info("Response Result: " + result);
            return new ObjectMapper().readValue(result, new TypeReference<T>() {
            });
        }

        return null;
    }

    protected <T> T doExecutePostRequest(String url, Object content) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        String body = objectMapper.writeValueAsString(content);

        logger.info("Post body is :" + body);

        okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(
                MediaType.parse("application/json;charset=utf-8"), body);

        Request request = new Request.Builder().url(url).post(requestBody).build();

        logger.info("Post url is :" + url);

        Response tokenResponse = client.newCall(request).execute();

        if (tokenResponse.isSuccessful()) {
            String result = tokenResponse.body().string();
            logger.info("Response Result: " + result);
            return objectMapper.readValue(result, new TypeReference<T>() {
            });
        }

        return null;
    }

}
