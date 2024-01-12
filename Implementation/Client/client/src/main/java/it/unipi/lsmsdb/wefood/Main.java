package it.unipi.lsmsdb.wefood;


import it.unipi.lsmsdb.wefood.actors.UnregisteredUserACTOR;
import it.unipi.lsmsdb.wefood.httprequests.IngredientHTTP;
import it.unipi.lsmsdb.wefood.model.Ingredient;

import java.util.List;

public class Main {
    private static List<Ingredient> ingredients = null;
    private static final IngredientHTTP ingredientHTTP = new IngredientHTTP();

    public static List<Ingredient> getIngredients() {
        return ingredients;
    }
    public static void main(String[] args) {
        ingredients = ingredientHTTP.getAllIngredients();
        System.out.println(ingredients.size());
        UnregisteredUserACTOR.executeUnregisteredUserShell();

    }
}
