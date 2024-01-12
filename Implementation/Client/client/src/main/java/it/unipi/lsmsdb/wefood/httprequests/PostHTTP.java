package it.unipi.lsmsdb.wefood.httprequests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.unipi.lsmsdb.wefood.apidto.PostByCaloriesRequestDTO;
import it.unipi.lsmsdb.wefood.apidto.PostByIngredientsRequestDTO;
import it.unipi.lsmsdb.wefood.apidto.PostRequestDTO;
import it.unipi.lsmsdb.wefood.apidto.PostTopRatedRequestDTO;
import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.dto.RecipeDTO;
import it.unipi.lsmsdb.wefood.model.Post;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class PostHTTP{
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BaseHttpRequest baseHttpRequest = new BaseHttpRequest();
    
    public Boolean uploadPost(PostRequestDTO request){
        Post post =  request.getPost();
        // modifica post 
        // in image ho path e metto image64
        request.setPost(post);
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("post/uploadPost", requestBody);
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

    public Boolean modifyPost(PostRequestDTO request){
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("post/modifyPost", requestBody);
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

    public Boolean deletePost(PostRequestDTO request){
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("post/deletePost", requestBody);
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

    public List<PostDTO> browseMostRecentTopRatedPosts(PostTopRatedRequestDTO request){
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("post/browseMostRecentTopRatedPosts", requestBody);
            if(result.statusCode() == 200){
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), new TypeReference<List<PostDTO>>(){});}
            // Unauthorized (401) or other errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<PostDTO> browseMostRecentTopRatedPostsByIngredients(PostByIngredientsRequestDTO request){
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("post/browseMostRecentTopRatedPostsByIngredients", requestBody);
            if(result.statusCode() == 200){
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), new TypeReference<List<PostDTO>>(){});}
            // Unauthorized (401) or other errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<PostDTO> browseMostRecentPostsByCalories(PostByCaloriesRequestDTO request){
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("post/browseMostRecentPostsByCalories", requestBody);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), new TypeReference<List<PostDTO>>(){});
            // Unauthorized (401) or other errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Post findPostByPostDTO(PostDTO request){
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("post/findPostByPostDTO", requestBody);
            if(result.statusCode() == 200){
                // HTTP 200 OK

                return objectMapper.readValue(result.body(), Post.class);}
            // Unauthorized (401) or other errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Post findPostById(String request){
        try{
            HttpResponse<String> result = baseHttpRequest.sendRequest("post/findPostById", request);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), Post.class);
            // Unauthorized (401) or other errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<PostDTO> findPostsByRecipeName(String request){
        try{
            HttpResponse<String> result = baseHttpRequest.sendRequest("post/findPostsByRecipeName", request);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), new TypeReference<List<PostDTO>>(){});
            // Unauthorized (401) or other errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Double> interactionsAnalysis(){
        try{
            HttpResponse<String> result = baseHttpRequest.sendRequest("post/interactionsAnalysis", "");
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), new TypeReference<Map<String, Double>>(){});
            // Unauthorized (401) or other errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Double> userInteractionsAnalysis(String request){
        try{
            HttpResponse<String> result = baseHttpRequest.sendRequest("post/userInteractionsAnalysis", request);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), new TypeReference<Map<String, Double>>(){});
            // Unauthorized (401) or other errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Double caloriesAnalysis(String request){
        try{
            HttpResponse<String> result = baseHttpRequest.sendRequest("post/caloriesAnalysis", request);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), Double.class);
            // Unauthorized (401) or other errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Double averageTotalCaloriesByUser(String request){
        try{
            HttpResponse<String> result = baseHttpRequest.sendRequest("post/averageTotalCaloriesByUser", request);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), Double.class);
            // Unauthorized (401) or other errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<RecipeDTO> findRecipeByIngredients(List<String> request){
        try{
            String requestBody = objectMapper.writeValueAsString(request);
            HttpResponse<String> result = baseHttpRequest.sendRequest("post/findRecipeByIngredients", requestBody);
            if(result.statusCode() == 200)
                // HTTP 200 OK
                return objectMapper.readValue(result.body(), new TypeReference<List<RecipeDTO>>(){});
            // Unauthorized (401) or other errors
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}