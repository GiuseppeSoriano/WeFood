package it.unipi.lsmsdb.wefood.repository.interfaces;

import com.mongodb.MongoException;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;

public interface UnregisteredUserMongoDBInterface {

    RegisteredUser register(String username, String password, String name, String surname) throws MongoException, IllegalArgumentException, IllegalStateException ;

}
