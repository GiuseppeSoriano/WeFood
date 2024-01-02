package it.unipi.lsmsdb.wefood.repository.mongodb;

import java.util.List;

import org.bson.Document;

import it.unipi.lsmsdb.wefood.model.Ingredient;
import it.unipi.lsmsdb.wefood.model.Post;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.repository.base.BaseMongoDB;
import it.unipi.lsmsdb.wefood.repository.interfaces.PostMongoDBInterface;

public class PostMongoDB implements PostMongoDBInterface{
    private final String collectionName = "Post";

    public boolean uploadPost(RegisteredUser user, Post post) {
        String query = "db."+collectionName+".insertOne({username: \"" + user.getUsername() +
                                            "\", description: \"" + post.getDescription() +
                                            "\", timestamp: \"" + post.getTimestamp().toString() +
                                            "\", surname: \"" + post.getRecipe().toString() + "\"})"; //aggiungere recipe toString()
        //BaseMongoDB.getMongoClient();
        List<Document> result = BaseMongoDB.executeQuery(query);
        if(result == null) {
            return false;
        } else {
            return true;
        }
    };
    public boolean modifyPost(Post post) {};
    public boolean deletePost(RegisteredUser user, Post post) {};

    public List<Post> browseMostRecentTopRatedPosts() {}; //da rivedere la documentazione nella sezione show
    public List<Post> browseMostRecentTopRatedPostsByIngredients(List<Ingredient> ingredients) {};
    public List<Post> browseMostRecentPostsByCalories(Double minCalories, Double maxCalories) {};
    
    public Post findPostById(String id) {}; //Dovrebbe essere by user per fare delete e modify
    //altrimenti non hai l'informazione sull'id fino a quando un utente non effettua un operazione di
    //modifica o cancellazione
}
