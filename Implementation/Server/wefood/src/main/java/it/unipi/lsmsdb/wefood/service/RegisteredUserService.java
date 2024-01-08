package it.unipi.lsmsdb.wefood.service;

import org.springframework.stereotype.Service;

import com.mongodb.MongoException;

import it.unipi.lsmsdb.wefood.dao.RegisteredUserDAO;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;

@Service
public class RegisteredUserService {

    public RegisteredUser login(String username, String password){
        try{
            return RegisteredUserDAO.login(username, password);
        }
        catch(MongoException e){
            System.out.println("MongoException in RegisteredUserDAO.login: " + e.getMessage());
            return null;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in RegisteredUserDAO.login: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in RegisteredUserDAO.login: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in RegisteredUserDAO.login: " + e.getMessage());
            return null;
        }
    }

}
