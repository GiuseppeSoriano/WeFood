package it.unipi.lsmsdb.wefood.httprequests;

import com.fasterxml.jackson.databind.ObjectMapper;


import it.unipi.lsmsdb.wefood.apidto.LoginRequestDTO;
import it.unipi.lsmsdb.wefood.model.Admin;
import it.unipi.lsmsdb.wefood.model.Ingredient;

import java.net.http.HttpResponse;

public class AdminHTTP{
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BaseHttpRequest baseHttpRequest = new BaseHttpRequest();

    public Admin loginAdmin(LoginRequestDTO credentials){
        try{
            String requestBody = objectMapper.writeValueAsString(credentials);
            HttpResponse<String> result = baseHttpRequest.sendRequest("admin/login", requestBody);
            if(result.statusCode() == 200 && !result.body().isEmpty())
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), Admin.class);
            // Unauthorized (401) or other errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Boolean createIngredient(Ingredient ingredient){
        try{
            String requestBody = objectMapper.writeValueAsString(ingredient);
            HttpResponse<String> result = baseHttpRequest.sendRequest("admin/createIngredient", requestBody);
            if(result.statusCode() == 200 && !result.body().isEmpty())
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), Boolean.class);
            // Unauthorized (401) or other errors
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Boolean banUser(String username){
        try{
            HttpResponse<String> result = baseHttpRequest.sendRequest("admin/banUser", username);
            if(result.statusCode() == 200 && !result.body().isEmpty())
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), Boolean.class);
            // Unauthorized (401) or other errors
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Boolean unbanUser(String username){
        try{
            HttpResponse<String> result = baseHttpRequest.sendRequest("admin/unbanUser", username);
            if(result.statusCode() == 200 && !result.body().isEmpty())
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), Boolean.class);
            // Unauthorized (401) or other errors
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

}