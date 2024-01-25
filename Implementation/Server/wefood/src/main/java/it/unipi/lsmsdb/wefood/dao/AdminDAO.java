package it.unipi.lsmsdb.wefood.dao;

import com.mongodb.MongoException;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import it.unipi.lsmsdb.wefood.model.Admin;
import it.unipi.lsmsdb.wefood.repository.interfaces.AdminMongoDBInterface;
import it.unipi.lsmsdb.wefood.repository.mongodb.AdminMongoDB;

import java.util.List;

public class AdminDAO {
    private final static AdminMongoDBInterface adminMongoDB = new AdminMongoDB();

    public static Admin loginAdmin(String username, String password) throws MongoException, IllegalArgumentException, IllegalStateException {
        return adminMongoDB.loginAdmin(username, password);
    }

    public static List<RegisteredUserDTO> findBannedUsers() {
        return adminMongoDB.findBannedUsers();
    }
}
