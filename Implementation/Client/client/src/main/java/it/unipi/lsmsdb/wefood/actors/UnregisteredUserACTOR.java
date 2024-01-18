package it.unipi.lsmsdb.wefood.actors;

import java.util.List;
import java.util.Scanner;

import it.unipi.lsmsdb.wefood.apidto.UnregisteredUserRequestDTO;
import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.dto.RecipeDTO;
import it.unipi.lsmsdb.wefood.httprequests.*;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.utility.Cleaner;
import it.unipi.lsmsdb.wefood.utility.Printer;
import it.unipi.lsmsdb.wefood.utility.Reader;

public class UnregisteredUserACTOR {

    private final static RegisteredUserHTTP registeredUserHTTP = new RegisteredUserHTTP();
    private final static UnregisteredUserHTTP unregisteredUserHTTP = new UnregisteredUserHTTP();
    private final static AdminHTTP adminHTTP = new AdminHTTP();
    private final static PostHTTP postHTTP = new PostHTTP();
    private final static IngredientHTTP ingredientHTTP = new IngredientHTTP();
    private final static Scanner scanner = new Scanner(System.in);

    private static List<PostDTO> postDTOs = null;
    private static Boolean AppIsRunning = true;
    private static List<RecipeDTO> recipeDTOs = null;

    public static void unsetAppIsRunning(){
        AppIsRunning = false;
    }

    private static void login(){
        System.out.println("Do you want to login as user (U) or admin (A): ");
        String choice = scanner.nextLine();
        if(choice.equals("U"))
            RegisteredUserACTOR.login(registeredUserHTTP.login(Reader.readLoginRequestDTO()));
        else if(choice.equals("A"))
            AdminACTOR.login(adminHTTP.loginAdmin(Reader.readLoginRequestDTO()));
        else
            System.out.println("Invalid choice");
    }

    private static void register(){
        System.out.println("Insert name: ");
        String name = scanner.nextLine();
        System.out.println("Insert surname: ");
        String surname = scanner.nextLine();
        System.out.println("Insert username: ");
        String username = scanner.nextLine();
        System.out.println("Insert password: ");
        String password = scanner.nextLine();
        System.out.println("Confirm password: ");
        if(!password.equals(scanner.nextLine())){
            System.out.println("Passwords do not match");
            return;
        }
        RegisteredUser credentials = unregisteredUserHTTP.register(new UnregisteredUserRequestDTO(name, surname, username, password));
        if(credentials == null){
            System.out.println("Error in creating the user");
            return;
        }
        RegisteredUserACTOR.login(credentials);
    }


    private static void browseMostRecentTopRatedPosts () {
        postDTOs = null;
        Cleaner.cleanTempFolder();
        postDTOs = postHTTP.browseMostRecentTopRatedPosts(Reader.readPostTopRatedRequestDTO());
        
        if(postDTOs == null){
            System.out.println("No Posts found");
            return;
        }
        Printer.printListOfPostDTO(postDTOs);
    }
    
    private static void browseMostRecentTopRatedPostsByIngredients () {

        postDTOs = null;
        Cleaner.cleanTempFolder();
        postDTOs = postHTTP.browseMostRecentTopRatedPostsByIngredients(Reader.readPostByIngredientsRequestDTO());
        
        if(postDTOs == null){
            System.out.println("No Posts found");
            return;
        }
        Printer.printListOfPostDTO(postDTOs);

    }

    private static void browseMostRecentPostsByCalories () {
        postDTOs = null;
        Cleaner.cleanTempFolder();
        postDTOs = postHTTP.browseMostRecentPostsByCalories(Reader.readPostByCaloriesRequestDTO());
        
        if(postDTOs == null){
            System.out.println("No Posts found");
            return;
        }
        Printer.printListOfPostDTO(postDTOs);
    }

    private static void findPostsByRecipeName () {
        System.out.println("Insert recipe name: ");
        String recipeName = scanner.nextLine();

        List<PostDTO> postDTOs = postHTTP.findPostsByRecipeName(recipeName);
        Printer.printListOfPostDTO(postDTOs);
    }

    public static void findRecipeByIngredients(){
        recipeDTOs = null;
        Cleaner.cleanTempFolder();
        recipeDTOs = postHTTP.findRecipeByIngredients(Reader.readListOfIngredientNames());

        if(recipeDTOs == null){
            System.out.println("No Recipes found");
            return;
        }
        Printer.printListOfRecipeDTO(recipeDTOs);
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


    private static void printAvailableCommands(){
        System.out.println("Available commands:");
        System.out.println("-> register");
        System.out.println("-> login");
        System.out.println("-> browseMostRecentTopRatedPosts");
        System.out.println("-> browseMostRecentTopRatedPostsByIngredients");
        System.out.println("-> browseMostRecentPostsByCalories");
        System.out.println("-> findPostsByRecipeName");
        System.out.println("-> findRecipeByIngredients");
        System.out.println("-> mostPopularCombinationOfIngredients");
        System.out.println("-> exit");
    }

    public static void executeUnregisteredUserShell(){
        while(AppIsRunning){
            System.out.println("Insert your command: ('help' to show commands, 'exit' to stop app)");
            String command = scanner.nextLine();
            switch(command){
                case "login":
                    login();
                    break;
                case "register":
                    register();
                    break;
                case "browseMostRecentTopRatedPosts":
                    browseMostRecentTopRatedPosts();
                    break;
                case "help":
                    printAvailableCommands();
                    break;
                case "exit":
                    AppIsRunning = false;
                    break;
                default:
                    System.out.println("Command not found");
                    break;
            }
        }

    }

}
