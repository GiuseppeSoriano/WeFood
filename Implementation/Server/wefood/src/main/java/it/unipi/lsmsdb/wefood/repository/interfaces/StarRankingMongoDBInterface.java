package it.unipi.lsmsdb.wefood.repository.interfaces;

import it.unipi.lsmsdb.wefood.model.StarRanking;
import it.unipi.lsmsdb.wefood.model.Post;

public interface StarRankingMongoDBInterface {

    boolean votePost(StarRanking starRanking, Post post);

    boolean deleteVote(StarRanking starRanking, Post post);

}
