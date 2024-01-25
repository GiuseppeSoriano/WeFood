package it.unipi.lsmsdb.wefood.service;

import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;

import it.unipi.lsmsdb.wefood.dao.AdminDAO;
import it.unipi.lsmsdb.wefood.model.Admin;

import java.util.List;

@Service 
public class AdminService {
    
    public Admin loginAdmin(String username, String password){
        try{
            return AdminDAO.loginAdmin(username, password);
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in AdminDAO.loginAdmin: " + e.getMessage());
            return null;
        }
    }

    public List<RegisteredUserDTO> findBannedUsers() {
        try{
            return AdminDAO.findBannedUsers();
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in AdminDAO.findBannedUsers: " + e.getMessage());
            return null;
        }
    }
}
