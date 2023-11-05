package com.jc.gymbasicsystemfront.api;

import com.google.gson.Gson;
import com.jc.gymbasicsystemfront.utils.TokenManager;
import java.io.IOException;
import okhttp3.*;
import java.lang.reflect.Type;

public class GymBasicSystemApi {

    private final OkHttpClient httpClient;
    private final String baseUrl;
    private final Gson gson;
    private final TokenManager tokenManager;
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    
    public GymBasicSystemApi(String baseUrl, TokenManager tokenManager) {
        this.httpClient = new OkHttpClient();
        this.baseUrl = baseUrl;
        this.gson = new Gson();
        this.tokenManager = tokenManager;
    }
    
    
    public <T> T get(String endPoint, Type typeOf, boolean withAuth)throws IOException {
        return executeRequest(endPoint, "GET", null, typeOf, withAuth);
    }  
    
    public <T> T post(String endPoint, String json, Type typeOf, boolean withAuth) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        return executeRequest(endPoint, "POST", body, typeOf, withAuth);
    }

    public <T> T put(String endPoint, String json, Type typeOf, boolean withAuth) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        return executeRequest(endPoint, "PUT", body, typeOf, withAuth);
    }

    public <T> T patch(String endPoint, String json, Type typeOf, boolean withAuth) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        return executeRequest(endPoint, "PATCH", body, typeOf, withAuth);
    }

    public <T> T delete(String endPoint, Type typeOf, boolean withAuth) throws IOException {
        return executeRequest(endPoint, "DELETE", null, typeOf, withAuth);
    }
 
    private <T> T executeRequest(
            String endPoint,
            String method,
            RequestBody body,
            Type typeOf,
            boolean withAuth
    ) throws IOException {
        System.out.println(method + ": " + endPoint);
        Request.Builder requestBuilder = new Request.Builder()
                .url(baseUrl + endPoint)
                .method(method, body);

        if (withAuth) {
            requestBuilder.addHeader("Authorization", "Bearer " + tokenManager.getAccessToken());
        }

        Request request = requestBuilder.build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String jsonResponse = response.body().string();
            return gson.fromJson(jsonResponse, typeOf);
        }
    }

}
