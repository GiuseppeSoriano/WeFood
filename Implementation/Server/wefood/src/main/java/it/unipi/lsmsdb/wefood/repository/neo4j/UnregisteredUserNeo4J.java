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
                       "    _id: '" + registeredUserDTO.neo4JgetId() + "',\r\n" + //
                       "    username: '" + registeredUserDTO.getUsername() + "'\r\n" + //
                       "})";
        List<Record> results = BaseNeo4j.executeQuery(query);
        System.out.println(results.get(0));

        return true;
    }

}
