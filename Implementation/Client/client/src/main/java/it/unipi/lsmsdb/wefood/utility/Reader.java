package it.unipi.lsmsdb.wefood.utility;

import java.util.Scanner;

import it.unipi.lsmsdb.wefood.apidto.CommentRequestDTO;
import it.unipi.lsmsdb.wefood.apidto.IngredientAndLimitRequestDTO;
import it.unipi.lsmsdb.wefood.apidto.LoginRequestDTO;
import it.unipi.lsmsdb.wefood.apidto.PostByCaloriesRequestDTO;
import it.unipi.lsmsdb.wefood.apidto.PostByIngredientsRequestDTO;
import it.unipi.lsmsdb.wefood.apidto.PostRequestDTO;
import it.unipi.lsmsdb.wefood.apidto.PostTopRatedRequestDTO;
import it.unipi.lsmsdb.wefood.apidto.RegisteredUserRequestDTO;
import it.unipi.lsmsdb.wefood.apidto.StarRankingRequestDTO;
import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import it.unipi.lsmsdb.wefood.httprequests.IngredientHTTP;
import it.unipi.lsmsdb.wefood.model.Comment;
import it.unipi.lsmsdb.wefood.model.Ingredient;
import it.unipi.lsmsdb.wefood.model.Post;
import it.unipi.lsmsdb.wefood.model.Recipe;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.model.StarRanking;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Reader {
    private final static Scanner scanner = new Scanner(System.in);
    private final static IngredientHTTP ingredientHTTP = new IngredientHTTP();
    
    // ------------ Needed for real-time ingredient suggestion ---------------
    private static List<Ingredient> ingredients = new ArrayList<>();
    private static List<String> ingredients_inserted = new ArrayList<>();
    private static volatile boolean running = true;
    private static StringBuilder currentInput = new StringBuilder();
    private static boolean singleIngredient = false;
    // -----------------------------------------------------------------------


    public static void getAllIngredientsFromDatabase(){
        ingredients = ingredientHTTP.getAllIngredients();
    }

    public static List<Ingredient> getAllIngredients(){
        return ingredients;
    }

    public static LoginRequestDTO readLoginRequestDTO(){
        System.out.println("Insert username: ");
        String username = scanner.nextLine();
        System.out.println("Insert password: ");
        String password = scanner.nextLine();

        return new LoginRequestDTO(username, password);
    }

    public static Ingredient readNewIngredient(){
        System.out.println("Insert new Ingredient name: ");
        String name = scanner.nextLine();
        System.out.println("Insert Calories: ");
        double calories = scanner.nextDouble();

        return new Ingredient(name, calories);
    }

    public static IngredientAndLimitRequestDTO readIngredientAndLimitRequestDTO(){

        // Real-time ingredient suggestion
        ingredients_inserted.clear();
        singleIngredient = true;
        readIngredients();
        String name = ingredients_inserted.get(0);
        System.out.println("Insert limit: ");
        int limit = scanner.nextInt();

        return new IngredientAndLimitRequestDTO(name, limit);
    }

    public static PostTopRatedRequestDTO readPostTopRatedRequestDTO(){
        System.out.println("Insert hours: ");
        long hours = scanner.nextLong();
        System.out.println("Insert limit: ");
        int limit = scanner.nextInt();

        return new PostTopRatedRequestDTO(hours, limit);
    }

    public static PostByCaloriesRequestDTO readPostByCaloriesRequestDTO(){

        System.out.println("Insert min calories: ");
        Double minCalories = scanner.nextDouble();
        System.out.println("Insert max calories: ");
        Double maxCalories = scanner.nextDouble();
        System.out.println("Insert hours: ");
        Long hours = scanner.nextLong();
        System.out.println("Insert limit: ");
        Integer limit = scanner.nextInt();

        return new PostByCaloriesRequestDTO(minCalories, maxCalories, hours, limit);
    }

    public static CommentRequestDTO readComment(RegisteredUser user, PostDTO postDTO) {
        System.out.println("Type your comment: ");
        String text = scanner.nextLine();
        return new CommentRequestDTO(user, new Comment(user.getUsername(), text, new Date()), postDTO);
    }

    public static StarRankingRequestDTO readStarRanking(RegisteredUser user, PostDTO postDTO) {
        System.out.println("Type your vote: ");
        double vote = scanner.nextDouble();
        while(vote < 0 || vote > 5) {
            System.out.println("Your vote must be from 0 to 5!");
            vote = scanner.nextDouble();
        }
        return new StarRankingRequestDTO(user, new StarRanking(user.getUsername(), vote), postDTO);
    }

    public static PostRequestDTO readPost(RegisteredUser user) {

        System.out.println("Type the description: ");
        String description = scanner.nextLine();
        System.out.println("Type the name of the recipe: ");
        String name = scanner.nextLine();
 
        Double calories, totalCalories, quantity;
        calories = 0.0;
        totalCalories = 0.0;
        Map<String, Double> recipe_ingredients = new HashMap<String, Double>();

        // Real-time ingredient suggestion
        // Ingredients and quantities
        singleIngredient = true;
        do{

            ingredients_inserted.clear();
            readIngredients();
            for(Ingredient ingredient : ingredients){
                if(ingredient.getName().equals(ingredients_inserted.get(0))){
                    calories = ingredient.getCalories();
                    break;
                }
            }
            System.out.println("Insert quantity (grams): ");
            quantity = scanner.nextDouble();
            totalCalories += quantity * (calories/100);
            recipe_ingredients.put(ingredients_inserted.get(0), quantity);

        }while(ingredients_inserted.size() != 0);

        List<String> steps = new ArrayList<>();
        System.out.println("Write each step (type n to stop)\n");
        while(true) {
            String step = scanner.nextLine();
            if (step.equals("n")) {
                break;
            } else {
                steps.add(step);
            }
        }

        System.out.println("Do you want to add an Image to the Post? (y/n)");
        String answer = scanner.nextLine();
        String image = null;
        if(answer.equals("y")){
            System.out.println("Add the image to the folder RecipesImages/" + user.getUsername() + "/ and then type here the name of the image (with the extension): ");
            answer = scanner.nextLine();
            image  = ImageConverter.fromImagePathToString(answer);
        }

        Post post = new Post(user.getUsername(), 
                             description, 
                             new Date(),
                             new Recipe(name, image, steps, recipe_ingredients, totalCalories));
        return new PostRequestDTO(post, null, user);

    }

    public static RegisteredUserRequestDTO readUser(RegisteredUser user) {
        System.out.println("Type the username: ");
        String unsername = scanner.nextLine();
        return new RegisteredUserRequestDTO(new RegisteredUserDTO(user.getId(), user.getUsername()),
                                                unsername);
    }


    public static PostByIngredientsRequestDTO readPostByIngredientsRequestDTO(){
        
        // Real-time ingredient suggestion
        ingredients_inserted.clear();
        singleIngredient = false;
        readIngredients();
        
        System.out.println("Insert hours: ");
        long hours = scanner.nextLong();
        System.out.println("Insert limit: ");
        int limit = scanner.nextInt();

        return new PostByIngredientsRequestDTO(ingredients_inserted, hours, limit);
    }


    public static List<String> readListOfIngredientNames(){
        
        // Real-time ingredient suggestion
        ingredients_inserted.clear();
        singleIngredient = false;
        readIngredients();
    
        return ingredients_inserted;
    }



    // -----------------------------------------------------------------------
    // ------------------ Real-time ingredient suggestion --------------------
    // -----------------------------------------------------------------------
    
    private static void readIngredients(){

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Ingredients Suggestion");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JTextField textField = new JTextField();
            JTextArea suggestionArea = new JTextArea();
            suggestionArea.setEditable(false);

            // Set the font size for the text field and suggestion area
            Font font = new Font(Font.MONOSPACED, Font.PLAIN, 18);
            textField.setFont(font);
            suggestionArea.setFont(font);

            // Font color and Background color
            textField.setBackground(Color.BLACK);
            textField.setForeground(Color.WHITE);
            suggestionArea.setBackground(Color.BLACK);
            suggestionArea.setForeground(Color.WHITE);

            textField.setColumns(20);

            textField.addKeyListener(new NewKeyListener(textField, suggestionArea));

            JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, suggestionArea, textField);
            splitPane.setResizeWeight(1.0);
            splitPane.setBorder(BorderFactory.createEmptyBorder());

            splitPane.setBackground(Color.BLACK);
            frame.getContentPane().setBackground(Color.BLACK);

            frame.add(splitPane);
            frame.setSize(400, 300);
            frame.setResizable(false);
            frame.setMinimumSize(new Dimension(400, 300));
            frame.setMaximumSize(new Dimension(400, 300));
            frame.setVisible(true);
            
        });

        Thread suggestionThread = new Thread(() -> {
            if(singleIngredient)
                System.out.println("Insert Ingredient:");
            else
                System.out.println("Insert Ingredients ('x' to exit):");

            while (running) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.exit(0); 
        });

        suggestionThread.start();
        while (running) {
            // Wait
        }
        suggestionThread.interrupt();

    }

    private static List<String> getSuggestions(String prefix) {
        List<String> suggestions = new ArrayList<>();

        for (Ingredient ingredient : ingredients) {
            String ingredientName = ingredient.getName();
            if (ingredientName.toLowerCase().startsWith(prefix.toLowerCase())) {
                suggestions.add(ingredientName);
            }
        }

        return suggestions;
    }

    private static class NewKeyListener implements KeyListener {

        private JTextField textField;
        private JTextArea suggestionArea;

        public NewKeyListener(JTextField textField, JTextArea suggestionArea) {
            this.textField = textField;
            this.suggestionArea = suggestionArea;
        }

        @Override
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();

            if (("x".equalsIgnoreCase(textField.getText())) && (!singleIngredient)) {
                // The user has typed 'x', exit
                running = false;
            } else if (c == KeyEvent.VK_ENTER) {
                // The user has pressed Enter
                String currentInputStr = currentInput.toString().toLowerCase();
                List<String> suggestions = getSuggestions(currentInputStr);

                if (suggestions.contains(currentInputStr)) {
                    // Current input matches a suggestion, return the result to the terminal
                    ingredients_inserted.add(currentInputStr);
                    System.out.println("Ingredient [" + ingredients_inserted.size() + "]: " + currentInputStr);
                    currentInput.setLength(0);
                    suggestionArea.setText(""); // Clear the suggestion area
                    if(singleIngredient)
                        running = false;
                }
            } else if (c == KeyEvent.VK_BACK_SPACE) {
                // The user has pressed Backspace, remove the last character from the current input
                if (currentInput.length() > 0) {
                    currentInput.deleteCharAt(currentInput.length() - 1);
                }
                // Update and show the suggestions
                updateSuggestionArea();
            } else {
                // The user has typed a character, add it to the current input
                currentInput.append(c);
                // Update and show the suggestions
                updateSuggestionArea();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        private void updateSuggestionArea() {
            List<String> suggestions = getSuggestions(currentInput.toString());
            StringBuilder suggestionText = new StringBuilder();
            for (String suggestion : suggestions) {
                suggestionText.append(suggestion).append("\n");
            }
            suggestionArea.setText(suggestionText.toString());
        }

    }

    // -----------------------------------------------------------------------
    // -----------------------------------------------------------------------



}
