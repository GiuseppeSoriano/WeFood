package it.unipi.lsmsdb.wefood.repository.interfaces;

import it.unipi.lsmsdb.wefood.model.RegisteredUser;
//import it.unipi.lsmsdb.wefood.model.Post;

public interface RegisteredUserMongoDBInterface {

    RegisteredUser login(String username, String password);

    RegisteredUser findRegisteredUserByUsername(String username);

    boolean modifyPersonalProfile(RegisteredUser user);

    boolean deleteUser(RegisteredUser user);

    
}
