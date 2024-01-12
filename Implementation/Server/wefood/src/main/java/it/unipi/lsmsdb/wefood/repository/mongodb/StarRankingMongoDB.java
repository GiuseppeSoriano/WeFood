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
        String query = "db.Post.updateOne({" + //
                            "_id: " + postDTO.editedGetId() + "," + //
                       "}, {" + //
                            "$push: {" + //
                                "starRankings: {" + //
                                    "idUser: " + user.editedGetId() + "," + //
                                    "username: \"" + user.getUsername() + "\"," + //
                                    "vote: " + starRanking.getVote() + //
                                "}" + //
                            "}" + //
                       "})";
        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    }

    public boolean deleteVote(StarRanking starRanking, PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException{
        String query = "db.Post.updateOne({" + //
                    "_id: " + postDTO.editedGetId() + "," + //
               "}, {" + //
                    "$pull: {" + //
                        "starRankings: {" + //
                            "username: \"" + starRanking.getUsername() + "\"" + //
                        "}" + //
                    "}" + //
               "})";
        BaseMongoDB.executeQuery(query);
        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    }

    public boolean updateAvgStarRanking(PostDTO postDTO, Double newAvgStarRanking) throws MongoException, IllegalArgumentException, IllegalStateException{
        String query = "db.Post.updateOne({" + //
                    "_id: " + postDTO.editedGetId() + "," + //
               "}, {" + //
                    "$set: {" + //
                        "avgStarRanking: " + newAvgStarRanking + //
                    "}" + //
               "})";
        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    }

    public boolean unsetStarRankings(PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException{
        String query = "db.Post.updateOne({" + //
                            "_id: " + postDTO.editedGetId() + "," + //
                       "}, {" + //
                            "$unset: {" + //
                                "avgStarRanking: \"\"," + //
                                "starRankings: \"\"" + //
                            "}" + //
                       "})";
        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    }

}