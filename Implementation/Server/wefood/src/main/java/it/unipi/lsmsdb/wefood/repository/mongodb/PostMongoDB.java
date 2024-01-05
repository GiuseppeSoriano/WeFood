package it.unipi.lsmsdb.wefood.repository.mongodb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.MongoException;

import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.model.Comment;
import it.unipi.lsmsdb.wefood.model.StarRanking;
import it.unipi.lsmsdb.wefood.model.Post;
import it.unipi.lsmsdb.wefood.model.Recipe;
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

    public List<PostDTO> browseMostRecentTopRatedPosts(long hours, int limit) {
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
        System.out.println(result.size());
        List<PostDTO> posts = new ArrayList<PostDTO>();
        
        for(Document doc : result){
            Document recipe = (Document) doc.get("recipe");
            String image = (recipe.get("image") == null) ? "DEFAULT" : recipe.get("image").toString();
            PostDTO post = new PostDTO(doc.get("_id").toString(), image, recipe.get("name").toString());
            posts.add(post);
        }  

        return posts;
    }; 

    private String ingredientDTOsToString(List<IngredientDTO> ingredientDTOs) {
        String str = "[";
        for(int i=0; i<ingredientDTOs.size(); i++){
            str += "'" + ingredientDTOs.get(i).getName() + "'";
            if(i != ingredientDTOs.size()-1){
                str += ", ";
            }
        }
        str += "]";
        return str;
    }
    
    public List<PostDTO> browseMostRecentTopRatedPostsByIngredients(List<IngredientDTO> ingredientDTOs, long hours, int limit) {
        long curr_timestamp = System.currentTimeMillis();
        long milliseconds = hours * 3600000;
        long timestamp = curr_timestamp - milliseconds; 
        String query = "db.Post.find({\r\n" + //
                       "    timestamp: {\r\n" + //
                       "        $gte: " + timestamp + "\r\n" + //
                       "    },\r\n" + //
                       "    \"recipe.ingredients.name\": {\r\n" + //
                       "        $all: " + ingredientDTOsToString(ingredientDTOs) + "\r\n" + //
                       "    }\r\n" + //
                       "}).sort({\r\n" + //
                       "    avgStarRanking: -1\r\n" + //
                       "}).limit(" + limit + ")";
        List<Document> result = BaseMongoDB.executeQuery(query);
        List<PostDTO> posts = new ArrayList<PostDTO>();
        
        for(Document doc : result){
            Document recipe = (Document) doc.get("recipe");
            String image = (recipe.get("image") == null) ? "DEFAULT" : recipe.get("image").toString();
            PostDTO post = new PostDTO(doc.get("_id").toString(), image, recipe.get("name").toString());
            posts.add(post);
        }  

        return posts;
    };
    
    public List<PostDTO> browseMostRecentPostsByCalories(Double minCalories, Double maxCalories, long hours, int limit) {
        long curr_timestamp = System.currentTimeMillis();
        long milliseconds = hours * 3600000;
        long timestamp = curr_timestamp - milliseconds; 

        String query = "db.Post.find({\r\n" + //
                       "    timestamp: {\r\n" + //
                       "        $gte: " + timestamp + "\r\n" + //
                       "    },\r\n" + //
                       "    \"recipe.totalCalories\": {\r\n" + //
                       "        $gte: " + minCalories + ",\r\n" + //
                       "        $lte: " + maxCalories + "\r\n" + //
                       "    }\r\n" + //
                       "}).sort({\r\n" + //
                       "    timestamp: -1\r\n" + //
                       "}).limit(" + limit + ")";

        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.size());
        List<PostDTO> posts = new ArrayList<PostDTO>();
        
        for(Document doc : result){
            Document recipe = (Document) doc.get("recipe");
            String image = (recipe.get("image") == null) ? "DEFAULT" : recipe.get("image").toString();
            PostDTO post = new PostDTO(doc.get("_id").toString(), image, recipe.get("name").toString());
            posts.add(post);
        }  

        return posts;
    };
    
    public Post findPostById(String _id) {
        // I'm sure that _id exists, so the result will contain one and only one document
        String query = "db.Post.find({\r\n" + //
                       "    _id: " + _id + ",\r\n" + //
                       "})";
        Document result = BaseMongoDB.executeQuery(query).get(0);

        Document recipe_doc = (Document) result.get("recipe");
        
        Map<String, Double> ingredients = new HashMap<String, Double>();
        for(Document ingredient_doc : (ArrayList<Document>) recipe_doc.get("ingredients")){
            double quantity = Double.parseDouble(ingredient_doc.get("quantity").toString());
            ingredients.put(ingredient_doc.get("name").toString(), quantity);
        }

        List<String> steps = new ArrayList<String>();
        steps = recipe_doc.getList("steps", String.class);
        System.out.println(steps.get(0));
        System.out.println(steps.get(1));
        //for(Document step : ){
        //    steps.add(step.toString());
        //}

        String image = (recipe_doc.get("image") == null) ? "DEFAULT" : recipe_doc.get("image").toString();
        Recipe recipe = new Recipe(recipe_doc.get("name").toString(), image, steps, ingredients, Double.parseDouble(recipe_doc.get("totalCalories").toString()));
        
        
        Post post = new Post(result.get("username").toString(), result.get("description").toString(), new Date(result.getLong("timestamp")), recipe);

        for(Document comment_doc : (ArrayList<Document>) result.get("comments")){
            Comment comment = new Comment(comment_doc.get("username").toString(),
                                          comment_doc.get("text").toString(),
                                          new Date(comment_doc.getLong("timestamp")));
            post.addComment(comment);
        }

        for(Document starRanking_doc : (ArrayList<Document>) result.get("starRankings")){
            StarRanking starRanking = new StarRanking(starRanking_doc.get("username").toString(),
                                                      Double.parseDouble(starRanking_doc.get("vote").toString()));
            post.addStarRanking(starRanking);
        }

        return post;
    }; 
    
    public Post findPostByPostDTO(PostDTO postDTO) {
        return findPostById(postDTO.getId());
    };

}
