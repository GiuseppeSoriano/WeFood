package it.unipi.lsmsdb.wefood.service;

import it.unipi.lsmsdb.wefood.dao.StarRankingDAO;
import it.unipi.lsmsdb.wefood.dto.PostDTO;

import org.springframework.stereotype.Service;

import com.mongodb.MongoException;

import it.unipi.lsmsdb.wefood.model.StarRanking;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;

@Service
public class StarRankingService {
    
    //aggiungere la consistenza dell'average star ranking
    public boolean votePost(RegisteredUser user, StarRanking starRanking, PostDTO postDTO) {
        try{
            return StarRankingDAO.votePost(user, starRanking, postDTO);
        }
        catch(MongoException e){
            System.out.println("MongoException in StarRanking.votePost: " + e.getMessage());
            return false;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in StarRanking.votePost: " + e.getMessage());
            return false;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in StarRanking.votePost: " + e.getMessage());
            return false;
        }
        catch(Exception e){
            System.out.println("Exception in StarRanking.votePost: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteVote(RegisteredUser user, PostDTO postDTO) {
        try{
            return StarRankingDAO.deleteVote(user, postDTO);
        }
        catch(MongoException e){
            System.out.println("MongoException in StarRankingDAO.deleteVote: " + e.getMessage());
            return false;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in StarRankingDAO.deleteVote: " + e.getMessage());
            return false;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in StarRankingDAO.deleteVote: " + e.getMessage());
            return false;
        }
        catch(Exception e){
            System.out.println("Exception in StarRankingDAO.deleteVote: " + e.getMessage());
            return false;
        }
    }

}