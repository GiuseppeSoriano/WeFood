package it.unipi.lsmsdb.wefood;


import it.unipi.lsmsdb.wefood.actors.UnregisteredUserACTOR;
import it.unipi.lsmsdb.wefood.httprequests.IngredientHTTP;
import it.unipi.lsmsdb.wefood.model.Ingredient;
import it.unipi.lsmsdb.wefood.utility.Reader;

import java.util.List;

public class Main {
    private static List<Ingredient> ingredients = null;
    private static final IngredientHTTP ingredientHTTP = new IngredientHTTP();

    public static List<Ingredient> getIngredients() {
        return ingredients;
    }
    public static void main(String[] args) {
        ingredients = ingredientHTTP.getAllIngredients();
        Reader.getAllIngredientsFromDatabase();
        // System.out.println(ingredients.size());
        UnregisteredUserACTOR.executeUnregisteredUserShell();

    }
}
