package it.unipi.lsmsdb.wefood.repository.mongodb;

import it.unipi.lsmsdb.wefood.model.StarRanking;
import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.repository.base.BaseMongoDB;
import it.unipi.lsmsdb.wefood.repository.interfaces.StarRankingMongoDBInterface;
import java.util.List;
import org.bson.Document;

import com.mongodb.MongoException;

public class StarRankingMongoDB implements StarRankingMongoDBInterface {
    
    public boolean votePost(RegisteredUser user, StarRanking starRanking, PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException{
        String query = "db.Post.updateOne({\r\n" + //
                       "    _id: " + postDTO.getId() + ",\r\n" + //
                       "}, {\r\n" + //
                       "    $push: {\r\n" + //
                       "        starRankings: {\r\n" + //
                       "            idUser: " + user.editedGetId() + ",\r\n" + //
                       "            username: \"" + user.getUsername() + "\",\r\n" + //
                       "            vote: " + starRanking.getVote() + "\r\n" + //
                       "        }\r\n" + //
                       "    }\r\n" + //
                       "})";
        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    }

    public boolean deleteVote(StarRanking starRanking, PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException{
        String query = "db.Post.updateOne({\r\n" + //
               "    _id: " + postDTO.getId() + ",\r\n" + //
               "}, {\r\n" + //
               "    $pull: {\r\n" + //
               "        starRankings: {\r\n" + //
               "            username: \"" + starRanking.getUsername() + "\"\r\n" + //
               "        }\r\n" + //
               "    }\r\n" + //
               "})";
        BaseMongoDB.executeQuery(query);
        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    }

    public boolean updateAvgStarRanking(PostDTO postDTO, Double newAvgStarRanking) throws MongoException, IllegalArgumentException, IllegalStateException{
        String query = "db.Post.updateOne({\r\n" + //
               "    _id: " + postDTO.getId() + ",\r\n" + //
               "}, {\r\n" + //
               "    $set: {\r\n" + //
               "        avgStarRanking: " + newAvgStarRanking + "\r\n" + //
               "    }\r\n" + //
               "})";
        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    }

    public boolean unsetStarRankings(PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException{
        String query = "db.Post.updateOne({\r\n" + //
                       "    _id: " + postDTO.getId() + ",\r\n" + //
                       "}, {\r\n" + //
                       "    $unset: {\r\n" + //
                       "        avgStarRanking: \"\",\r\n" + //
                       "        starRankings: \"\"\r\n" + //
                       "    }\r\n" + //
                       "})";
        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    }

}