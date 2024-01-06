package it.unipi.lsmsdb.wefood.dao;

import com.mongodb.MongoException;
import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.model.StarRanking;
import it.unipi.lsmsdb.wefood.repository.interfaces.StarRankingMongoDBInterface;
import it.unipi.lsmsdb.wefood.repository.mongodb.StarRankingMongoDB;

public class StarRankingDAO {
    private final static StarRankingMongoDBInterface starRankingMongoDB = new StarRankingMongoDB();

    public static boolean votePost(RegisteredUser user, StarRanking starRanking, PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException {
        return starRankingMongoDB.votePost(user, starRanking, postDTO);
    }

    public static boolean deleteVote(RegisteredUser user, PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException {
        return starRankingMongoDB.deleteVote(user, postDTO);
    }

}
