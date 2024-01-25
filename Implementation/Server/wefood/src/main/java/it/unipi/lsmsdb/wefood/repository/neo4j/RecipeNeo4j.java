package it.unipi.lsmsdb.wefood.repository.neo4j;

import it.unipi.lsmsdb.wefood.dto.PostDTO;
import org.neo4j.driver.Record;
import org.neo4j.driver.exceptions.Neo4jException;

import java.util.ArrayList;
import java.util.List;
import it.unipi.lsmsdb.wefood.repository.base.BaseNeo4j;
import it.unipi.lsmsdb.wefood.repository.interfaces.RecipeNeo4jInterface;
import org.neo4j.driver.types.Node;

public class RecipeNeo4j implements RecipeNeo4jInterface {

    public boolean createRecipe(PostDTO postDTO) throws IllegalStateException, Neo4jException {

        String image_string = (postDTO.getImage() == null) ? "" : ", image: \"" + postDTO.getImage() + "\"";

        String query = "CREATE (r:Recipe {" + //
                       "    _id: \"" + postDTO.getId() + "\"," + //
                       "    name: \"" + postDTO.getRecipeName() + "\"" + //
                       image_string +
                       "})";

        List<Record> results = BaseNeo4j.executeQuery(query);

        // If it does not throw an exception, it means that the query has been executed successfully
        return true;        
    }
    
    public boolean deleteRecipe(PostDTO postDTO) throws IllegalStateException, Neo4jException  {
        String query = "MATCH (r:Recipe {_id: \"" + postDTO.getId() + "\"})\r\n" + //
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

    public List<PostDTO> findRecipeByIngredients(List<String> ingredientNames) throws IllegalStateException, Neo4jException {
        String query = "MATCH (r:Recipe)-[:CONTAINS]->(i:Ingredient)\n" +
                        "WHERE i.name IN" + ingredientNamesToString(ingredientNames) + "\n" +
                        "WITH r, COLLECT(i.name) AS ingredients\n" +
                        "WHERE ALL(ingredient IN " + ingredientNamesToString(ingredientNames) + "\n" +
                        "          WHERE ingredient IN ingredients)\n" +
                        "RETURN r\n" +
                        "LIMIT 15\n";
        List<Record> results = BaseNeo4j.executeQuery(query);
        List<PostDTO> recipes = new ArrayList<PostDTO>();
        for(Record recipe_record: results){
            Node recipeNode = recipe_record.get("r").asNode();
            // Ottieni il campo image se esiste, altrimenti imposta un valore di default
            String image = recipeNode.containsKey("image") ? recipeNode.get("image").asString() : "DEFAULT";
            PostDTO recipe_to_insert = new PostDTO(recipe_record.get("r").get("_id").asString(), image, recipe_record.get("r").get("name").asString());
            recipes.add(recipe_to_insert);
        }
        return recipes;
    }

    public boolean createRecipeIngredientsRelationship(PostDTO postDTO, List<String> ingredientNames) throws IllegalStateException, Neo4jException {
        
        for(String ingredientName: ingredientNames){
            String query = "MATCH (r:Recipe {_id: \"" + postDTO.getId() + "\"}), (i:Ingredient {name: \"" + ingredientName + "\"})\r\n" + //
                           "CREATE (r)-[:CONTAINS]->(i)";
            List<Record> results = BaseNeo4j.executeQuery(query);
        }
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;        
    }
}