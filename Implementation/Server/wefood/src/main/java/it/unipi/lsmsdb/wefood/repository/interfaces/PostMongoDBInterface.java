package it.unipi.lsmsdb.wefood.repository.interfaces;

import java.util.List;

import it.unipi.lsmsdb.wefood.model.Ingredient;
import it.unipi.lsmsdb.wefood.model.Post;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;

public interface PostMongoDBInterface {

    boolean uploadPost(RegisteredUser user, Post post);
    boolean modifyPost(Post post);
    boolean deletePost(RegisteredUser user, Post post);

    List<Post> browseMostRecentTopRatedPosts();
    List<Post> browseMostRecentTopRatedPosts(List<Ingredient> ingredients);
    List<Post> browseMostRecentPostsByCalories(Double minCalories, Double maxCalories);
    
    Post findPostById(String id);

}
