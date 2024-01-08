package it.unipi.lsmsdb.wefood.repository.neo4j;

import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;

import org.neo4j.driver.Record;
import org.neo4j.driver.exceptions.Neo4jException;

import java.util.ArrayList;
import java.util.List;
import it.unipi.lsmsdb.wefood.repository.base.BaseNeo4j;
import it.unipi.lsmsdb.wefood.repository.interfaces.IngredientNeo4jInterface;

public class IngredientNeo4j implements IngredientNeo4jInterface {


    public boolean createIngredient(IngredientDTO ingredientDTO) throws IllegalStateException, Neo4jException {
        
        String query = "CREATE (i:Ingredient {\r\n" + //
                       "    _id: " + ingredientDTO.neo4JgetId() + ",\r\n" + //
                       "    name: " + ingredientDTO.getName() + "\r\n" + //
                       "})";

        List<Record> results = BaseNeo4j.executeQuery(query);
        System.out.println(results.get(0));
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    }

    public List<IngredientDTO> findIngredientsUsedWithIngredient(IngredientDTO ingredientDTO, int limit) throws IllegalStateException, Neo4jException {
        
        String query = "MATCH (i1:Ingredient{name:'" + ingredientDTO.getName() + "'})-[r:USED_WITH]->(i2:Ingredient)\r\n" + //
                       "RETURN i2, r.times AS times\r\n" + //
                       "ORDER BY times DESC\r\n" + //
                       "LIMIT " + limit + "\r\n";

        List<Record> results = BaseNeo4j.executeQuery(query);
        List<IngredientDTO> resultSet = new ArrayList<IngredientDTO>();
        
        for(Record record: results) {
            IngredientDTO ingredient = new IngredientDTO(record.get("i2").get("_id").asString(), record.get("i2").get("name").asString());
            resultSet.add(ingredient);
        }

        return resultSet; 
    }

    public List<IngredientDTO> findIngredientsUsedWithIngredient(IngredientDTO ingredientDTO) throws IllegalStateException, Neo4jException {
        return findIngredientsUsedWithIngredient(ingredientDTO, 5);
    }  

    public boolean createIngredientIngredientRelationship(List<String> ingredientNames) throws IllegalStateException, Neo4jException {
        
        for(String ingredientName1: ingredientNames){
            for(String ingredientName2: ingredientNames){
                if(ingredientName1.equals(ingredientName2))
                    continue;
                String query = "MATCH (i1:Ingredient {name: " + ingredientName1 + "}), (i2:Ingredient {name: " + ingredientName2 + "})\r\n" + //
                               "MERGE (i1)-[r:USED_WITH]->(i2) ON CREATE SET r.times = 1 ON MATCH SET r.times = r.times + 1";
                List<Record> results = BaseNeo4j.executeQuery(query);
                System.out.println(results.get(0));
            }
        }
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;      
    }

    public List<IngredientDTO> mostPopularCombinationOfIngredients(IngredientDTO ingredientDTO) throws IllegalStateException, Neo4jException {
        String query = "MATCH (i1:Ingredient{name: " + ingredientDTO.getName() + "})-[r:USED_WITH]->(i2:Ingredient)\r\n" + //
                       "RETURN i2, r.times AS times\r\n" + //
                       "ORDER BY times DESC\r\n" + //
                       "LIMIT 5"; 
        List<Record> results = BaseNeo4j.executeQuery(query);
        List<IngredientDTO> ingredients = new ArrayList<IngredientDTO>();
        for(Record result: results){
            IngredientDTO ingredient = new IngredientDTO(result.get("i2").get("_id").asString(),
                                                         result.get("i2").get("name").asString());
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    public List<IngredientDTO> findNewIngredientsBasedOnFriendsUsage(RegisteredUserDTO user) throws IllegalStateException, Neo4jException {
         String query = "MATCH (u1:User {username: " + user.getUsername() + "})-[:FOLLOWS]->(u2:User)-[r:USED]->(i:Ingredient)\r\n" + //
                        "WHERE (u2)-[:FOLLOWS]->(u1)\r\n" + //
                        "AND NOT (u1)-[:USED]->(i)\r\n" + //
                        "RETURN i, r.times AS times\r\n" + //
                        "ORDER BY times DESC\r\n" + //
                        "LIMIT 5"; 
        List<Record> results = BaseNeo4j.executeQuery(query);
        List<IngredientDTO> ingredients = new ArrayList<IngredientDTO>();
        for(Record result: results){
            IngredientDTO ingredient = new IngredientDTO(result.get("i").get("_id").asString(),
                                                         result.get("i").get("name").asString());
            ingredients.add(ingredient);
        }
        return ingredients;
    } 

    public List<IngredientDTO> findMostUsedIngredientsByUser(RegisteredUserDTO user) throws IllegalStateException, Neo4jException {
        String query = "MATCH (u:User {username: " + user.getUsername() + "})-[r:USED]->(i:Ingredient)\r\n" + //
                       "RETURN i, r.times AS times\r\n" + //
                       "ORDER BY times DESC\r\n" + //
                       "LIMIT 5"; 
        List<Record> results = BaseNeo4j.executeQuery(query);
        List<IngredientDTO> ingredients = new ArrayList<IngredientDTO>();
        for(Record result: results){
            IngredientDTO ingredient = new IngredientDTO(result.get("i").get("_id").asString(),
                                                         result.get("i").get("name").asString());
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    // If DESC is true, it returns the most used ingredients, otherwise the least used ingredients
    public List<IngredientDTO> findMostLeastUsedIngredients(boolean DESC) throws IllegalStateException, Neo4jException {
        
        String order = (DESC) ? "DESC" : "ASC";

        String query = "MATCH (u:User)-[r:USED]->(i:Ingredient)\r\n" + //
                       "RETURN i, SUM(r.times) AS times \r\n" + //
                       "ORDER BY times "+ order +"\r\n" + //
                       "LIMIT 5"; 
        List<Record> results = BaseNeo4j.executeQuery(query);
        List<IngredientDTO> ingredients = new ArrayList<IngredientDTO>();
        for(Record result: results){
            IngredientDTO ingredient = new IngredientDTO(result.get("i").get("_id").asString(),
                                                         result.get("i").get("name").asString());
            ingredients.add(ingredient);
        }
        return ingredients;
    }
}