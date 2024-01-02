package it.unipi.lsmsdb.wefood.repository.interfaces;

import java.util.List;

import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.model.Post;
//import it.unipi.lsmsdb.wefood.model.RegisteredUser;

public interface PostMongoDBInterface {

    boolean uploadPost(Post post);
    boolean modifyPost(Post post);
    boolean deletePost(Post post);

    List<PostDTO> browseMostRecentTopRatedPosts();
    List<PostDTO> browseMostRecentTopRatedPostsByIngredients(List<IngredientDTO> ingredientDTOs);
    List<PostDTO> browseMostRecentPostsByCalories(Double minCalories, Double maxCalories);
    
    Post findPostByPostDTO(PostDTO postDTO);

}
