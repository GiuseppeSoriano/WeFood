package it.unipi.lsmsdb.wefood.httprequests;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.unipi.lsmsdb.wefood.apidto.StarRankingRequestDTO;

import java.net.http.HttpResponse;

public class StarRankingHTTP{
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BaseHttpRequest baseHttpRequest = new BaseHttpRequest();

    public Boolean votePost(StarRankingRequestDTO request) {
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("starranking/create", requestBody);
            if(result.statusCode() == 200 && !result.body().isEmpty())
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

    public Boolean deleteVote(StarRankingRequestDTO request) {
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("starranking/delete", requestBody);
            if(result.statusCode() == 200 && !result.body().isEmpty())
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

}