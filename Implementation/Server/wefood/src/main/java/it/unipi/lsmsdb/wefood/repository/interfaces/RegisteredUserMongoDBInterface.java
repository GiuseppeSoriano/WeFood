package it.unipi.lsmsdb.wefood.repository.interfaces;

import com.mongodb.MongoException;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserPageDTO;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;
//import it.unipi.lsmsdb.wefood.model.Post;

public interface RegisteredUserMongoDBInterface {

    RegisteredUser login(String username, String password) throws MongoException, IllegalArgumentException, IllegalStateException;

    // RegisteredUser findRegisteredUserById(String _id);

    RegisteredUserPageDTO findRegisteredUserPageByUsername(String username) throws MongoException, IllegalArgumentException, IllegalStateException;

    boolean modifyPersonalInformation(RegisteredUser user) throws MongoException, IllegalArgumentException, IllegalStateException;

    boolean deleteUser(RegisteredUser user) throws MongoException, IllegalArgumentException, IllegalStateException;

    boolean banUser(String username) throws MongoException, IllegalArgumentException, IllegalStateException;
    boolean unbanUser(String username) throws MongoException, IllegalArgumentException, IllegalStateException;

}
