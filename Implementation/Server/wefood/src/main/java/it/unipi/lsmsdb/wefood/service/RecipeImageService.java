package it.unipi.lsmsdb.wefood.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.util.Base64;
import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.model.Post;

public class RecipeImageService {

    private static final String LOCAL_RECIPES_IMAGES_PATH = "RecipesImages/";
    private static final String DEFAULT_IMAGE_PATH = LOCAL_RECIPES_IMAGES_PATH + "Default/WeFood.jpg";
    
    public String storePostImage(Post post){
        String username = post.getUsername();
        String timestamp = "time_"+post.getTimestamp().getTime();
        String image = post.getRecipe().getImage();
        if(image == null)
            return null;
        
        String localPath = username + "/" + timestamp + "/" + "image.jpg";
        String relativePath = LOCAL_RECIPES_IMAGES_PATH + localPath;
        
        try {
            // Use the class loader to get the resources directory
            ClassLoader classLoader = RecipeImageService.class.getClassLoader();
            File resourcesDirectory = new File(classLoader.getResource("").getFile());

            // Create the output file within the resources directory
            File outputFile = new File(resourcesDirectory, relativePath);

            // We need to convert the image from Base64 to bytes
            byte[] imageBytes = Base64.getDecoder().decode(image);

            // Save the image to the file
            FileUtils.writeByteArrayToFile(outputFile, imageBytes);
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        return localPath;
    }

    private String getWebImage(String webPath) {

        try{
            InputStream inputStream = new java.net.URL(webPath).openStream();
            if (inputStream == null) {
                // Web Image not found
                System.out.println("User Image not found. Default image will be returned.");

                // We try to return the default one before giving up
                return getLocalImage("DEFAULT");
            }

            byte[] imageBytes = IOUtils.toByteArray(inputStream);

            // Encode the image bytes to Base64
            String encodedImage = Base64.getEncoder().encodeToString(imageBytes);


            return encodedImage;
        }
        catch(IOException e){
            System.out.println("IOException in getWebImage: " + e);
            // We try to return the default one before giving up
            return getLocalImage("DEFAULT");
        }
    }

    private String getLocalImage(String localPath) {

        try{
            String imagePath;
            if(localPath.equals("DEFAULT"))
                imagePath = DEFAULT_IMAGE_PATH;
            else
                imagePath = LOCAL_RECIPES_IMAGES_PATH + localPath;

            InputStream inputStream = RecipeImageService.class.getClassLoader().getResourceAsStream(imagePath);

            if (inputStream == null) {
                // Image not found
                System.out.println("User Image not found. Default image will be returned.");

                // We try to return the default one before giving up
                imagePath = DEFAULT_IMAGE_PATH;
                inputStream = RecipeImageService.class.getClassLoader().getResourceAsStream(imagePath);
                if(inputStream == null){
                    // Default image not found
                    System.err.println("Default image not found. Returning null.");
                    return null;
                }
            }

            byte[] imageBytes = IOUtils.toByteArray(inputStream);

            // Encode the image bytes to Base64
            String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
            
            return encodedImage;
        }
        catch(IOException e){
            System.out.println("IOException in localImageCoverter: " + e);
            return null;
        }
    }

    public List<PostDTO> postDTOconverter(List<PostDTO> posts) {
        // if string starts with "http" 

        for (PostDTO postDTO : posts) {
            if(postDTO.getImage().startsWith("http"))
                postDTO.setImage(getWebImage(postDTO.getImage()));
            else
                postDTO.setImage(getLocalImage(postDTO.getImage()));
        }
        return posts;
    }

    public Post postConverter(Post post) {
        if(post.getRecipe().getImage().startsWith("http"))
            post.getRecipe().setImage(getWebImage(post.getRecipe().getImage()));
        else    
            post.getRecipe().setImage(getLocalImage(post.getRecipe().getImage()));
        return post;
    } 


    public static void main(String[] args) {
        // https://img.sndimg.com/food/image/upload/w_555,h_416,c_fit,fl_progressive,q_95/v1/img/recipes/19/44/91/3xjO4aXTeiZpOajAsBRX_0S9A6246.jpg
        RecipeImageService recipeImageService = new RecipeImageService();
        // String temp = recipeImageService.getWebImage("https://img.sndimg.com/food/image/upload/w_555,h_416,c_fit,fl_progressive,q_95/v1/img/recipes/19/44/91/3xjO4aXTeiZpOajAsBRX_0S9A6246.jpg");

        // System.out.println(recipeImageService.storePostImage("Pippo", "123", temp));
        System.out.println(recipeImageService.getLocalImage("Pippo/123/image.jpg"));
    }

}
