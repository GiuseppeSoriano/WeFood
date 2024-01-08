package it.unipi.lsmsdb.wefood.service;

import it.unipi.lsmsdb.wefood.model.RegisteredUser;

import org.neo4j.driver.exceptions.Neo4jException;
import com.mongodb.MongoException;
import org.springframework.stereotype.Service;

import it.unipi.lsmsdb.wefood.dao.UnregisteredUserDAO;
import it.unipi.lsmsdb.wefood.dao.RegisteredUserDAO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;

@Service
public class UnregisteredUserService {

    public boolean createRegisteredUser(String username, String password, String name, String surname){
        RegisteredUser registeredUser = null;
        try{
            registeredUser = UnregisteredUserDAO.register(username, password, name, surname);
            try{
                return UnregisteredUserDAO.createRegisteredUser(new RegisteredUserDTO(registeredUser.getId(), username));
            }
            catch(Neo4jException e){
                RegisteredUserDAO.cancelUser(registeredUser.getUsername());
                System.out.println("Exception in UnregisteredUserService.createRegisteredUser: " + e.getMessage());
                return false;
            }
            catch(IllegalStateException e){
                RegisteredUserDAO.cancelUser(registeredUser.getUsername());
                System.out.println("Exception in UnregisteredUserService.createRegisteredUser: " + e.getMessage());
                return false;
            }
            catch(Exception e){
                RegisteredUserDAO.cancelUser(registeredUser.getUsername());
                System.out.println("Exception in UnregisteredUserService.createRegisteredUser: " + e.getMessage());
                return false;
            }
        }
        catch(MongoException e){
            if(registeredUser != null)
                System.err.println("Databases are not synchronized, user " + registeredUser.getId() + " has been created in MongoDB but not in Neo4j");
            System.out.println("MongoException in UnregisteredUserService.createRegisteredUser: " + e.getMessage());
            return false;
        }
        catch(IllegalArgumentException e){
            if(registeredUser != null)
                System.err.println("Databases are not synchronized, user " + registeredUser.getId() + " has been created in MongoDB but not in Neo4j");
            System.out.println("IllegalArgumentException in UnregisteredUserService.createRegisteredUser: " + e.getMessage());
            return false;
        }
        catch(IllegalStateException e){
            if(registeredUser != null)
                System.err.println("Databases are not synchronized, user " + registeredUser.getId() + " has been created in MongoDB but not in Neo4j");
            System.out.println("IllegalStateException in UnregisteredUserService.createRegisteredUser: " + e.getMessage());
            return false;
        }
        catch(Exception e){
            if(registeredUser != null)
                System.err.println("Databases are not synchronized, user " + registeredUser.getId() + " has been created in MongoDB but not in Neo4j");
            System.out.println("Exception in UnregisteredUserService.createRegisteredUser: " + e.getMessage());
            return false;
        }
    }

}