package it.unipi.lsmsdb.wefood;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unipi.lsmsdb.wefood.model.Admin;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) {
        try {
            Admin admin = new Admin("admin", "passworddd");

            // Crea un client HTTP
            HttpClient client = HttpClient.newHttpClient();

            Boolean obj_to_send = true;

            // Usa Jackson per convertire l'oggetto admin in una stringa JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(obj_to_send);

            // Crea una richiesta POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/ingredient/getAllIngredients")) // Sostituisci con il tuo URL effettivo
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            System.out.println(requestBody);
            System.out.println(obj_to_send);

            // Invia la richiesta e ricevi la risposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Stampa il corpo della risposta
            System.out.println("Response body:");
            System.out.println(response.statusCode());
            System.out.println(response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}