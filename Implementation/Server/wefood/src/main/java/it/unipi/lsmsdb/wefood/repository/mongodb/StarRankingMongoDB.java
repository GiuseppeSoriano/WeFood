package it.unipi.lsmsdb.wefood.repository.mongodb;

import it.unipi.lsmsdb.wefood.model.StarRanking;
import it.unipi.lsmsdb.wefood.model.Post;
import it.unipi.lsmsdb.wefood.repository.base.BaseMongoDB;
import it.unipi.lsmsdb.wefood.repository.interfaces.StarRankingMongoDBInterface;
import java.util.List;
import org.bson.Document;

public class StarRankingMongoDB implements StarRankingMongoDBInterface {
    private final String collectionName = "Post";
    
    public boolean votePost(StarRanking starRanking, Post post) {
        String query = ""; //mettere query
        List<Document> result = BaseMongoDB.executeQuery(query);
        if(result.isEmpty()) {
            return false;
        } else {
            return true;
        }
    };

    public boolean deleteVote(StarRanking starRanking, Post post) {
        String query = "";
        BaseMongoDB.executeQuery(query);
        List<Document> result = BaseMongoDB.executeQuery(query);
        if(result.isEmpty()) {
            return false;
        } else {
            return true;
        }
    };
}