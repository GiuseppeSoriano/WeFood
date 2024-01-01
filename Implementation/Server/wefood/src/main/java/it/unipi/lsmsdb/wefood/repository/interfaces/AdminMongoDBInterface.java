package it.unipi.lsmsdb.wefood.repository.interfaces;

import it.unipi.lsmsdb.wefood.model.Admin;
import it.unipi.lsmsdb.wefood.model.User;

public interface AdminMongoDBInterface {

    Admin login(String username, String password);
    boolean logout();
    
    boolean banUser(User user);
    boolean unbanUser(User user);

    



}
