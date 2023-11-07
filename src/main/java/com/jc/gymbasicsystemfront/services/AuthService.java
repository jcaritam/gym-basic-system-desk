
package com.jc.gymbasicsystemfront.services;

import com.google.gson.Gson;
import com.jc.gymbasicsystemfront.api.ApiManager;
import com.jc.gymbasicsystemfront.api.GymBasicSystemApi;
import com.jc.gymbasicsystemfront.dto.auth.LoginCredentialsDto;
import com.jc.gymbasicsystemfront.exceptions.AuthException;
import com.jc.gymbasicsystemfront.models.JwtDto;
import java.io.IOException;

public class AuthService {

    private final ApiManager apiManager = ApiManager.getInstance();
    private final String relativeUri = "/auth";
    
    public void login(LoginCredentialsDto loginCredentialsDto) throws AuthException {
        
        try {
            GymBasicSystemApi gymApi = apiManager.getGymApi();
            
            Gson gson = new Gson();
            String json = gson.toJson(loginCredentialsDto);
            
            JwtDto jwtDto = gymApi.post(relativeUri + "/login", json, JwtDto.class, false);
            
            apiManager.getTokenManager().setAccessToken(jwtDto.getAccessToken());
            
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new AuthException("Error al iniciar session");
        }
        
    }
    
}

