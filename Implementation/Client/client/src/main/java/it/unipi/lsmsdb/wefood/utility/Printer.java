package it.unipi.lsmsdb.wefood.utility;

import it.unipi.lsmsdb.wefood.model.Ingredient;

public class Printer {
    public static void printIngredient(Ingredient ingredient){
        System.out.println(ingredient.getName() + ":  " + ingredient.getCalories() + " calories");
    }
}
