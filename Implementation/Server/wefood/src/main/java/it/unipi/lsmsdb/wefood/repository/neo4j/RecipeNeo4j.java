package it.unipi.lsmsdb.wefood.repository.neo4j;

import it.unipi.lsmsdb.wefood.model.Recipe;
import it.unipi.lsmsdb.wefood.model.Ingredient;
import it.unipi.lsmsdb.wefood.dto.RecipeDTO;
import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import org.neo4j.driver.Record;
import java.util.List;
import it.unipi.lsmsdb.wefood.repository.base.BaseNeo4j;
import it.unipi.lsmsdb.wefood.repository.interfaces.RecipeNeo4jInterface;

public class RecipeNeo4j implements RecipeNeo4jInterface {

    public boolean createRecipe(RecipeDTO recipeDTO) {

        String query = "CREATE (r: {\r\n" + //
                       "    _id: " + ingredientDTO.getId() + ",\r\n" + //
                       "    name: " + ingredientDTO.getName() + "\r\n" + //
                       "})";

        List<Record> results = BaseNeo4j.executeQuery(query);
        System.out.println(results.get(0));
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;

        
        String query = "CREATE (r:Recipe {name: " + recipeDTO.getName() + "})";
        List<Record> results = BaseNeo4j.executeQuery(query);
        if (results.isEmpty()) {
            return false;
        } else {
            return true;
        }
    };
    
    
    public boolean deleteRecipe(RecipeDTO recipeDTO) {
        String query = "";
        List<Record> results = BaseNeo4j.executeQuery(query);
        if (results.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public List<RecipeDTO> findRecipeByIngredients(List<IngredientDTO> ingredients) {
        String query = "";
        List<Record> results = BaseNeo4j.executeQuery(query);
        if (results.isEmpty()) {
            return null;
        } else {
            /*
                ritornare una lista di RecipeDTO
                sotto forma di List<RecipeDTO> recipe = new LinkedList<> ...
            */
           return recipe;
        }
    }
}