package it.unipi.lsmsdb.wefood.repository.neo4j;

import java.util.List;
import org.neo4j.driver.Record;
import org.neo4j.driver.exceptions.Neo4jException;

import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import it.unipi.lsmsdb.wefood.repository.base.BaseNeo4j;
import it.unipi.lsmsdb.wefood.repository.interfaces.UnregisteredUserNeo4JInterface;

public class UnregisteredUserNeo4J implements UnregisteredUserNeo4JInterface {

    public boolean createRegisteredUser(RegisteredUserDTO registeredUserDTO) throws IllegalStateException, Neo4jException {
        String query = "CREATE (u:User {\r\n" + //
                       "    _id: '" + registeredUserDTO.getId() + "',\r\n" + //
                       "    username: '" + registeredUserDTO.getUsername() + "'\r\n" + //
                       "})";
        BaseNeo4j.executeQuery(query);

        return true;
    }

}
