package it.unipi.lsmsdb.wefood.repository.interfaces;

import com.mongodb.MongoException;
import it.unipi.lsmsdb.wefood.model.Ingredient;

import java.util.List;

public interface IngredientMongoDBInterface {

    String createIngredient(Ingredient ingredient) throws MongoException, IllegalArgumentException, IllegalStateException;
    
    Ingredient findIngredientByName(String name) throws MongoException, IllegalArgumentException, IllegalStateException;

    List<Ingredient> getAllIngredients() throws MongoException, IllegalArgumentException, IllegalStateException;

    boolean deleteIngredient(String _id) throws MongoException, IllegalArgumentException, IllegalStateException;
}
