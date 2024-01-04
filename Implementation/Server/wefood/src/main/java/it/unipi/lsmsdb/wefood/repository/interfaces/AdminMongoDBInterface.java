package it.unipi.lsmsdb.wefood.repository.interfaces;

import it.unipi.lsmsdb.wefood.model.Admin;

public interface AdminMongoDBInterface {

    Admin loginAdmin(String username, String password);
    
}
