package it.unipi.lsmsdb.wefood.repository.neo4j;

import it.unipi.lsmsdb.wefood.dto.IngredientDTO;

import org.neo4j.driver.Record;
import org.neo4j.driver.exceptions.Neo4jException;

import java.util.ArrayList;
import java.util.List;
import it.unipi.lsmsdb.wefood.repository.base.BaseNeo4j;
import it.unipi.lsmsdb.wefood.repository.interfaces.IngredientNeo4jInterface;

public class IngredientNeo4j implements IngredientNeo4jInterface {


    public boolean createIngredient(IngredientDTO ingredientDTO) throws IllegalStateException, Neo4jException {
        
        String query = "CREATE (i:Ingredient {\r\n" + //
                       "    _id: " + ingredientDTO.getId() + ",\r\n" + //
                       "    name: " + ingredientDTO.getName() + "\r\n" + //
                       "})";

        List<Record> results = BaseNeo4j.executeQuery(query);
        System.out.println(results.get(0));
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    };

    public List<IngredientDTO> findIngredientsUsedWithIngredient(IngredientDTO ingredientDTO, int limit) {
        
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
    };

    public List<IngredientDTO> findIngredientsUsedWithIngredient(IngredientDTO ingredientDTO){
        return findIngredientsUsedWithIngredient(ingredientDTO, 5);
    };  

}