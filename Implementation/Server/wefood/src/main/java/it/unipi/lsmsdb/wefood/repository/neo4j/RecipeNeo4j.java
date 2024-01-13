package it.unipi.lsmsdb.wefood.repository.neo4j;

import it.unipi.lsmsdb.wefood.dto.RecipeDTO;
import org.neo4j.driver.Record;
import org.neo4j.driver.exceptions.Neo4jException;

import java.util.ArrayList;
import java.util.List;
import it.unipi.lsmsdb.wefood.repository.base.BaseNeo4j;
import it.unipi.lsmsdb.wefood.repository.interfaces.RecipeNeo4jInterface;

public class RecipeNeo4j implements RecipeNeo4jInterface {

    public boolean createRecipe(RecipeDTO recipeDTO) throws IllegalStateException, Neo4jException {

        String query = "CREATE (r:Recipe {\r\n" + //
                       "    _id: \"" + recipeDTO.getId() + "\",\r\n" + //
                       "    name: \"" + recipeDTO.getName() + "\"\r\n" + //
                       "})";

        List<Record> results = BaseNeo4j.executeQuery(query);

        // If it does not throw an exception, it means that the query has been executed successfully
        return true;        
    }
    
    public boolean deleteRecipe(RecipeDTO recipeDTO) throws IllegalStateException, Neo4jException  {
        String query = "MATCH (r:Recipe {_id: \"" + recipeDTO.getId() + "\"})\r\n" + //
                       "DETACH DELETE r";
        List<Record> results = BaseNeo4j.executeQuery(query);
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;        
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

    public List<RecipeDTO> findRecipeByIngredients(List<String> ingredientNames) throws IllegalStateException, Neo4jException {
        String query = "MATCH (r:Recipe)-[:CONTAINS]->(i:Ingredient)\r\n" + //
                       "WHERE i.name IN " + ingredientNamesToString(ingredientNames) + "\r\n" + //
                       "RETURN r \r\n" + //
                       "LIMIT 10";
        List<Record> results = BaseNeo4j.executeQuery(query);
        if (results.isEmpty()) {
            return null;
        } else {
            List<RecipeDTO> recipes = new ArrayList<RecipeDTO>();
            for(Record recipe_record: results){
                recipes.add(new RecipeDTO(recipe_record.get("r").get("_id").asString(), recipe_record.get("r").get("name").asString()));
            }
            return recipes;
        }
    }

    public boolean createRecipeIngredientsRelationship(RecipeDTO recipeDTO, List<String> ingredientNames) throws IllegalStateException, Neo4jException {
        
        for(String ingredientName: ingredientNames){
            String query = "MATCH (r:Recipe {_id: \"" + recipeDTO.getId() + "\"}), (i:Ingredient {name: \"" + ingredientName + "\"})\r\n" + //
                           "CREATE (r)-[:CONTAINS]->(i)";
            List<Record> results = BaseNeo4j.executeQuery(query);
        }
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;        
    }
}