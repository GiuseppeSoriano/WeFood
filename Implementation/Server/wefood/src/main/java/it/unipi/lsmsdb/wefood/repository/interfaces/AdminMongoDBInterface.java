package it.unipi.lsmsdb.wefood.repository.interfaces;

import com.mongodb.MongoException;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import it.unipi.lsmsdb.wefood.model.Admin;

import java.util.List;

public interface AdminMongoDBInterface {

    Admin loginAdmin(String username, String password) throws MongoException, IllegalArgumentException, IllegalStateException;

    List<RegisteredUserDTO> findBannedUsers();
    
}
