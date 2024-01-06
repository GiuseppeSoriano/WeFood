package it.unipi.lsmsdb.wefood.dao;

import com.mongodb.MongoException;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.repository.interfaces.UnregisteredUserMongoDBInterface;
import it.unipi.lsmsdb.wefood.repository.interfaces.UnregisteredUserNeo4JInterface;
import it.unipi.lsmsdb.wefood.repository.mongodb.UnregisteredUserMongoDB;
import it.unipi.lsmsdb.wefood.repository.neo4j.UnregisteredUserNeo4J;
import org.neo4j.driver.exceptions.Neo4jException;

public class UnregisteredUserDAO {
    private final static UnregisteredUserNeo4JInterface unregisteredUserNeo4j = new UnregisteredUserNeo4J();
    private final static UnregisteredUserMongoDBInterface registeredUserMongoDB = new UnregisteredUserMongoDB();

    public static RegisteredUser register(String username, String password, String name, String surname) throws MongoException, IllegalArgumentException, IllegalStateException {
        return registeredUserMongoDB.register(username, password, name, surname);
    };

    // Accorpare i due metodi in uno solo e gestire il caso allo stesso modo di ingredient?
    public static boolean createRegisteredUser(RegisteredUserDTO registeredUser) throws IllegalStateException, Neo4jException  {
        return unregisteredUserNeo4j.createRegisteredUser(registeredUser);
    };
}
