package it.unipi.lsmsdb.wefood.httprequests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.unipi.lsmsdb.wefood.apidto.IngredientAndLimitRequestDTO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import it.unipi.lsmsdb.wefood.model.Ingredient;

import java.net.http.HttpResponse;
import java.util.List;

public class IngredientHTTP{
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BaseHttpRequest baseHttpRequest = new BaseHttpRequest();
    
    
    public Ingredient findIngredientByName(String request) {
        try{
            HttpResponse<String> result = baseHttpRequest.sendRequest("ingredient/findIngredientByName", request);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), Ingredient.class);
            // errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    
    public List<Ingredient> getAllIngredients() {
        try{
            HttpResponse<String> result = baseHttpRequest.sendRequest("ingredient/getAllIngredients", "");
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), new TypeReference<List<Ingredient>>(){});
            // errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    
    public List<String> findIngredientsUsedWithIngredient(IngredientAndLimitRequestDTO request) {
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("ingredient/findIngredientsUsedWithIngredient", requestBody);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), new TypeReference<List<String>>(){});
            // errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    
    public List<String> findIngredientsUsedWithIngredient(String request) {
        try{
            HttpResponse<String> result = baseHttpRequest.sendRequest("ingredient/findIngredientsUsedWithIngredient", request);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), new TypeReference<List<String>>(){});
            // errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public List<String> mostPopularCombinationOfIngredients(String request){
        try{
            HttpResponse<String> result = baseHttpRequest.sendRequest("ingredient/mostPopularCombinationOfIngredients", request);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), new TypeReference<List<String>>(){});
            // errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public List<String> findNewIngredientsBasedOnFriendsUsage(RegisteredUserDTO request){
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("ingredient/findNewIngredientsBasedOnFriendsUsage", requestBody);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), new TypeReference<List<String>>(){});
            // errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    
    public List<String> findMostUsedIngredientsByUser(RegisteredUserDTO request){
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("ingredient/findMostUsedIngredientsByUser", requestBody);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), new TypeReference<List<String>>(){});
            // errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    
    public List<String> findMostLeastUsedIngredients(Boolean request){
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("ingredient/findMostLeastUsedIngredients", requestBody);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), new TypeReference<List<String>>(){});
            // errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}