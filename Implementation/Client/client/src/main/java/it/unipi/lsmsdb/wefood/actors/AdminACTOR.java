package it.unipi.lsmsdb.wefood.actors;

import java.util.List;
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
        if(info == null){
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

    private static void findIngredientByName(){
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

    private static void getAllIngredients(){
        if(info == null){
            System.out.println("You must be logged in to get all ingredients");
            return;
        }

        for(Ingredient ingredient : Reader.getAllIngredients())
            Printer.printIngredient(ingredient);
    }

    private static void findIngredientsUsedWithIngredient(){
        if(info == null){
            System.out.println("You must be logged in to get ingredients by calories");
            return;
        }

        List<String> ingredients = ingredientHTTP.findIngredientsUsedWithIngredient(Reader.readIngredientAndLimitRequestDTO());
        for(String ingredient : ingredients)
            System.out.println(ingredient);
    }

    // SERVE QUELLO DI DEFAULT?? COME FARE?? Che vuol dire? Sei Giuse? Io sono Gio

    private static void mostPopularCombinationOfIngredients(){
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

    private static void findMostLeastUsedIngredients(){
        if(info == null){
            System.out.println("You must be logged in to get the most and least used ingredients");
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
            System.out.println("You must be logged in to browse the most recent top rated posts");
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

    public void browseMostRecentTopRatedPostsByIngredients(){
    
    }

    public void browseMostRecentPostsByCalories(){
        if(info == null){
            System.out.println("You must be logged in to browse the most recent top rated posts");
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
    
    public void findPostsByRecipeName(){
        if(info == null){
            System.out.println("You must be logged in to browse the most recent top rated posts");
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

    public void interactionsAnalysis(){
        if(info == null){
            System.out.println("You must be logged in to browse the most recent top rated posts");
            return;
        }

        Printer.printMapDouble(postHTTP.interactionsAnalysis());
    }

    public void userInteractionsAnalysis(){
        if(info == null){
            System.out.println("You must be logged in to browse the most recent top rated posts");
            return;
        }
        System.out.println("Insert username: ");
        String username = scanner.nextLine();

        Printer.printMapDouble(postHTTP.userInteractionsAnalysis(username));
    }

    public void caloriesAnalysis(){
        if(info == null){
            System.out.println("You must be logged in to browse the most recent top rated posts");
            return;
        }
        System.out.println("Insert recipe name: ");
        String recipeName = scanner.nextLine();
        
        System.out.println("User average total calories per post: " + postHTTP.caloriesAnalysis(recipeName));
    }

    // GIUSE bisogna sistemare il println .... anche sopra!!!!!!!!!
    public void averageTotalCaloriesByUser(){
        if(info == null){
            System.out.println("You must be logged in to browse the most recent top rated posts");
            return;
        }
        System.out.println("Insert username: ");
        String username = scanner.nextLine();
        
        System.out.println("User average total calories per post: " + postHTTP.caloriesAnalysis(username));
    }

    public void findRecipeByIngredients(){
        if(info == null){
            System.out.println("You must be logged in to find Recipes By Ingredients");
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
                case "findIngredientByName":
                    findIngredientByName();
                    break;
                case "getAllIngredients":
                    getAllIngredients();
                    break;
                case "findIngredientsUsedWithIngredient":
                    findIngredientsUsedWithIngredient();
                    break;
                case "mostPopularCombinationOfIngredients":
                    mostPopularCombinationOfIngredients();
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println("Command not found");
                    break;
            }
        }
    }



}