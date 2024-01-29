package it.unipi.lsmsdb.wefood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.unipi.lsmsdb.wefood.repository.base.BaseMongoDB;
import it.unipi.lsmsdb.wefood.repository.base.BaseNeo4j;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
public class WefoodApplication {

	public static void main(String[] args) {
		BaseMongoDB.openMongoClient();
		BaseNeo4j.openNeo4jDriver();

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			BaseMongoDB.closeMongoClient();
			BaseNeo4j.closeNeo4jDriver();
			System.out.println("Connections have been closed properly.");
		}));

		SpringApplication.run(WefoodApplication.class, args); //MAIN APPLICATION
	}

}
