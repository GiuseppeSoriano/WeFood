package it.unipi.lsmsdb.wefood.utility;

import java.util.Scanner;

import it.unipi.lsmsdb.wefood.apidto.IngredientAndLimitRequestDTO;
import it.unipi.lsmsdb.wefood.apidto.LoginRequestDTO;
import it.unipi.lsmsdb.wefood.model.Ingredient;

public class Reader {
    private final static Scanner scanner = new Scanner(System.in);

    public static LoginRequestDTO readLoginRequestDTO(){
        System.out.println("Insert username: ");
        String username = scanner.nextLine();
        System.out.println("Insert password: ");
        String password = scanner.nextLine();

        return new LoginRequestDTO(username, password);
    }

    public static Ingredient readIngredient(){
        System.out.println("Insert ingredient name: ");
        String name = scanner.nextLine();
        System.out.println("Insert calories: ");
        double calories = scanner.nextDouble();

        return new Ingredient(name, calories);
    }

    public static IngredientAndLimitRequestDTO readIngredientAndLimitRequestDTO(){
        System.out.println("Insert ingredient name: ");
        String name = scanner.nextLine();
        System.out.println("Insert limit: ");
        int limit = scanner.nextInt();

        return new IngredientAndLimitRequestDTO(name, limit);
    }
}
