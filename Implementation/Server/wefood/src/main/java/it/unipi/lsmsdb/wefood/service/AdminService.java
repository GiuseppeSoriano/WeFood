package it.unipi.lsmsdb.wefood.service;

import org.springframework.stereotype.Service;

import com.mongodb.MongoException;

import it.unipi.lsmsdb.wefood.dao.AdminDAO;
import it.unipi.lsmsdb.wefood.model.Admin;

@Service 
public class AdminService {
    
    public Admin loginAdmin(String username, String password){
        try{
            return AdminDAO.loginAdmin(username, password);
        }
        catch(MongoException e){
            System.out.println("MongoException in AdminService.loginAdmin: " + e.getMessage());
            return null;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in AdminService.loginAdmin: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in AdminService.loginAdmin: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in AdminService.loginAdmin: " + e.getMessage());
            return null;
        }
    }

}
