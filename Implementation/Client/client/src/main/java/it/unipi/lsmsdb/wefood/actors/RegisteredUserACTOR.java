package it.unipi.lsmsdb.wefood.actors;

import java.util.Scanner;

import it.unipi.lsmsdb.wefood.httprequests.IngredientHTTP;
import it.unipi.lsmsdb.wefood.httprequests.RegisteredUserHTTP;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.utility.Reader;

public class RegisteredUserACTOR {
    private static RegisteredUser info = null;
    
    private final static RegisteredUserHTTP registeredUserHTTP = new RegisteredUserHTTP();
    private final static IngredientHTTP ingredientHTTP = new IngredientHTTP();
    
    private final static Scanner scanner = new Scanner(System.in);

    public static void login(){
        if(info != null){
            System.out.println("You are already logged in as user");
            return;
        }
        if(AdminACTOR.getInfo() != null){
            System.out.println("You are already logged in as admin");
            return;
        }
        info = registeredUserHTTP.login(Reader.readLoginRequestDTO());
        if(info != null)
            System.out.println("Logged in as admin");
        else
            System.out.println("Error while logging in");
    }
    
    public static void logout(){
        info = null;
        System.out.println("Logged out");
    }

    public static RegisteredUser getInfo(){
        return info;
    }
}
