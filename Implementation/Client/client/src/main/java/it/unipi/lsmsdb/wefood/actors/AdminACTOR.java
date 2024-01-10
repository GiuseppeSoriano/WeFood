package it.unipi.lsmsdb.wefood.actors;

import it.unipi.lsmsdb.wefood.apidto.LoginRequestDTO;
import it.unipi.lsmsdb.wefood.httprequests.AdminHTTP;
import it.unipi.lsmsdb.wefood.model.Admin;

public class AdminACTOR{
    private Admin info = null;

    public boolean login(){
        String username = "";
        String password = "";
        // CHIEDI ALL'UTENTE DI INSERIRE CREDENZIALI
        System.out.println("Insert username: ");
        
        LoginRequestDTO credentials = new LoginRequestDTO(username, password);
        info = new AdminHTTP().loginAdmin(credentials);
        return info != null;
    }
    
    public boolean logout(){
        info = null;
        return true;
    }

    
    

}