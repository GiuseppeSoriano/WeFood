package it.unipi.lsmsdb.wefood.dao;

import it.unipi.lsmsdb.wefood.model.Post;
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
    
    public boolean modifyPost(Post post) {
        return this.postMongoDB.modifyPost(post);
    }

    public boolean deletePost(RegisteredUser user, Post post) {
        return this.postMongoDB.deletePost(user, post);
    }

    public List<Post> browseMostRecentTopRatedPosts() {
        return this.postMongoDB.browseMostRecentTopRatedPosts();
    }

    public List<Post> browseMostRecentTopRatedPostsByIngredients(List<Ingredient> ingredients) {
        return this.postMongoDB.browseMostRecentTopRatedPostsByIngredients(ingredients);
    }

    public List<Post> browseMostRecentPostsByCalories(Double minCalories, Double maxCalories) {
        return this.postMongoDB.browseMostRecentPostsByCalories(minCalories, maxCalories);
    }

    public Post findPostById(String id) {
        return this.postMongoDB.findPostById(id);
    }
}
