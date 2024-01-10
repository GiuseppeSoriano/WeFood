package it.unipi.lsmsdb.wefood.service;

import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.model.Post;

import java.util.List;

public class RecipeImageService {


    private static final String LOCAL_RECIPES_IMAGES_PATH = "RecipesImages/";
    

    private String localImageConverter(String localPath) {
        
        try{
            imagePath = LOCAL_RECIPES_IMAGES_PATH + localPath;
            InputStream inputStream = RecipeImageService.class.getClassLoader().getResourceAsStream(imagePath);

            if (inputStream == null) {
                // Image not found
                
                // We try to return
f eht
                
            }


        }
        catch(IOException e){
            System.out.println("IOException in localImageCoverter: " + e);
        }




    }

    public List<PostDTO> postDTOconverter(List<PostDTO> posts) {
        for (PostDTO postDTO : posts) {
            postDTO.setImage(localImageConverter(postDTO.getImage()));
        }
        return postDTOs;
    }

    public Post postConverter(Post post) {
        post.setImage(localImageConverter(post.getImage()));
        return post;
    }
}
