package it.unipi.lsmsdb.wefood.service;

import it.unipi.lsmsdb.wefood.dao.StarRankingDAO;
import it.unipi.lsmsdb.wefood.dto.PostDTO;

import org.springframework.stereotype.Service;

import it.unipi.lsmsdb.wefood.model.StarRanking;
import it.unipi.lsmsdb.wefood.model.Post;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;

@Service
public class StarRankingService {
    
    public boolean votePost(RegisteredUser user, StarRanking starRanking, PostDTO postDTO, Post post) {

        try{
            StarRankingDAO.votePost(user, starRanking, postDTO);
            try{
                Double sum_votes = starRanking.getVote();
                // Computing the new average star ranking
                for(StarRanking oldStarRanking: post.getStarRankings()){
                    sum_votes += oldStarRanking.getVote();
                }
                Double newAvgStarRanking = sum_votes / (post.getStarRankings().size() + 1);

                StarRankingDAO.updateAvgStarRanking(postDTO, newAvgStarRanking);
                return true;
            }
            // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
            catch(Exception e){
                System.out.println("Exception in StarRankingDAO.updateAvgStarRanking: " + e.getMessage());
                System.err.println("MongoDB is not consistent, vote has been added in MongoDB but average star ranking has not been updated in MongoDB");
                return false;
            }
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e){
            System.out.println("Exception in StarRankingDAO.votePost: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteVote(StarRanking starRanking, PostDTO postDTO, Post post) {

        try{
            StarRankingDAO.deleteVote(starRanking, postDTO);
            try{
                if(post.getStarRankings().size() == 1){
                    StarRankingDAO.unsetStarRankings(postDTO);
                    return true;
                }

                Double sum_votes = -starRanking.getVote();
                // Computing the new average star ranking
                for(StarRanking oldStarRanking: post.getStarRankings()){
                    sum_votes += oldStarRanking.getVote();
                }
                Double newAvgStarRanking = sum_votes / (post.getStarRankings().size() - 1);

                StarRankingDAO.updateAvgStarRanking(postDTO, newAvgStarRanking);
                return true;
            }
            // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
            catch(Exception e){
                System.err.println("Exception in StarRankingDAO.unsetStarRankings OR StarRankingDAO.updateAvgStarRanking: " + e.getMessage());
                System.err.println("MongoDB is not consistent, vote has been deleted in MongoDB but average star ranking has not been updated in MongoDB");
                return false;
            }
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e){
            System.err.println("Exception in StarRankingDAO.deleteVote: " + e.getMessage());
            return false;
        }
    }

}