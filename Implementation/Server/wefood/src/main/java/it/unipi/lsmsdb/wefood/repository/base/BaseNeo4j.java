package it.unipi.lsmsdb.wefood.repository.base;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.exceptions.Neo4jException;

import java.util.List;

public abstract class BaseNeo4j {

    private static final String NEO4J_HOST = "10.1.1.24";
    private static final String NEO4J_PORT = "7687";

    private static String URI = String.format("bolt://%s:/%s", 
                                              NEO4J_HOST,
                                              NEO4J_PORT);

    private static final String NEO4J_USERNAME = "neo4j";
    private static final String NEO4J_PASSWORD = "password";
    
    private static Driver driver;

    
    public static Driver getNeo4jDriver() {
        try {
            driver = GraphDatabase.driver(URI, AuthTokens.basic(NEO4J_USERNAME, NEO4J_PASSWORD));
            return driver;
        } 
        catch (Neo4jException e) {
            System.out.println("Failed to create the driver: " + e.getMessage());
            return null;
        }
    }

    public void closeNeo4jDriver() {
        if (driver != null) {
            driver.close();
        }
    }

    public List<Record> query(String query) {
        if (driver == null) {
            throw new IllegalStateException("Driver not initialized!");
        }
        try (Session session = driver.session()) {
            Result result = session.run(query);
            return result.list();
        } 
        catch (Neo4jException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

}
