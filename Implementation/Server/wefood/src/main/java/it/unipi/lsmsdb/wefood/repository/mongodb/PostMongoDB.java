package it.unipi.lsmsdb.wefood.repository.mongodb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.MongoException;

import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.model.Comment;
import it.unipi.lsmsdb.wefood.model.StarRanking;
import it.unipi.lsmsdb.wefood.model.Post;
import it.unipi.lsmsdb.wefood.model.Recipe;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.repository.base.BaseMongoDB;
import it.unipi.lsmsdb.wefood.repository.interfaces.PostMongoDBInterface;


public class PostMongoDB implements PostMongoDBInterface{

    public String uploadPost(Post post, RegisteredUser user) throws MongoException, IllegalStateException, IllegalArgumentException {

        String query = "db.Post.insertOne({" + //
                            "idUser: " + user.editedGetId() + "," + //
                            "username: \"" + user.getUsername() + "\"," + //
                            "description: \"" + post.getDescription() + "\"," + //
                            "timestamp: " + post.getTimestamp().getTime() + "," + //
                            "recipe: {" + //
                                "name: \"" + post.getRecipe().getName() + "\"," + //
                                "image: \"" + post.getRecipe().getImage() +"\"," + //
                                "steps: " + post.getRecipe().stepsGetString() + "," + //
                                "totalCalories: " + post.getRecipe().getTotalCalories() + "," + //
                                "ingredients: " + post.getRecipe().ingredientsGetString() +  //
                            "}" + //
                       "})";
        
        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return result.get(0).get("_id").toString();
    }

    public boolean modifyPost(Post post, PostDTO postDTO) {

        String query = "db.Post.updateOne({" + //
                            "_id: " + postDTO.editedGetId() + //
                       "}, {" + //
                            "$set: {" + //
                                "description: \"" + post.getDescription() + "\"" + //
                            "}" + //
                       "})";

        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    }

    public boolean deletePost(PostDTO postDTO) {

        String query = "db.Post.deleteOne({" + //
                            "_id: " + postDTO.editedGetId() +  //
                       "})";

        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    }

    public List<PostDTO> browseMostRecentTopRatedPosts(long hours, int limit) {
        long curr_timestamp = System.currentTimeMillis();
        long milliseconds = hours * 3600000;
        long timestamp = curr_timestamp - milliseconds; 
        String query =  "db.Post.find({" + //
                                "timestamp: {" + //
                                "$gte: " + timestamp +  //
                            "}" + //
                        "}).sort({" + //
                            "avgStarRanking:-1" + //
                        "}).limit(" + limit + ")";
        List<Document> result = BaseMongoDB.executeQuery(query);

        List<PostDTO> posts = new ArrayList<>();
        
        for(Document doc : result){
            Document recipe = (Document) doc.get("recipe");
            String image = (recipe.get("image") == null) ? "DEFAULT" : recipe.get("image").toString();
            PostDTO post = new PostDTO(doc.getObjectId("_id").toHexString(), image, recipe.get("name").toString());
            posts.add(post);
        }

        return posts;
    }

    private String ingredientNamesToString(List<String> ingredientNames) {
        String str = "[";
        for(int i=0; i<ingredientNames.size(); i++){
            str += "'" + ingredientNames.get(i) + "'";
            if(i != ingredientNames.size()-1){
                str += ", ";
            }
        }
        str += "]";
        return str;
    }
    
    public List<PostDTO> browseMostRecentTopRatedPostsByIngredients(List<String> ingredientNames, long hours, int limit) {
        long curr_timestamp = System.currentTimeMillis();
        long milliseconds = hours * 3600000;
        long timestamp = curr_timestamp - milliseconds; 
        String query = "db.Post.find({" + //
                            "timestamp: {" + //
                                "$gte: " + timestamp +  //
                            "}," + //
                            "\"recipe.ingredients.name\": {" + //
                                "$all: " + ingredientNamesToString(ingredientNames) + //
                            "}" + //
                       "}).sort({" + //
                            "avgStarRanking: -1" + //
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
    }
    
    public List<PostDTO> browseMostRecentPostsByCalories(Double minCalories, Double maxCalories, long hours, int limit) {
        long curr_timestamp = System.currentTimeMillis();
        long milliseconds = hours * 3600000;
        long timestamp = curr_timestamp - milliseconds; 

        String query = "db.Post.find({" + //
                            "timestamp: {" + //
                                "$gte: " + timestamp + //
                            "}," + //
                            "\"recipe.totalCalories\": {" + //
                                "$gte: " + minCalories + "," + //
                                "$lte: " + maxCalories + //
                            "}" + //
                       "}).sort({" + //
                            "timestamp: -1" + //
                       "}).limit(" + limit + ")";

        List<Document> result = BaseMongoDB.executeQuery(query);
        //System.out.println(result.size());
        List<PostDTO> posts = new ArrayList<PostDTO>();
        
        for(Document doc : result){
            Document recipe = (Document) doc.get("recipe");
            String image = (recipe.get("image") == null) ? "DEFAULT" : recipe.get("image").toString();
            PostDTO post = new PostDTO(doc.get("_id").toString(), image, recipe.get("name").toString());
            posts.add(post);
        }  

        return posts;
    }
    
    public Post findPostById(String _id) {
        // I'm sure that _id exists, so the result will contain one and only one document
        String query = "db.Post.find({" + //
                            "_id: " + _id + //
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
        
        String image = (recipe_doc.get("image") == null) ? "DEFAULT" : recipe_doc.get("image").toString();
        Recipe recipe = new Recipe(recipe_doc.get("name").toString(), image, steps, ingredients, Double.parseDouble(recipe_doc.get("totalCalories").toString()));
        
        
        Post post = new Post(result.get("username").toString(), result.get("description").toString(), new Date(result.getLong("timestamp")), recipe);

        if(result.containsKey("comments"))
            for(Document comment_doc : (ArrayList<Document>) result.get("comments")){
                Comment comment = new Comment(comment_doc.get("username").toString(),
                                            comment_doc.get("text").toString(),
                                            new Date(comment_doc.getLong("timestamp")));
                post.addComment(comment);
            }

        if(result.containsKey("starRankings"))
            for(Document starRanking_doc : (ArrayList<Document>) result.get("starRankings")){
                StarRanking starRanking = new StarRanking(starRanking_doc.get("username").toString(),
                                                        Double.parseDouble(starRanking_doc.get("vote").toString()));
                post.addStarRanking(starRanking);
            }

        if(result.containsKey("avgStarRanking"))
            post.setAvgStarRanking(Double.parseDouble(result.get("avgStarRanking").toString()));
        else
            post.setAvgStarRanking(-1.0);

        return post;
    } 
    
    public Post findPostByPostDTO(PostDTO postDTO) {
        return findPostById(postDTO.editedGetId());
    }

    public List<PostDTO> findPostsByRecipeName(String recipeName) {
        String query = "db.Post.find( { \"recipe.name\": { $regex: \"" + recipeName + "\", $options: \"i\" } } ).limit(10)";

        List<Document> result = BaseMongoDB.executeQuery(query);
        List<PostDTO> posts = new ArrayList<PostDTO>();
        
        for(Document doc : result){
            Document recipe = (Document) doc.get("recipe"); 
            String image = (recipe.get("image") == null) ? "DEFAULT" : recipe.get("image").toString();
            PostDTO post = new PostDTO(doc.getObjectId("_id").toHexString(), image, recipe.get("name").toString());
            posts.add(post);
        }  

        return posts;
    }    

    // #1 Aggregation
    public Map<String, Double> interactionsAnalysis(){
        String query = "db.Post.aggregate([" + //
                            "{" + //
                                "$project: {" + //
                                    "_id: 1," + //
                                    "hasImage: {" + //
                                        "$cond: {" + //
                                            "if: {" + //
                                                "$eq: [{ $type: \"$recipe.image\" }, \"missing\"]" + //
                                            "}," + //
                                            "then: false," + //
                                            "else: true" + //
                                        "}" + //
                                    "}," + //
                                    "comments: {" + //
                                        "$size: {" + //
                                            "$ifNull: [\"$comments\", []]" + //
                                        "}" + //
                                    "}," + //
                                    "starRankings: {" + //
                                        "$size: {" + //
                                            "$ifNull: [\"$starRankings\", []]" + //
                                        "}" + //
                                    "}," + //
                                    "avgStarRanking: {" + //
                                        "$ifNull: [\"$avgStarRanking\", 0]" + //
                                    "}" + //
                                "}" + //
                            "}," + //
                            "{" + //
                                "$group: {" + //
                                    "_id: \"$hasImage\"," + //
                                    "numberOfPosts: {" + //
                                        "$sum: 1" + //
                                    "}," + //
                                    "totalComments: {" + //
                                        "$sum: \"$comments\"" + //
                                    "}," + //
                                    "totalStarRankings: {" + //
                                        "$sum: \"$starRankings\"" + //
                                    "}," + //
                                    "avgOfAvgStarRanking: {" + //
                                        "$avg: \"$avgStarRanking\"" + //
                                    "}" + //
                                "}" + //
                            "}," + //
                            "{" + //
                                "$project: {" + //
                                    "_id: 0," + //
                                    "hasImage: \"$_id\"," + //
                                    "ratioOfComments: {" + //
                                        "$divide: [\"$totalComments\", \"$numberOfPosts\"]" + //
                                    "}," + //
                                    "ratioOfStarRankings: {" + //
                                        "$divide: [\"$totalStarRankings\", \"$numberOfPosts\"]" + //
                                    "}," + //
                                    "avgOfAvgStarRanking: 1" + //
                                "}" + //
                            "}" + //
                       "])";

        List<Document> result = BaseMongoDB.executeQuery(query);
        
        Map<String, Double> interactions = new HashMap<String, Double>();

        for(Document analytics_doc : result){
            if(analytics_doc.getBoolean("hasImage")){
                interactions.put("IMAGEavgOfAvgStarRanking", Double.parseDouble(analytics_doc.get("avgOfAvgStarRanking").toString()));
                interactions.put("IMAGEratioOfComments", Double.parseDouble(analytics_doc.get("ratioOfComments").toString()));
                interactions.put("IMAGEratioOfStarRankings", Double.parseDouble(analytics_doc.get("ratioOfStarRankings").toString()));
            }
            else{
                interactions.put("NOIMAGEavgOfAvgStarRanking", Double.parseDouble(analytics_doc.get("avgOfAvgStarRanking").toString()));
                interactions.put("NOIMAGEratioOfComments", Double.parseDouble(analytics_doc.get("ratioOfComments").toString()));
                interactions.put("NOIMAGEratioOfStarRankings", Double.parseDouble(analytics_doc.get("ratioOfStarRankings").toString()));
            }
        }  

        return interactions; 
    }

    // #2 Aggregation
    public Map<String, Double> userInteractionsAnalysis(String username){
        String query = "db.Post.aggregate([" + //
                            "{" + //
                                "$match: {" + //
                                    "$or: [" + //
                                        "{\"comments.username\": \"" + username + "\"}," + //
                                        "{\"starRankings.username\": \"" + username + "\"}" + //
                                    "]" + //
                                "}" + //
                            "}," + //
                            "{" + //
                                "$project: {" + //
                                    "filteredComments: {" + //
                                        "$filter: {" + //
                                            "input: \"$comments\"," + //
                                            "as: \"comment\"," + //
                                            "cond: {$eq: [\"$$comment.username\", \"" + username + "\"]}" + //
                                        "}" + //
                                    "}," + //
                                    "filteredStarRankings: {" + //
                                        "$filter: {" + //
                                            "input: \"$starRankings\"," + //
                                            "as: \"starRanking\"," + //
                                            "cond: {$eq: [\"$$starRanking.username\", \"" + username + "\"]}" + //
                                        "}" + //
                                    "}" + //
                                "}" + //
                            "}," + //
                            "{" + //
                                "$group: {" + //
                                    "_id: null," + //
                                    "avgOfStarRankings: {" + //
                                        "$avg: {$sum: \"$filteredStarRankings.vote\"}" + //
                                    "}," + //
                                    "numberOfStarRankings: {" + //
                                        "$sum: {$size: \"$filteredStarRankings\"}" + //
                                    "}," + //
                                    "numberOfComments: {" + //
                                        "$sum: {$size: \"$filteredComments\"}" + //
                                    "}" + //
                                "}" + //
                            "}," + //
                            "{" + //
                                "$project: {" + //
                                    "_id: 0," + //
                                    "numberOfComments: 1," + //
                                    "numberOfStarRankings: 1," + //
                                    "avgOfStarRankings: 1" + //
                                "}" + //
                            "}" + //
                       "])";

        List<Document> result = BaseMongoDB.executeQuery(query);
        if(result.isEmpty())
            return null;
        
        Map<String, Double> interactions = new HashMap<String, Double>();

        Document analytics_doc = result.get(0);

        interactions.put("avgOfStarRankings", Double.parseDouble(analytics_doc.get("avgOfStarRankings").toString()));
        interactions.put("numberOfStarRankings", Double.parseDouble(analytics_doc.get("numberOfStarRankings").toString()));
        interactions.put("numberOfComments", Double.parseDouble(analytics_doc.get("numberOfComments").toString()));        
        
        return interactions;
    }

    // #3 Aggregation
    public Double caloriesAnalysis(String recipeName){
        String query = "db.Post.aggregate([" + //
                            "{" + //
                                "$match: {" + //
                                    "\"recipe.name\": { $regex: \"" + recipeName + "\", $options: \"i\" }" + //
                                "}" + //
                            "}," + //
                            "{" + //
                                "$sort: {" + //
                                    "avgStarRanking: -1" + //
                                "}" + //
                            "}," + //
                            "{" + //
                                "$limit: 10" + //
                            "}," + //
                            "{" + //
                                "$group: {" + //
                                    "_id: null," + //
                                    "avgOfTotalCalories: {" + //
                                        "$avg: \"$recipe.totalCalories\"" + //
                                    "}" + //
                                "}" + //
                            "}," + //
                            "{" + //
                                "$project: {" + //
                                    "_id: 0," + //
                                    "avgOfTotalCalories: 1" + //
                                "}" + //
                            "}" + //
                       "])";

        List<Document> result = BaseMongoDB.executeQuery(query);
        if(result.isEmpty())
            return null;
        
        Document analytics_doc = result.get(0);

        return Double.parseDouble(analytics_doc.get("avgOfTotalCalories").toString());
    }


    public Double averageTotalCaloriesByUser(String username){
        String query = "db.Post.aggregate([ " + //
                            "{ " + //
                                "$match: { " + //
                                    "username: \"" + username + "\" " + //
                                "} " + //
                            "}, " + //
                            "{   $project: { " + //
                                    "recipeCalories: \"$recipe.totalCalories\" " + //
                                "} " + //
                            "}, " + //
                            "{   $group: { " + //
                                    "_id: null, " + //
                                    "avgCalories: { " + //
                                        "$avg: \"$recipeCalories\" " + //
                                    "} " + //
                                "} " + //
                            "}" + //
                       "])";

        List<Document> result = BaseMongoDB.executeQuery(query);
        if(result.isEmpty())
            return null;
        
        Document analytics_doc = result.get(0);

        return Double.parseDouble(analytics_doc.get("avgCalories").toString());
    }
    
}
