package it.unipi.lsmsdb.wefood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import com.fasterxml.jackson.databind.JsonSerializable.Base;

import it.unipi.lsmsdb.wefood.repository.base.BaseMongoDB;
import it.unipi.lsmsdb.wefood.repository.base.BaseNeo4j;

@SpringBootApplication
public class WefoodApplication {

	public static void main(String[] args) {
		// BaseMongoDB.openMongoClient();
		// BaseNeo4j.openNeo4jDriver();

		SpringApplication.run(WefoodApplication.class, args); //MAIN APPLICATION
		
		// BaseMongoDB.closeMongoClient();
		// BaseNeo4j.closeNeo4jDriver();
	}

}
