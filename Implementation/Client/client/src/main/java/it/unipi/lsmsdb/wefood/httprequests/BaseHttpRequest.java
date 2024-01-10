package it.unipi.lsmsdb.wefood.httprequests;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BaseHttpRequest{

    private final String SERVER_ADDRESS = "http://localhost:8080/";
    private final HttpClient client = HttpClient.newHttpClient();

    public HttpResponse<String> sendRequest(String path, String requestBody){
        try{
            // Crea una richiesta POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SERVER_ADDRESS+path))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // Invia la richiesta e ricevi la risposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            // Stampa il corpo della risposta
            // System.out.println(response.statusCode());
            // System.out.println(response.body());
            
            return response;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}