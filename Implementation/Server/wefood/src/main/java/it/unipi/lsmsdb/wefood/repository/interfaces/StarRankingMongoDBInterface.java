package it.unipi.lsmsdb.wefood.repository.interfaces;

import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.model.StarRanking;

import com.mongodb.MongoException;

import it.unipi.lsmsdb.wefood.dto.PostDTO;

public interface StarRankingMongoDBInterface {

    boolean votePost(RegisteredUser user, StarRanking starRanking, PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException;

    boolean deleteVote(RegisteredUser user, PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException;

}
