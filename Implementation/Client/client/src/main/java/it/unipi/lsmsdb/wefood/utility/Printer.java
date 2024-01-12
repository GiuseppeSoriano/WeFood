package it.unipi.lsmsdb.wefood.utility;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.dto.RecipeDTO;
import it.unipi.lsmsdb.wefood.httprequests.PostHTTP;
import it.unipi.lsmsdb.wefood.model.Ingredient;
import it.unipi.lsmsdb.wefood.model.Comment;
import it.unipi.lsmsdb.wefood.model.StarRanking;
import it.unipi.lsmsdb.wefood.model.Post;

import it.unipi.lsmsdb.wefood.actors.RegisteredUserACTOR;

public class Printer {
    private final static Scanner scanner = new Scanner(System.in);
    private final static PostHTTP postHTTP = new PostHTTP();
    
    public static void printIngredient(Ingredient ingredient){
        System.out.println(ingredient.getName() + ":  " + ingredient.getCalories() + " calories");
    }

    public static void printPostDTO(PostDTO postDTO, int postNumber){
        System.out.println("Recipe name: " + postDTO.getRecipeName());
        ImageConverter.fromStringToImage(postDTO.getImage(), String.valueOf(postNumber));
        // Image Preview
        System.out.println(ImageConverter.convertImageToAsciiArt(String.valueOf(postNumber)));
        System.out.println("Image Path: " + ImageConverter.getPathOfTempImage(String.valueOf(postNumber)));
    }

    public static void printRecipeDTO(RecipeDTO recipeDTO){
        System.out.println("Recipe name: " + recipeDTO.getName());
    }

    public static void printPostByPostDTO(PostDTO postDTO, int pos){
        Post post = postHTTP.findPostByPostDTO(postDTO);
        printPost(postDTO, post, pos);
    }
    
    public static void PrintPostByRecipeDTO(RecipeDTO recipeDTO, int pos){
        Post post = postHTTP.findPostById(recipeDTO.getID());
        PostDTO postDTO = new PostDTO(recipeDTO.getID(), post.getRecipe().getImage(), recipeDTO.getName());
        ImageConverter.fromStringToImage(postDTO.getImage(), String.valueOf(pos));
        printPost(postDTO, post, pos);
    }

    public static void printPost(PostDTO postDTO, Post post, int pos){
        System.out.println("Post [" + pos + "]");
        System.out.println("Author: " + post.getUsername());
        System.out.println("Description: " + post.getDescription());
        System.out.println("Date: " + post.getTimestamp().toString());
        System.out.println("Average StarRanking: " + post.getAvgStarRanking());

        System.out.println("Recipe: ");
        System.out.println("Name: " + post.getRecipe().getName());
        // Image Preview
        System.out.println(ImageConverter.convertImageToAsciiArt(String.valueOf(pos)));
        System.out.println("Image Path: " + ImageConverter.getPathOfTempImage(String.valueOf(pos)));
        System.out.println("Calories: " + post.getRecipe().getTotalCalories());
        System.out.println("Steps: ");
        for(String step : post.getRecipe().getSteps()){
            System.out.println(step);
        }
        // iterate over a map
        System.out.println("Ingredients: ");
        for(String ingredient : post.getRecipe().getIngredients().keySet()){
            System.out.println(ingredient + ": " + post.getRecipe().getIngredients().get(ingredient) + " grams");
        }

        System.out.println("Comments: ");
        for(Comment comment : post.getComments()){
            System.out.println("Comment by " + comment.getUsername() + ": " + comment.getText());
        }

        System.out.println("Star Rankings: ");
        for(StarRanking starRanking : post.getStarRankings()){
            System.out.println("Left by " + starRanking.getUsername() + ": " + starRanking.getVote());
        }

        while(true){
            System.out.println("Insert 'C' to comment, 'S' to star rank, 'E' to exit: ");
            String input = scanner.nextLine();
            String input2;
            switch(input){
                case "C":
                    System.out.println("Choose if insert comment 'I', delete comment 'D', modify comment 'M', exit 'E': ");
                    input2 = scanner.nextLine();
                    switch(input2){
                        case "I":
                            RegisteredUserACTOR.commentPost(postDTO);
                            break;
                        case "D":
                            RegisteredUserACTOR.deleteComment(postDTO);
                            break;
                        case "M":
                            RegisteredUserACTOR.updateComment(postDTO);
                            break;
                        case "E":
                            System.out.println("Exiting...");
                            break;
                        default:
                            System.out.println("Invalid input");
                            break;
                    }
                    break;
                case "S":
                    // RegisteredUserACTOR.votePost(postDTO);
                    System.out.println("Choose if insert star ranking 'I', delete star ranking 'D', exit 'E': ");
                    input2 = scanner.nextLine();
                    switch(input2){
                        case "I":
                            RegisteredUserACTOR.votePost(postDTO);
                            break;
                        case "D":
                            RegisteredUserACTOR.deleteVote(postDTO);
                            break;
                        case "E":
                            System.out.println("Exiting...");
                            break;
                        default:
                            System.out.println("Invalid input");
                            break;
                    }
                    break;
                case "E":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }

    

    public static void printListOfPostDTO(List<PostDTO> postDTOs){
        for(int i = 0; i < postDTOs.size(); i++){
            // I need to print the Post number because
            // inside the TempImages folder the images
            // are named with the Post number and the
            // user needs to know which image is related
            // to which post
            System.out.println("Post number " + i + ":");
            printPostDTO(postDTOs.get(i), i);
            System.out.println("Insert 'V' to view the post, 'N' to see the next post, 'P' to see the previous post, 'E' to exit: ");
            String input = scanner.nextLine();
            switch(input){
                case "V":
                    System.out.println("Opening post...");
                    printPostByPostDTO(postDTOs.get(i), i);
                    break;
                case "N":
                    if(i != postDTOs.size()-1){
                        System.out.println("Next post...");
                    }
                    break;
                case "P":
                    if(i == 0){
                        System.out.println("No previous post...");
                        i--;
                        break;
                    }
                    else
                        i-=2;
                    System.out.println("Previous post...");
                    break;
                case "E":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid input");
                    i--;
                    break;
            }
        }
    }

    public static void printListOfRecipeDTO(List<RecipeDTO> recipeDTOs){

        for(int i = 0; i < recipeDTOs.size(); i++){
            System.out.println("Recipe number " + i + ":");
            printRecipeDTO(recipeDTOs.get(i));
            System.out.println("Insert 'V' to view the Recipe, 'N' to see the next Recipe, 'P' to see the previous Recipe, 'E' to exit: ");
            String input = scanner.nextLine();
            switch(input){
                case "V":
                    System.out.println("Opening Recipe...");
                    PrintPostByRecipeDTO(recipeDTOs.get(i), i);
                    break;
                case "N":
                    System.out.println("Next Recipe...");
                    break;
                case "P":
                    if(i == 0){
                        System.out.println("No previous Recipe...");
                        i--;
                        break;
                    }
                    else
                        i-=2;
                    System.out.println("Previous Recipe...");
                    break;
                case "E":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid input");
                    i--;
                    break;
            }
        }
    }

    public static void printMapDouble(Map<String, Double> map){
        for(String key : map.keySet()){
            System.out.println(key + ": " + map.get(key));
        }
    }
}
