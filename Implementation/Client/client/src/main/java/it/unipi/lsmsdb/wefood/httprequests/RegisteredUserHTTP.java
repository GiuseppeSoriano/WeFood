package it.unipi.lsmsdb.wefood.httprequests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import it.unipi.lsmsdb.wefood.apidto.LoginRequestDTO;
import it.unipi.lsmsdb.wefood.apidto.RegisteredUserRequestDTO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;

import java.net.http.HttpResponse;
import java.util.List;

public class RegisteredUserHTTP{
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BaseHttpRequest baseHttpRequest = new BaseHttpRequest();

    public RegisteredUser login(LoginRequestDTO request){
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("registereduser/login", requestBody);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), RegisteredUser.class);
            // Unauthorized (401) or other errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Boolean modifyPersonalInformation(RegisteredUser request){
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("registereduser/modifyPersonalInformation", requestBody);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), Boolean.class);
            // Unauthorized (401) or other errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Boolean deleteUser(RegisteredUser request){
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("registereduser/deleteUser", requestBody);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), Boolean.class);
            // Unauthorized (401) or other errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Boolean followUser(RegisteredUserRequestDTO request){
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("registereduser/followUser", requestBody);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), Boolean.class);
            // Unauthorized (401) or other errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Boolean unfollowUser(RegisteredUserRequestDTO request){
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("registereduser/unfollowUser", requestBody);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), Boolean.class);
            // Unauthorized (401) or other errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<RegisteredUserDTO> findFriends(RegisteredUserDTO request){
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("registereduser/findFriends", requestBody);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), new TypeReference<List<RegisteredUserDTO>>(){});
            // Unauthorized (401) or other errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<RegisteredUserDTO> findFollowers(RegisteredUserDTO request){
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("registereduser/findFollowers", requestBody);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), new TypeReference<List<RegisteredUserDTO>>(){});
            // Unauthorized (401) or other errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<RegisteredUserDTO> findFollowed(RegisteredUserDTO request){
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("registereduser/findFollowed", requestBody);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), new TypeReference<List<RegisteredUserDTO>>(){});
            // Unauthorized (401) or other errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<RegisteredUserDTO> findUsersToFollowBasedOnUserFriends(RegisteredUserDTO request){
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("registereduser/findUsersToFollowBasedOnUserFriends", requestBody);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), new TypeReference<List<RegisteredUserDTO>>(){});
            // Unauthorized (401) or other errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<RegisteredUserDTO> findMostFollowedUsers(){
        try{
            HttpResponse<String> result = baseHttpRequest.sendRequest("registereduser/findMostFollowedUsers", "");
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), new TypeReference<List<RegisteredUserDTO>>(){});
            // Unauthorized (401) or other errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<RegisteredUserDTO> findUsersByIngredientUsage(String request){
        try{
            HttpResponse<String> result = baseHttpRequest.sendRequest("registereduser/findUsersByIngredientUsage", request);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), new TypeReference<List<RegisteredUserDTO>>(){});
            // Unauthorized (401) or other errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}