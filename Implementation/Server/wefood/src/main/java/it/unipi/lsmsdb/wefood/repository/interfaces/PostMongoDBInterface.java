package it.unipi.lsmsdb.wefood.repository.interfaces;

import java.util.List;

import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.model.Post;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;

public interface PostMongoDBInterface {

    boolean uploadPost(Post post, RegisteredUser user);
    boolean modifyPost(Post post, PostDTO postDTO);
    boolean deletePost(PostDTO postDTO);

    List<PostDTO> browseMostRecentTopRatedPosts(int hours, int limit);
    List<PostDTO> browseMostRecentTopRatedPostsByIngredients(List<IngredientDTO> ingredientDTOs);
    List<PostDTO> browseMostRecentPostsByCalories(Double minCalories, Double maxCalories);
    
    Post findPostByPostDTO(PostDTO postDTO);

}
