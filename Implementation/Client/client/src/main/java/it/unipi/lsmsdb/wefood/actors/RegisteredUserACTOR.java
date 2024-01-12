package it.unipi.lsmsdb.wefood.actors;

import java.util.List;
import java.util.Scanner;

import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import it.unipi.lsmsdb.wefood.httprequests.CommentHTTP;
import it.unipi.lsmsdb.wefood.httprequests.IngredientHTTP;
import it.unipi.lsmsdb.wefood.httprequests.PostHTTP;
import it.unipi.lsmsdb.wefood.httprequests.RegisteredUserHTTP;
import it.unipi.lsmsdb.wefood.httprequests.StarRankingHTTP;
import it.unipi.lsmsdb.wefood.model.Ingredient;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.utility.Cleaner;
import it.unipi.lsmsdb.wefood.utility.Printer;
import it.unipi.lsmsdb.wefood.utility.Reader;

public class RegisteredUserACTOR {
    private static RegisteredUser info = null;
    
    private final static RegisteredUserHTTP registeredUserHTTP = new RegisteredUserHTTP();
    private final static IngredientHTTP ingredientHTTP = new IngredientHTTP();
    private final static CommentHTTP commentHTTP = new CommentHTTP();
    private final static StarRankingHTTP starRankingHTTP = new StarRankingHTTP();
    private final static PostHTTP postHTTP = new PostHTTP();
    private final static Scanner scanner = new Scanner(System.in);

    private static List<PostDTO> postDTOs = null;

    public static void login(RegisteredUser user){
        if(user == null){
            System.out.println("Wrong credentials!");
            return;
        }
        info = user;
        System.out.println("Logged in as " + info.getUsername() + "!");
        System.out.println(info.getId());
        System.out.println("Welcome " + info.getName() + " " + info.getSurname() + "!");
        executeUserShell();
    }
    
    public static RegisteredUser getInfo(){
        return info;
    }

    public static void commentPost(PostDTO postDTO) {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        boolean state = commentHTTP.commentPost(Reader.readComment(info, postDTO));

        if(state) {
            System.out.println("Post commented successfully!");
        } else {
            System.out.println("Error while commenting!");
        }
    }

    public static void updateComment(PostDTO postDTO) {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        boolean state = commentHTTP.updateComment(Reader.readComment(info, postDTO));

        if(state) {
            System.out.println("Post commented successfully!");
        } else {
            System.out.println("Error while commenting!");
        }
    }

    public static void deleteComment(PostDTO postDTO) {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        boolean state = commentHTTP.deleteComment(Reader.readComment(info, postDTO));

        if(state) {
            System.out.println("Comment deleted successfully!");
        } else {
            System.out.println("Error while deleting!");
        }
    }

    public static void votePost(PostDTO postDTO) {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        boolean state = starRankingHTTP.votePost(Reader.readStarRanking(info, postDTO));

        if(state) {
            System.out.println("Post voted successfully!");
        } else {
            System.out.println("Error while voting!");
        }
    }

    public static void deleteVote(PostDTO postDTO) {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }
        //Assumiamo che l'utente inserisca il suo voto correttamente
        boolean state = starRankingHTTP.deleteVote(Reader.readStarRanking(info, postDTO));

        if(state) {
            System.out.println("Vote deleted successfully!");
        } else {
            System.out.println("Error while deleting your vote!");
        }
    }

    private static void findIngredientByName () {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
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

    /*
    private static void getAllIngredients () {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        for(Ingredient ingredient : ingredientHTTP.getAllIngredients())
            Printer.printIngredient(ingredient);
    }
    */

    private static void findIngredientsUsedWithIngredient () {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        List<String> ingredients = ingredientHTTP.findIngredientsUsedWithIngredient(Reader.readIngredientAndLimitRequestDTO());
        for(String ingredient : ingredients)
            System.out.println(ingredient);
    }

    private static void mostPopularCombinationOfIngredients () {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        System.out.println("Insert ingredient name: ");
        String ingredientName = scanner.nextLine();

        List<String> ingredients = ingredientHTTP.mostPopularCombinationOfIngredients(ingredientName);
        for(String ingredient : ingredients)
            System.out.println(ingredient);
    }

    private static void findNewIngredientsBasedOnFriendsUsage () {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        List<String> ingredients = ingredientHTTP.findNewIngredientsBasedOnFriendsUsage(new RegisteredUserDTO(info.getId(), info.getUsername()));
        for(String ingredient : ingredients)
            System.out.println(ingredient);
    }

    private static void findMostUsedIngredientsByUser () {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        List<String> ingredients = ingredientHTTP.findMostUsedIngredientsByUser(new RegisteredUserDTO(info.getId(), info.getUsername()));
        for(String ingredient : ingredients)
            System.out.println(ingredient);
    }

    private static void findMostLeastUsedIngredients () {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        System.out.println("Do you want the Most or the Least Used Ingredients?");
        System.out.println("Insert M for Most anything else for Least:");
        boolean DESC;
        if((scanner.nextLine()).equals("M"))
            DESC = true;
        else
            DESC = false;
        
        List<String> ingredients = ingredientHTTP.findMostLeastUsedIngredients(DESC);
        for(String ingredient : ingredients)
            System.out.println(ingredient);
    }

    private static void uploadPost () {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        boolean state = postHTTP.uploadPost(Reader.readPost(info));

        if(state) {
            System.out.println("Post commented successfully!");
        } else {
            System.out.println("Error while posting!");
        }
    }

    private static void modifyPost () {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        boolean state = postHTTP.modifyPost(Reader.readPost(info));

        if(state) {
            System.out.println("Post modified successfully!");
        } else {
            System.out.println("Error while modifying the post!");
        }
    }

    private static void deletePost () {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        boolean state = postHTTP.deletePost(Reader.readPost(info));

        if(state) {
            System.out.println("Post deleted successfully!");
        } else {
            System.out.println("Error while deleting the post!");
        }
    }

    private static void browseMostRecentTopRatedPosts () {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
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

    private static void browseMostRecentTopRatedPostsByIngredients () {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
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

    private static void browseMostRecentPostsByCalories () {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
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

    private static void findPostsByRecipeName () {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }
        
        System.out.println("Insert recipe name: ");
        String recipeName = scanner.nextLine();

        List<PostDTO> posts = postHTTP.findPostsByRecipeName(recipeName);
        for(PostDTO post : posts)
            System.out.println(post);
    }

    private static void averageTotalCaloriesByUser () {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        double avg = postHTTP.averageTotalCaloriesByUser(info.getUsername());
        System.out.println(avg);
    }

    private static void findRecipeByIngredients () {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }


        // GIUSE QUI PERCHE' NON C'E' NULLA?????
        //filtro ingredienti
    }

    private static void modifyPersonalInformation() {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        boolean state = registeredUserHTTP.modifyPersonalInformation(info);

        if(state) {
            System.out.println("Informations modified successfully!");
        } else {
            System.out.println("Error while modifying your informations!");
        }
    }

    private static void deleteUser() {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        boolean state = registeredUserHTTP.deleteUser(info);

        if(state) {
            System.out.println("User delete!");
        } else {
            System.out.println("Error while deleting yourself!");
        }
    }

    private static void followUser() {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        boolean state = registeredUserHTTP.followUser(Reader.readUser(info));

        if(state) {
            System.out.println("User followed!");
        } else {
            System.out.println("Error while following!");
        }
    }

    private static void unfollowUser() {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        boolean state = registeredUserHTTP.unfollowUser(Reader.readUser(info));

        if(state) {
            System.out.println("User unfollowed!");
        } else {
            System.out.println("Error while unfollowing!");
        }
    }

    private static void findFriends() {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        List<RegisteredUserDTO> friends = registeredUserHTTP.findFriends(new RegisteredUserDTO(info.getId(), info.getUsername()));

        for(RegisteredUserDTO friend : friends)
            System.out.println(friend);
    }

    private static void findFollowers() {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        List<RegisteredUserDTO> followers = registeredUserHTTP.findFollowers(new RegisteredUserDTO(info.getId(), info.getUsername()));

        for(RegisteredUserDTO follower : followers)
            System.out.println(follower);
    }

    private static void findFollowed() {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        List<RegisteredUserDTO> followeds = registeredUserHTTP.findFollowed(new RegisteredUserDTO(info.getId(), info.getUsername()));

        for(RegisteredUserDTO followed : followeds)
            System.out.println(followed);
    }

    private static void findUsersToFollowBasedOnUserFriends() {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        List<RegisteredUserDTO> suggestions = registeredUserHTTP.
                findUsersToFollowBasedOnUserFriends(new RegisteredUserDTO(info.getId(), info.getUsername()));

        for(RegisteredUserDTO suggestion : suggestions)
            System.out.println(suggestion);
    }

    private static void findMostFollowedUsers() {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        List<RegisteredUserDTO> suggestions = registeredUserHTTP.findMostFollowedUsers();

        for(RegisteredUserDTO suggestion : suggestions)
            System.out.println(suggestion);
    }

    private static void findUsersByIngredientUsage() {
        if(info == null) {
            System.out.println("You must be logged to perform this action!");
            return;
        }

        List<RegisteredUserDTO> suggestions = registeredUserHTTP.
                findUsersByIngredientUsage(info.getUsername()); //corretto?

        for(RegisteredUserDTO suggestion : suggestions)
            System.out.println(suggestion);
    }

    public static void executeUserShell() {
        boolean exit = false;
        while(!exit){
            System.out.println("Insert command: ");
            String command = scanner.nextLine();
            switch(command) {
                case "logout":
                    info = null;
                    System.out.println("Logged out");
                    exit = true;
                    break;
                case "findIngredientByName":
                    findIngredientByName();
                    break;
                case "findIngredientsUsedWithIngredient":
                    findIngredientsUsedWithIngredient();
                    break;
                case "findNewIngredientsBasedOnFriendsUsage":
                    findNewIngredientsBasedOnFriendsUsage();
                    break;
                case "findUsersToFollowBasedOnUserFriends":
                    findUsersToFollowBasedOnUserFriends();
                    break;
                case "findMostFollowedUsers":
                    findMostFollowedUsers();
                    break;
                case "findUsersByIngredientUsage":
                    findUsersByIngredientUsage();
                    break;
                case "findMostUsedIngredientByUser":
                    findMostUsedIngredientsByUser();
                    break;
                case "findMostLeastUsedIngredient":
                    findMostLeastUsedIngredients();
                    break;
                case "uploadPost":
                    uploadPost();
                    break;
                case "modifyPost":
                    modifyPost();
                    break;
                case "deletePost":
                    deletePost();
                    break;
                //aggiungere le operazioni possibili solo all'interno del post
                case "browseMostRecentTopRatedPosts":
                    browseMostRecentTopRatedPosts();
                    break;
                case "browseMostRecentTopRatedPostByIngredients":
                    browseMostRecentTopRatedPostsByIngredients();
                    break;
                case "browseMostRecentPostsByCalories":
                    browseMostRecentPostsByCalories();
                    break;
                case "findPostByRecipeName":
                    findPostsByRecipeName();
                    break;
                case "averageTotalCaloriesByUser":
                    averageTotalCaloriesByUser();
                    break;
                case "findRecipeByIngredients":
                    findRecipeByIngredients();
                    break;
                case "modifyPersonalInformation":
                    modifyPersonalInformation();
                    break;
                case "deleteUser":
                    deleteUser();
                    break;
                case "followUser":
                    followUser();
                    break;
                case "unfollowUser":
                    unfollowUser();
                    break;
                case "findFriends":
                    findFriends();
                    break;
                case "findFollowers":
                    findFollowers();
                    break;
                case "findFollowed":
                    findFollowed();
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
}   
