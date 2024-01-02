package it.unipi.lsmsdb.wefood.repository.interfaces;

import it.unipi.lsmsdb.wefood.dto.RegisteredUserPageDTO;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;
//import it.unipi.lsmsdb.wefood.model.Post;

public interface RegisteredUserMongoDBInterface {

    RegisteredUser login(String username, String password);

    RegisteredUser findRegisteredUserByUsername(String username);
    
    RegisteredUserPageDTO findRegisteredUserPageByUsername(String username);

    boolean modifyPersonalInformation(RegisteredUser user);

    boolean deleteUser(RegisteredUser user);

    boolean banUser(RegisteredUser user);
    boolean unbanUser(RegisteredUser user);
    
}
