package it.unipi.lsmsdb.wefood.dao;

import com.mongodb.MongoException;
import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import it.unipi.lsmsdb.wefood.model.Ingredient;
import it.unipi.lsmsdb.wefood.repository.interfaces.IngredientMongoDBInterface;
import it.unipi.lsmsdb.wefood.repository.interfaces.IngredientNeo4jInterface;
import it.unipi.lsmsdb.wefood.repository.mongodb.IngredientMongoDB;
import it.unipi.lsmsdb.wefood.repository.neo4j.IngredientNeo4j;
import org.neo4j.driver.exceptions.Neo4jException;

import java.util.List;

public class IngredientDAO {
    private final static IngredientMongoDBInterface ingredientMongoDBInterface = new IngredientMongoDB();
    private final static IngredientNeo4jInterface ingredientNeo4jInterface = new IngredientNeo4j();

    // Bisogna gestire il caso in cui fallisce la creazione del nodo in neo4j
    public static String createIngredientMongoDB(Ingredient ingredient) throws MongoException, IllegalArgumentException, IllegalStateException{
        return ingredientMongoDBInterface.createIngredient(ingredient);
        // eccezioni lanciate da neo4j vanno gestite con try catch per evitare che il nodo venga creato solo in mongo
    }

    public static boolean deleteIngredientMongoDB(String _id) throws MongoException, IllegalArgumentException, IllegalStateException{
        return ingredientMongoDBInterface.deleteIngredient(_id);
    }

    public static boolean createIngredientNeo4j(IngredientDTO ingredientDTO) throws IllegalStateException, Neo4jException {
        return ingredientNeo4jInterface.createIngredient(ingredientDTO);
    }

    public static Ingredient findIngredientByName(String name) throws MongoException, IllegalArgumentException, IllegalStateException {
        return ingredientMongoDBInterface.findIngredientByName(name);
    }

    public static List<Ingredient> getAllIngredients() throws MongoException, IllegalArgumentException, IllegalStateException {
        return ingredientMongoDBInterface.getAllIngredients();
    }

    public static List<IngredientDTO> findIngredientsUsedWithIngredient(IngredientDTO ingredientDTO, int limit) throws IllegalStateException, Neo4jException  {
        return ingredientNeo4jInterface.findIngredientsUsedWithIngredient(ingredientDTO, limit);
    }
    public static List<IngredientDTO> findIngredientsUsedWithIngredient(IngredientDTO ingredientDTO) throws IllegalStateException, Neo4jException {
        return ingredientNeo4jInterface.findIngredientsUsedWithIngredient(ingredientDTO);
    }

    public static boolean createIngredientIngredientRelationship(List<String> ingredientNames) throws IllegalStateException, Neo4jException {
        return ingredientNeo4jInterface.createIngredientIngredientRelationship(ingredientNames);
    }

    public static List<IngredientDTO> mostPopularCombinationOfIngredients(IngredientDTO ingredient) throws IllegalStateException, Neo4jException {
        return ingredientNeo4jInterface.mostPopularCombinationOfIngredients(ingredient);
    }

    public static List<IngredientDTO> findNewIngredientsBasedOnFriendsUsage(RegisteredUserDTO user) throws IllegalStateException, Neo4jException {
        return ingredientNeo4jInterface.findNewIngredientsBasedOnFriendsUsage(user);
    }

    public static List<IngredientDTO> findMostUsedIngredientsByUser(RegisteredUserDTO user) throws IllegalStateException, Neo4jException {
        return ingredientNeo4jInterface.findMostUsedIngredientsByUser(user);
    }
    public static List<IngredientDTO> findMostLeastUsedIngredients(boolean DESC) throws IllegalStateException, Neo4jException {
        return ingredientNeo4jInterface.findMostLeastUsedIngredients(DESC);
    }
}
