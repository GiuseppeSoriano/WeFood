package it.unipi.lsmsdb.wefood.repository.mongodb;

import it.unipi.lsmsdb.wefood.model.Ingredient;
import org.bson.Document;

import com.mongodb.MongoException;

import java.util.ArrayList;
import java.util.List;
import it.unipi.lsmsdb.wefood.repository.base.BaseMongoDB;
import it.unipi.lsmsdb.wefood.repository.interfaces.IngredientMongoDBInterface;

public class IngredientMongoDB implements IngredientMongoDBInterface {

    public String createIngredient(Ingredient ingredient) throws MongoException, IllegalArgumentException, IllegalStateException {
        
        String query = "db.Ingredient.insertOne({" + //
                            "name: \"" + ingredient.getName() +"\"," + //
                            "calories: " + ingredient.getCalories() +"," + //
                       "})"; 
        
        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return result.get(0).get("_id").toString();
    };

    
    public Ingredient findIngredientByName(String name) throws MongoException, IllegalArgumentException, IllegalStateException {
        String query = "db.Ingredient.find({" + //
                            "name: \"" + name + "\"," + //
                       "}, {_id: 0})";
        
        List<Document> result = BaseMongoDB.executeQuery(query);
        if(result.isEmpty()) {
            // Ingredient does not exist
            return null;
        } else {
            // Ingredient is unique
            return new Ingredient(result.get(0).getString("name"), Double.parseDouble(result.get(0).get("calories").toString()));
        }
    };

    public List<Ingredient> getAllIngredients() throws MongoException, IllegalArgumentException, IllegalStateException {
        String query = "db.Ingredient.find({}, {_id:0})";

        List<Document> result = BaseMongoDB.executeQuery(query);
        List<Ingredient> ingredients = new ArrayList<Ingredient>();

        for(Document document : result) {
            ingredients.add(new Ingredient(document.getString("name"), Double.parseDouble(document.get("calories").toString())));
        }

        return ingredients;
    };

    public boolean deleteIngredient(String _id) throws MongoException, IllegalArgumentException, IllegalStateException {
        String query = "db.Ingredient.deleteOne({" + //
                            "_id: " + _id + //
                       "})";

        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    }


}