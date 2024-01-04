package it.unipi.lsmsdb.wefood.repository.mongodb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.MongoException;

import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.model.Ingredient;
import it.unipi.lsmsdb.wefood.model.Post;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.repository.base.BaseMongoDB;
import it.unipi.lsmsdb.wefood.repository.interfaces.PostMongoDBInterface;

public class PostMongoDB implements PostMongoDBInterface{

    public boolean uploadPost(Post post, RegisteredUser user) throws MongoException, IllegalStateException, IllegalArgumentException {

        String query = "db.Post.insertOne({\r\n" + //
                       "    idUser: " + user.getId() + ",\r\n" + //
                       "    username: " + user.getUsername() + ",\r\n" + //
                       "    description: " + post.getDescription() + ",\r\n" + //
                       "    timestamp: " + post.getTimestamp().getTime() + ",\r\n" + //
                       "    recipe: {\r\n" + //
                       "                name: " + post.getRecipe().getName() + ",\r\n" + //
                       "                image: " + post.getRecipe().getImage() +",\r\n" + //
                       "                steps: " + post.getRecipe().getStepsString() + ",\r\n" + //
                       "                totalCalories: " + post.getRecipe().getTotalCalories() + ",\r\n" + //
                       "                ingredients: " + post.getRecipe().getIngredientsString() + "\r\n" + //
                       "    }\r\n" + //
                       "})";
        
        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    };

    public boolean modifyPost(Post post, PostDTO postDTO) {

        String query = "db.Post.updateOne({\r\n" + //
                       "    _id: " + postDTO.getId() + ",\r\n" + //
                       "}, {\r\n" + //
                       "    $set: {\r\n" + //
                       "        description: " + post.getDescription() + ",\r\n" + //
                       "    }\r\n" + //
                       "})";

        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    };

    public boolean deletePost(PostDTO postDTO) {

        String query = "db.Post.deleteOne({\r\n" + //
                       "    _id: " + postDTO.getId() + ",\r\n" + //
                       "})";

        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    };

    public List<PostDTO> browseMostRecentTopRatedPosts(int hours, int limit) {
        long curr_timestamp = System.currentTimeMillis();
        long milliseconds = hours * 3600000;
        long timestamp = curr_timestamp - milliseconds; 
        String query = "db.Post.find({\r\n" + //
                       "    timestamp: {\r\n" + //
                       "        $gte: " + timestamp + "\r\n" + //
                       "    }\r\n" + //
                       "}).sort({\r\n" + //
                       "    avgStarRanking: -1\r\n" + //
                       "}).limit(" + limit + ")";
        List<Document> result = BaseMongoDB.executeQuery(query);
        List<PostDTO> posts = new ArrayList<PostDTO>();
        
        for(Document doc : result){
            Document recipe = (Document) doc.get("recipe");
            PostDTO post = new PostDTO(doc.get("_id").toString(), recipe.get("image").toString(), recipe.get("name").toString());
            posts.add(post);
        }  

        return posts;
    }; 

    
    public List<PostDTO> browseMostRecentTopRatedPostsByIngredients(List<Ingredient> ingredients) {
        String query = "";
        List<Document> result = BaseMongoDB.executeQuery(query);
        
    };
    public List<PostDTO> browseMostRecentPostsByCalories(Double minCalories, Double maxCalories) {
        String query = "";
        List<Document> result = BaseMongoDB.executeQuery(query);
        
    };
    
    public Post findPostById(String _id) {
        String query = "";
        List<Document> result = BaseMongoDB.executeQuery(query);
        
    }; //Dovrebbe essere by user per fare delete e modify
    //altrimenti non hai l'informazione sull'id fino a quando un utente non effettua un operazione di
    //modifica o cancellazione


    public static String getIngredientsString(Map<Ingredient, Double> ingredients) {
        String str = "[";
        int i = 0;
        for (Map.Entry<Ingredient, Double> entry : ingredients.entrySet()) {
            Ingredient ingredient = entry.getKey();
            Double quantity = entry.getValue();
            str += "{name: '" + ingredient.getName() + "', quantity: " + quantity + "}";
            if(i != ingredients.size()-1){
                str += ", ";
            }
            i++;
        }
        str += "]";
        return str;
    }

    public static void main(String[] args) {
        // Map<Ingredient, Double> ingredients = new HashMap<Ingredient, Double>();
        // ingredients.put(new Ingredient("Pippo", 21.0), 12.2);
        // ingredients.put(new Ingredient("TOPOLINO!", 2231.0), 15.2);

        // System.out.println(getIngredientsString(ingredients)); 

        String query = "db.Post.find({\r\n" + //
                       "    _id: ObjectId(\"658572b7d312a33aeb784cfc\")" + //
                       "})";
        List<Document> result = BaseMongoDB.executeQuery(query);
//        List<PostDTO> posts = new ArrayList<PostDTO>();
        
        for(Document doc : result){
            Document recipe = (Document) doc.get("recipe");
//            PostDTO post = new PostDTO(doc.get("_id").toString(), recipe.get("image").toString(), recipe.get("name").toString());
            System.out.println(doc.get("_id").toString());
            System.out.println(recipe.get("image").toString());
            System.out.println(recipe.get("name").toString());
//            posts.add(post);
        }

    }

}
