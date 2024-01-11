package it.unipi.lsmsdb.wefood.actors;

import java.util.List;
import java.util.Scanner;

import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.httprequests.AdminHTTP;
import it.unipi.lsmsdb.wefood.httprequests.PostHTTP;
import it.unipi.lsmsdb.wefood.httprequests.RegisteredUserHTTP;
import it.unipi.lsmsdb.wefood.utility.Cleaner;
import it.unipi.lsmsdb.wefood.utility.Printer;
import it.unipi.lsmsdb.wefood.utility.Reader;

public class UnregisteredUserACTOR {

    private final static RegisteredUserHTTP registeredUserHTTP = new RegisteredUserHTTP();
    private final static AdminHTTP adminHTTP = new AdminHTTP();
    private final static PostHTTP postHTTP = new PostHTTP();
    private final static Scanner scanner = new Scanner(System.in);

    private static List<PostDTO> postDTOs = null;

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

        List<PostDTO> posts = postHTTP.findPostsByRecipeName(recipeName);
        for(PostDTO post : posts)
            System.out.println(post);
    }


    public static void executeUnregisteredUserShell(){
        login();
    }

}
