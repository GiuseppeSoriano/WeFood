package it.unipi.lsmsdb.wefood.repository.mongodb;

import it.unipi.lsmsdb.wefood.model.Ingredient;
import org.bson.Document;
import java.util.List;
import it.unipi.lsmsdb.wefood.repository.base.BaseMongoDB;
import it.unipi.lsmsdb.wefood.repository.interfaces.IngredientMongoDBInterface;

public class IngredientMongoDB implements IngredientMongoDBInterface {

    public boolean createIngredient(Ingredient ingredient) {
        
        String query = "db.Ingredient.insertOne({\r\n" + //
                       "    name: " + ingredient.getName() +",\r\n" + //
                       "    calories: " + ingredient.getCalories() +",\r\n" + //
                       "})"; 
        
        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    };

    
    public Ingredient findIngredientByName(String name) {
        String query = "db.Ingredient.find({\r\n" + //
                       "    name: " + name + ",\r\n" + //
                       "})"; 
        
        List<Document> result = BaseMongoDB.executeQuery(query);
        if(result.isEmpty()) {
            // Ingredient does not exist
            return null;
        } else {
            // Ingredient is unique
            return new Ingredient(result.get(0).getString("name"), result.get(0).getDouble("calories"));
        }
    };

}