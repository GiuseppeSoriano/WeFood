package it.unipi.lsmsdb.wefood.repository.neo4j;

import it.unipi.lsmsdb.wefood.model.Ingredient;
import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import org.neo4j.driver.Record;
import java.util.List;
import it.unipi.lsmsdb.wefood.repository.base.BaseNeo4j;
import it.unipi.lsmsdb.wefood.repository.interfaces.IngredientNeo4jInterface;

public class IngredientNeo4j implements IngredientNeo4jInterface {
    public boolean createIngredient(Ingredient ingredient) {
        String query = "";
        //BaseNeo4j.getNeo4jDriver();
        List<Record> results = BaseNeo4j.executeQuery(query);
        if (results.isEmpty()) {
            return false;
        } else {
            return true;
        }
    };

    public List<IngredientDTO> findIngredientsUsedWithIngredient(IngredientDTO ingredientDTO) {
        String query = "";
        //BaseNeo4j.getNeo4jDriver();
        List<Record> results = BaseNeo4j.executeQuery(query);
        if (results.isEmpty()) {
            return null;
        } else {
            //return true; return di una lista
            return ingredientsDTO;
        }
    };
}