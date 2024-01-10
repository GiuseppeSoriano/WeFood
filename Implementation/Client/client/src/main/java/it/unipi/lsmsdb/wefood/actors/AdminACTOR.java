package it.unipi.lsmsdb.wefood.actors;

import java.util.List;
import java.util.Scanner;

// import it.unipi.lsmsdb.wefood.apidto.LoginRequestDTO;
import it.unipi.lsmsdb.wefood.httprequests.AdminHTTP;
import it.unipi.lsmsdb.wefood.httprequests.IngredientHTTP;
import it.unipi.lsmsdb.wefood.model.Admin;
import it.unipi.lsmsdb.wefood.model.Ingredient;
import it.unipi.lsmsdb.wefood.utility.Printer;
import it.unipi.lsmsdb.wefood.utility.Reader;

public class AdminACTOR{
    private static Admin info = null;
    private final static AdminHTTP adminHTTP = new AdminHTTP();
    private final static IngredientHTTP ingredientHTTP = new IngredientHTTP();
    private final static Scanner scanner = new Scanner(System.in);

    public static void login(){
        if(info != null){
            System.out.println("You are already logged in as admin");
            return;
        }
        if(RegisteredUserACTOR.getInfo() != null){
            System.out.println("You are already logged in as user");
            return;
        }
        info = adminHTTP.loginAdmin(Reader.readLoginRequestDTO());
        if(info != null)
            System.out.println("Logged in as admin");
        else
            System.out.println("Error while logging in");
    }
    
    public static void logout(){
        info = null;
        System.out.println("Logged out");
    }

    public static Admin getInfo(){
        return info;
    }

    public static void createIngredient(){
        if(info == null){
            System.out.println("You must be logged in as admin to create an ingredient");
            return;
        }

        boolean state = adminHTTP.createIngredient(Reader.readIngredient());
        if(state)
            System.out.println("Ingredient created successfully");
        else
            System.out.println("Error while creating ingredient");
    }
    
    public static void banUser(){
        if(info == null){
            System.out.println("You must be logged in as admin to ban a user");
            return;
        }

        System.out.println("Insert username: ");
        String username = scanner.nextLine();

        boolean state = adminHTTP.banUser(username);
        if(state)
            System.out.println("User banned successfully");
        else
            System.out.println("Error while banning user");
    }

    public static void unbanUser(){
        if(info == null){
            System.out.println("You must be logged in as admin to unban a user");
            return;
        }

        System.out.println("Insert username: ");
        String username = scanner.nextLine();

        boolean state = adminHTTP.unbanUser(username);
        if(state)
            System.out.println("User unbanned successfully");
        else
            System.out.println("Error while unbanning user");
    }

    public static void findIngredientByName(){
        if(info == null){
            System.out.println("You must be logged in to find an ingredient by name");
            return;
        }

        System.out.println("Insert ingredient name: ");
        String name = scanner.nextLine();

        Ingredient ingredient = ingredientHTTP.findIngredientByName(name);
        if(ingredient != null)
            Printer.printIngredient(ingredient);
        else
            System.out.println("Error while finding ingredient");
    }

    public static void getAllIngredients(){
        if(info == null){
            System.out.println("You must be logged in to get all ingredients");
            return;
        }

        for(Ingredient ingredient : ingredientHTTP.getAllIngredients())
            Printer.printIngredient(ingredient);
    }

    public static void findIngredientsUsedWithIngredient(){
        if(info == null){
            System.out.println("You must be logged in to get ingredients by calories");
            return;
        }

        List<String> ingredients = ingredientHTTP.findIngredientsUsedWithIngredient(Reader.readIngredientAndLimitRequestDTO());
        for(String ingredient : ingredients)
            System.out.println(ingredient);
    }

    // SERVE QUELLO DI DEFAULT?? COME FARE??

    public static void mostPopularCombinationOfIngredients(){
        if(info == null){
            System.out.println("You must be logged in to get the most popular combination of ingredients");
            return;
        }

        System.out.println("Insert ingredient name: ");
        String ingredientName = scanner.nextLine();

        List<String> ingredients = ingredientHTTP.mostPopularCombinationOfIngredients(ingredientName);
        for(String ingredient : ingredients)
            System.out.println(ingredient);
    }

    public static void findMostUsedIngredientsByUser(){

    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Esegui un'azione");
            System.out.println("2. Esci");
            System.out.print("Scegli un'opzione: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Implementa l'azione
                    break;
                case 2:
                    System.out.println("Chiusura del terminale...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opzione non valida. Riprova.");
            }
        }
    }

}