package it.unipi.lsmsdb.wefood.httprequests;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.unipi.lsmsdb.wefood.apidto.CommentRequestDTO;

import java.net.http.HttpResponse;

public class CommentHTTP{
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BaseHttpRequest baseHttpRequest = new BaseHttpRequest();

    public Boolean commentPost(CommentRequestDTO request){
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("comment/create", requestBody);
            if(result.statusCode() == 200)
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

    public Boolean updateComment(CommentRequestDTO request){
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("comment/update", requestBody);
            if(result.statusCode() == 200)
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

    public Boolean deleteComment(CommentRequestDTO request){
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("comment/delete", requestBody);
            if(result.statusCode() == 200)
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