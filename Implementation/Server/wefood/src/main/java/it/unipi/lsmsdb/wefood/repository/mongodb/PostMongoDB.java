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

    public String uploadPost(Post post, RegisteredUser user) throws MongoException, IllegalStateException, IllegalArgumentException {

        String query = "db.Post.insertOne({\r\n" + //
                       "    idUser: " + user.getId() + ",\r\n" + //
                       "    username: \"" + user.getUsername() + "\",\r\n" + //
                       "    description: \"" + post.getDescription() + "\",\r\n" + //
                       "    timestamp: " + post.getTimestamp().getTime() + ",\r\n" + //
                       "    recipe: {\r\n" + //
                       "                name: \"" + post.getRecipe().getName() + "\",\r\n" + //
                       "                image: \"" + post.getRecipe().getImage() +"\",\r\n" + //
                       "                steps: " + post.getRecipe().getStepsString() + ",\r\n" + //
                       "                totalCalories: " + post.getRecipe().getTotalCalories() + ",\r\n" + //
                       "                ingredients: " + post.getRecipe().getIngredientsString() + "\r\n" + //
                       "    }\r\n" + //
                       "})";
        
        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return result.get(0).get("_id").toString();
    }

    public boolean modifyPost(Post post, PostDTO postDTO) {

        String query = "db.Post.updateOne({\r\n" + //
                       "    _id: " + postDTO.getId() + ",\r\n" + //
                       "}, {\r\n" + //
                       "    $set: {\r\n" + //
                       "        description: \"" + post.getDescription() + "\",\r\n" + //
                       "    }\r\n" + //
                       "})";

        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    }

    public boolean deletePost(PostDTO postDTO) {

        String query = "db.Post.deleteOne({\r\n" + //
                       "    _id: " + postDTO.getId() + ",\r\n" + //
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
    }
    
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
    }
    
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
        //System.out.println(steps.get(0));
        //System.out.println(steps.get(1));
        //for(Document step : ){
        //    steps.add(step.toString());
        //}

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
        return findPostById(postDTO.getId());
    }

    public List<PostDTO> findPostsByRecipeName(String recipeName) {
        String query = "db.Post.find( { \"recipe.name\": { $regex: \"" + recipeName + "\", $options: \"i\" } } )";

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

    // #1 Aggregation
    public Map<String, Double> interactionsAnalysis(){
        String query = "db.Post.aggregate([\r\n" + //
                       "    {\r\n" + //
                       "        $project: {\r\n" + //
                       "            _id: 1,\r\n" + //
                       "            hasImage: {\r\n" + //
                       "                $cond: {\r\n" + //
                       "                    if: {\r\n" + //
                       "                         $eq: [{ $type: \"$recipe.image\" }, \"missing\"]\r\n" + //
                       "                    },\r\n" + //
                       "                    then: false,\r\n" + //
                       "                    else: true\r\n" + //
                       "                }\r\n" + //
                       "            },\r\n" + //
                       "            comments: {\r\n" + //
                       "                $size: {\r\n" + //
                       "                    $ifNull: [\"$comments\", []]\r\n" + //
                       "                }\r\n" + //
                       "            },\r\n" + //
                       "            starRankings: {\r\n" + //
                       "                $size: {\r\n" + //
                       "                    $ifNull: [\"$starRankings\", []]\r\n" + //
                       "                }\r\n" + //
                       "            },\r\n" + //
                       "            avgStarRanking: {\r\n" + //
                       "                $ifNull: [\"$avgStarRanking\", 0]\r\n" + //
                       "            }\r\n" + //
                       "        }\r\n" + //
                       "    },\r\n" + //
                       "    {\r\n" + //
                       "        $group: {\r\n" + //
                       "            _id: \"$hasImage\",\r\n" + //
                       "            numberOfPosts: {\r\n" + //
                       "                $sum: 1\r\n" + //
                       "            },\r\n" + //
                       "            totalComments: {\r\n" + //
                       "                $sum: \"$comments\"\r\n" + //
                       "            },\r\n" + //
                       "            totalStarRankings: {\r\n" + //
                       "                $sum: \"$starRankings\"\r\n" + //
                       "            },\r\n" + //
                       "            avgOfAvgStarRanking: {\r\n" + //
                       "                $avg: \"$avgStarRanking\"\r\n" + //
                       "            }\r\n" + //
                       "        }\r\n" + //
                       "    },\r\n" + //
                       "    {\r\n" + //
                       "        $project: {\r\n" + //
                       "            _id: 0,\r\n" + //
                       "            hasImage: \"$_id\",\r\n" + //
                       "            ratioOfComments: {\r\n" + //
                       "                $divide: [\"$totalComments\", \"$numberOfPosts\"]\r\n" + //
                       "            },\r\n" + //
                       "            ratioOfStarRankings: {\r\n" + //
                       "                $divide: [\"$totalStarRankings\", \"$numberOfPosts\"]\r\n" + //
                       "            },\r\n" + //
                       "            avgOfAvgStarRanking: 1\r\n" + //
                       "        }\r\n" + //
                       "    }\r\n" + //
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
        String query = "db.Post.aggregate([\r\n" + //
                       "    {\r\n" + //
                       "        $match: {\r\n" + //
                       "            $or: [\r\n" + //
                       "                {\"comments.username\": \"" + username + "\"},\r\n" + //
                       "                {\"starRankings.username\": \"" + username + "\"}\r\n" + //
                       "            ]\r\n" + //
                       "        }\r\n" + //
                       "    },\r\n" + //
                       "    {\r\n" + //
                       "        $project: {\r\n" + //
                       "            filteredComments: {\r\n" + //
                       "                $filter: {\r\n" + //
                       "                    input: \"$comments\",\r\n" + //
                       "                    as: \"comment\",\r\n" + //
                       "                    cond: {$eq: [\"$$comment.username\", \"" + username + "\"]}\r\n" + //
                       "                }\r\n" + //
                       "            },\r\n" + //
                       "            filteredStarRankings: {\r\n" + //
                       "                $filter: {\r\n" + //
                       "                    input: \"$starRankings\",\r\n" + //
                       "                    as: \"starRanking\",\r\n" + //
                       "                    cond: {$eq: [\"$$starRanking.username\", \"" + username + "\"]}\r\n" + //
                       "                }\r\n" + //
                       "            }\r\n" + //
                       "        }\r\n" + //
                       "    },\r\n" + //
                       "    {\r\n" + //
                       "        $group: {\r\n" + //
                       "            _id: null,\r\n" + //
                       "            avgOfStarRankings: {\r\n" + //
                       "                $avg: {$sum: \"$filteredStarRankings.vote\"}\r\n" + //
                       "            },\r\n" + //
                       "            numberOfStarRankings: {\r\n" + //
                       "                $sum: {$size: \"$filteredStarRankings\"}\r\n" + //
                       "            },\r\n" + //
                       "            numberOfComments: {\r\n" + //
                       "                $sum: {$size: \"$filteredComments\"}\r\n" + //
                       "            }\r\n" + //
                       "        }\r\n" + //
                       "    },\r\n" + //
                       "    {\r\n" + //
                       "        $project: {\r\n" + //
                       "            _id: 0,\r\n" + //
                       "            numberOfComments: 1,\r\n" + //
                       "            numberOfStarRankings: 1,\r\n" + //
                       "            avgOfStarRankings: 1\r\n" + //
                       "        }\r\n" + //
                       "    }\r\n" + //
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
        String query = "db.Post.aggregate([\r\n" + //
                       "    {\r\n" + //
                       "        $match: {\r\n" + //
                       "            \"recipe.name\": { $regex: \"" + recipeName + "\", $options: \"i\" }\r\n" + //
                       "        }\r\n" + //
                       "    },\r\n" + //
                       "    {\r\n" + //
                       "        $sort: {\r\n" + //
                       "            avgStarRanking: -1\r\n" + //
                       "        }\r\n" + //
                       "    },\r\n" + //
                       "    {\r\n" + //
                       "        $limit: 10\r\n" + //
                       "    },\r\n" + //
                       "    {\r\n" + //
                       "        $group: {\r\n" + //
                       "            _id: null,\r\n" + //
                       "            avgOfTotalCalories: {\r\n" + //
                       "                $avg: \"$recipe.totalCalories\"\r\n" + //
                       "            }\r\n" + //
                       "        }\r\n" + //
                       "    },\r\n" + //
                       "    {\r\n" + //
                       "        $project: {\r\n" + //
                       "            _id: 0,\r\n" + //
                       "            avgOfTotalCalories: 1\r\n" + //
                       "        }\r\n" + //
                       "    }\r\n" + //
                       "])";

        List<Document> result = BaseMongoDB.executeQuery(query);
        if(result.isEmpty())
            return null;
        
        Document analytics_doc = result.get(0);

        return Double.parseDouble(analytics_doc.get("avgOfTotalCalories").toString());
    }


    public Double averageTotalCaloriesByUser(String username){
        String query = "db.Post.aggregate([ \r\n" + //
                       "    { \r\n" + //
                       "        $match: { \r\n" + //
                       "            username: \"" + username + "\" \r\n" + //
                       "        } \r\n" + //
                       "    }, \r\n" + //
                       "    {   $project: { \r\n" + //
                       "            recipeCalories: \"$recipe.totalCalories\" \r\n" + //
                       "        } \r\n" + //
                       "    }, \r\n" + //
                       "    {   $group: { \r\n" + //
                       "            _id: null, \r\n" + //
                       "            avgCalories: { \r\n" + //
                       "                $avg: \"$recipeCalories\" \r\n" + //
                       "            } \r\n" + //
                       "        } \r\n" + //
                       "    }\r\n" + //
                       "])";

        List<Document> result = BaseMongoDB.executeQuery(query);
        if(result.isEmpty())
            return null;
        
        Document analytics_doc = result.get(0);

        return Double.parseDouble(analytics_doc.get("avgCalories").toString());
    }
    
}
