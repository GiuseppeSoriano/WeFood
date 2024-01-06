package it.unipi.lsmsdb.wefood.repository.interfaces;


import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import org.neo4j.driver.exceptions.Neo4jException;

public interface UnregisteredUserNeo4JInterface {

    boolean createRegisteredUser(RegisteredUserDTO registeredUserDTO) throws IllegalStateException, Neo4jException;

}
