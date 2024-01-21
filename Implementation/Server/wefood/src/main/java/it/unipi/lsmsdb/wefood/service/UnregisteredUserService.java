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

    public RegisteredUser createRegisteredUser(String username, String password, String name, String surname){
        RegisteredUser registeredUser = null;
        try{
            // We need to create the user in MongoDB first
            registeredUser = UnregisteredUserDAO.register(username, password, name, surname);
            try{
                // Then we create the user in Neo4j
                UnregisteredUserDAO.createRegisteredUser(new RegisteredUserDTO(registeredUser.getId(), username));
                return registeredUser;
            }
            // Other types of exceptions can be handled if necessary: Neo4jException, IllegalStateException
            catch(Exception e){
                RegisteredUserDAO.cancelUserMongoDB(registeredUser.getUsername());
                System.err.println("Exception in UnregisteredUserDAO.createRegisteredUser: " + e.getMessage());
                return null;
            }
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e){
            if(registeredUser != null)
                System.err.println("Databases are not synchronized, user " + registeredUser.getId() + " has been created in MongoDB but not in Neo4j");
            if(e.getMessage().contains("duplicate key error"))
                System.out.println("Username already exists");
            else
                System.err.println("Exception in UnregisteredUserDAO.register: " + e.getMessage());
            return null;
        }
    }

}