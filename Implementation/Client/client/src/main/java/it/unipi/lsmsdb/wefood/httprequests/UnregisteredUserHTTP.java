package it.unipi.lsmsdb.wefood.httprequests;

import com.fasterxml.jackson.databind.ObjectMapper;


import it.unipi.lsmsdb.wefood.apidto.UnregisteredUserRequestDTO;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;

import java.net.http.HttpResponse;

public class UnregisteredUserHTTP{
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BaseHttpRequest baseHttpRequest = new BaseHttpRequest();

    public RegisteredUser register(UnregisteredUserRequestDTO request){
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("unregistereduser/register", requestBody);
            if(result.statusCode() == 200 && !result.body().isEmpty())
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
}