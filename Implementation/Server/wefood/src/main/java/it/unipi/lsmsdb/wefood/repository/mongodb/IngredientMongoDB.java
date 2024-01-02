package it.unipi.lsmsdb.wefood.repository.mongodb;

import it.unipi.lsmsdb.wefood.model.Ingredient;
import org.bson.Document;
import java.util.List;
import it.unipi.lsmsdb.wefood.repository.base.BaseMongoDB;
import it.unipi.lsmsdb.wefood.repository.interfaces.IngredientMongoDBInterface;

public class IngredientMongoDB implements IngredientMongoDBInterface {
    public Ingredient findIngredientByName(String name) {
        String query = ""; //mettere query
        List<Document> result = BaseMongoDB.executeQuery(query);
        if(result.isEmpty()) {
            return null;
        } else {
            return new Ingredient(result.get(0).getString("name"), result.get(0).getDouble("calories"));
        }
    };

    public boolean createIngredient(Ingredient ingredient) {
        String query = ""; //mettere query
        List<Document> result = BaseMongoDB.executeQuery(query);
        if(result.isEmpty()) {
            return false;
        } else {
            return true;
        }
    };
}