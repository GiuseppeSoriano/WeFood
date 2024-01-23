package it.unipi.lsmsdb.wefood.actors;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import it.unipi.lsmsdb.wefood.dto.PostDTO;
// import it.unipi.lsmsdb.wefood.apidto.LoginRequestDTO;
import it.unipi.lsmsdb.wefood.httprequests.AdminHTTP;
import it.unipi.lsmsdb.wefood.httprequests.IngredientHTTP;
import it.unipi.lsmsdb.wefood.httprequests.PostHTTP;
import it.unipi.lsmsdb.wefood.model.Admin;
import it.unipi.lsmsdb.wefood.model.Ingredient;
import it.unipi.lsmsdb.wefood.utility.Printer;
import it.unipi.lsmsdb.wefood.utility.Reader;
import it.unipi.lsmsdb.wefood.utility.Cleaner;
import it.unipi.lsmsdb.wefood.dto.RecipeDTO;

public class AdminACTOR{
    private static Admin info = null;
    private final static AdminHTTP adminHTTP = new AdminHTTP();
    private final static IngredientHTTP ingredientHTTP = new IngredientHTTP();
    private final static PostHTTP postHTTP = new PostHTTP();
    private final static Scanner scanner = new Scanner(System.in);
    private static List<PostDTO> postDTOs = null;
    private static List<RecipeDTO> recipeDTOs = null;

    public static void login(Admin admin){
        if(admin == null){
            System.out.println("Wrong credentials!");
            return;
        }
        info = admin;
        System.out.println("Logged in as " + info.getUsername() + "!");
        executeAdminShell();
    }
    

    public static Admin getInfo(){
        return info;
    }

    private static void createIngredient(){
        if(info == null){
            System.out.println("You must be logged in as admin to create an ingredient");
            return;
        }

        boolean state = adminHTTP.createIngredient(Reader.readNewIngredient());
        if(state)
            System.out.println("Ingredient created successfully");
        else
            System.out.println("Error while creating ingredient");
    }
    
    private static void banUser(){
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

    private static void unbanUser(){
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

    public static void mostPopularCombinationOfIngredients(){
        List<String> ingredients_found = ingredientHTTP.mostPopularCombinationOfIngredients(Reader.readIngredient());
        if(ingredients_found == null || ingredients_found.isEmpty()){
            System.out.println("No ingredients found");
            return;
        }
        for(String ingredient : ingredients_found)
            System.out.println(ingredient);
    }

    private static void findMostLeastUsedIngredients(){
        if(info == null){
            System.out.println("You must be logged in as admin to get the most and least used ingredients");
            return;
        }

        System.out.println("Insert if you want the most or least used ingredients (M or L): ");
        String choice = scanner.nextLine();

        List<String> ingredients = null;
        if(choice.equals("M"))
            ingredients = ingredientHTTP.findMostLeastUsedIngredients(true);
        else if(choice.equals("L"))
            ingredients = ingredientHTTP.findMostLeastUsedIngredients(false);
        
        if(ingredients == null){
            System.out.println("Wrong character inserted");
            return;
        }
        for(String ingredient : ingredients)
            System.out.println(ingredient);
    }

    private static void browseMostRecentTopRatedPosts(){
        if(info == null){
            System.out.println("You must be logged in as admin to browse the most recent top rated posts");
            return;
        }

        postDTOs = null;
        Cleaner.cleanTempFolder();
        postDTOs = postHTTP.browseMostRecentTopRatedPosts(Reader.readPostTopRatedRequestDTO());
        
        if(postDTOs == null){
            System.out.println("No Posts found");
            return;
        }
        Printer.printListOfPostDTO(postDTOs);
    }

    public static void browseMostRecentTopRatedPostsByIngredients(){
        if(info == null){
            System.out.println("You must be logged in as admin to browse the most recent top rated posts");
            return;
        }

        postDTOs = null;
        Cleaner.cleanTempFolder();
        postDTOs = postHTTP.browseMostRecentTopRatedPostsByIngredients(Reader.readPostByIngredientsRequestDTO());

        if(postDTOs == null){
            System.out.println("No Posts found");
            return;
        }
        Printer.printListOfPostDTO(postDTOs);
    }

    public static void browseMostRecentPostsByCalories(){
        if(info == null){
            System.out.println("You must be logged in as admin to browse the most recent top rated posts");
            return;
        }

        postDTOs = null;
        Cleaner.cleanTempFolder();
        postDTOs = postHTTP.browseMostRecentPostsByCalories(Reader.readPostByCaloriesRequestDTO());
        
        if(postDTOs == null){
            System.out.println("No Posts found");
            return;
        }
        Printer.printListOfPostDTO(postDTOs);
    }
    
    public static void findPostsByRecipeName(){
        if(info == null){
            System.out.println("You must be logged in as admin to browse the most recent top rated posts");
            return;
        }
        
        postDTOs = null;
        Cleaner.cleanTempFolder();
        System.out.println("Insert recipe name: ");
        String recipeName = scanner.nextLine();
        postDTOs = postHTTP.findPostsByRecipeName(recipeName);
        
        if(postDTOs == null){
            System.out.println("No Posts found");
            return;
        }
        Printer.printListOfPostDTO(postDTOs);
    }

    public static void interactionsAnalysis(){
        if(info == null){
            System.out.println("You must be logged in as admin to browse the most recent top rated posts");
            return;
        }

        Printer.printMapDouble(postHTTP.interactionsAnalysis());
    }

    public static void userInteractionsAnalysis(){
        if(info == null){
            System.out.println("You must be logged in as admin to browse the most recent top rated posts");
            return;
        }
        System.out.println("Insert username: ");
        String username = scanner.nextLine();
        Map<String, Double> map = postHTTP.userInteractionsAnalysis(username);
        if(map == null)
            System.out.println("No user found or no posts published by the user");
        else
            Printer.printMapDouble(map);
    }

    public static void caloriesAnalysis(){
        if(info == null){
            System.out.println("You must be logged in as admin to browse the most recent top rated posts");
            return;
        }
        System.out.println("Insert recipe name: ");
        String recipeName = scanner.nextLine();
        Double result = postHTTP.caloriesAnalysis(recipeName);
        if(result == null){
            System.out.println("No recipe found");
            return;
        }
        System.out.println("User average total calories per post: " + result);
    }
    public static void averageTotalCaloriesByUser(){
        if(info == null){
            System.out.println("You must be logged in as admin to browse the most recent top rated posts");
            return;
        }
        System.out.println("Insert username: ");
        String username = scanner.nextLine();
        Double result = postHTTP.averageTotalCaloriesByUser(username);
        if(result == null){
            System.out.println("No user found or no posts published by the user");
            return;
        }
        System.out.println("User average total calories per post: " + result);
    }

    public static void findRecipeByIngredients(){
        if(info == null){
            System.out.println("You must be logged in as admin to find Recipes By Ingredients");
            return;
        }

        recipeDTOs = null;
        Cleaner.cleanTempFolder();
        recipeDTOs = postHTTP.findRecipeByIngredients(Reader.readListOfIngredientNames());
        
        if(recipeDTOs == null){
            System.out.println("No Recipes found");
            return;
        }
        Printer.printListOfRecipeDTO(recipeDTOs);
    }

    public static void executeAdminShell(){
        boolean exit = false;
        while(!exit){
            System.out.println("Insert command: ");
            String command = scanner.nextLine();
            switch(command){
                case "logout":
                    info = null;
                    System.out.println("Logged out");
                    exit = true;
                    break;
                case "createIngredient":
                    createIngredient();
                    break;
                case "banUser":
                    banUser();
                    break;
                case "unbanUser":
                    unbanUser();
                    break;
                case "mostPopularCombinationOfIngredients":
                    mostPopularCombinationOfIngredients();
                    break;
                case "findMostLeastUsedIngredients":
                    findMostLeastUsedIngredients();
                    break;
                case "browseMostRecentTopRatedPosts":
                    browseMostRecentTopRatedPosts();
                    break;
                case "browseMostRecentTopRatedPostsByIngredients":
                    browseMostRecentTopRatedPostsByIngredients();
                    break;
                case "browseMostRecentPostsByCalories":
                    browseMostRecentPostsByCalories();
                    break;
                case "findPostsByRecipeName":
                    findPostsByRecipeName();
                    break;
                case "interactionsAnalysis":            // TO CHECK
                    interactionsAnalysis();
                    break;
                case "userInteractionsAnalysis":
                    userInteractionsAnalysis();
                    break;
                case "caloriesAnalysis":
                    caloriesAnalysis();
                    break;
                case "averageTotalCaloriesByUser":
                    averageTotalCaloriesByUser();
                    break;
                case "findRecipeByIngredients":
                    findRecipeByIngredients();
                    break;
                case "help":
                    printAvailableCommands();
                    break;
                case "exit":
                    exit = true;
                    UnregisteredUserACTOR.unsetAppIsRunning();
                    break;
                default:
                    System.out.println("Command not found");
                    break;
            }
        }
    }

    private static void printAvailableCommands() {
        System.out.println("Available commands:");
        System.out.println("-> logout");
        System.out.println("-> createIngredient");
        System.out.println("-> banUser");
        System.out.println("-> unbanUser");
        System.out.println("-> mostPopularCombinationOfIngredients");           // IMPLEMENTED
        System.out.println("-> findMostLeastUsedIngredients");                  // NO BUTTON
        System.out.println("-> browseMostRecentTopRatedPosts");                 // IMPLEMENTED
        System.out.println("-> browseMostRecentTopRatedPostsByIngredients");    // IMPLEMENTED
        System.out.println("-> browseMostRecentPostsByCalories");               // IMPLEMENTED
        System.out.println("-> findPostsByRecipeName");                         // IMPLEMENTED
        System.out.println("-> interactionsAnalysis");
        System.out.println("-> userInteractionsAnalysis");
        System.out.println("-> caloriesAnalysis");
        System.out.println("-> averageTotalCaloriesByUser");
        System.out.println("-> findRecipeByIngredients");
        System.out.println("-> exit");
    }


}