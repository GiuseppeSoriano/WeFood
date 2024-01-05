package it.unipi.lsmsdb.wefood.dao;

import it.unipi.lsmsdb.wefood.model.Post;
import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.model.Ingredient;
import it.unipi.lsmsdb.wefood.repository.mongodb.PostMongoDB;

import java.util.List;

public class PostDAO {
    PostMongoDB postMongoDB;

    public PostDAO() {
        this.postMongoDB = new PostMongoDB();
    }

    public boolean uploadPost(Post post, RegisteredUser user) {
        return this.postMongoDB.uploadPost(post, user);
    }
    
    public boolean modifyPost(Post post, PostDTO postDTO) {
        return this.postMongoDB.modifyPost(post, postDTO);
    }

    public boolean deletePost(PostDTO postDTO) {
        return this.postMongoDB.deletePost(postDTO);
    }

    public List<PostDTO> browseMostRecentTopRatedPosts(long timestamp, int limit) {
        return this.postMongoDB.browseMostRecentTopRatedPosts(timestamp, limit);
    }

    public List<PostDTO> browseMostRecentTopRatedPostsByIngredients(List<IngredientDTO> ingredients, long timestamp, int limit) {
        return this.postMongoDB.browseMostRecentTopRatedPostsByIngredients(ingredients, timestamp, limit);
    }

    public List<PostDTO> browseMostRecentPostsByCalories(Double minCalories, Double maxCalories, long timestamp, int limit) {
        return this.postMongoDB.browseMostRecentPostsByCalories(minCalories, maxCalories, timestamp, limit);
    }

    public Post findPostById(String id) {
        return this.postMongoDB.findPostById(id);
    }
}
