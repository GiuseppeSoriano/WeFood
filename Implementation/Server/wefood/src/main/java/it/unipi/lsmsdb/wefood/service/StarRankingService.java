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
        boolean created = false;
        try{
            created = StarRankingDAO.votePost(user, starRanking, postDTO);
            // Compute the new average star ranking
            try{
                StarRankingDAO.computeAverageStarRanking(postDTO);
            }
            catch(MongoException e){
                System.out.println("MongoException in StarRanking.votePost: " + e.getMessage());
                StarRankingDAO.deleteVote(user, postDTO);
                return false;
            }
            catch(IllegalArgumentException e){
                System.out.println("IllegalArgumentException in StarRanking.votePost: " + e.getMessage());
                StarRankingDAO.deleteVote(user, postDTO);
                return false;
            }
            catch(IllegalStateException e){
                System.out.println("IllegalStateException in StarRanking.votePost: " + e.getMessage());
                StarRankingDAO.deleteVote(user, postDTO);
                return false;
            }
            catch(Exception e){
                System.out.println("Exception in StarRanking.votePost: " + e.getMessage());
                StarRankingDAO.deleteVote(user, postDTO);
                return false;
            }
        }
        catch(MongoException e){
            if(created)
                System.err.println("Databases are not synchronized, vote has been created in MongoDB but average star ranking has not been computed in MongoDB");
            System.out.println("MongoException in StarRanking.votePost: " + e.getMessage());
            return false;
        }
        catch(IllegalArgumentException e){
            if(created)
                System.err.println("Databases are not synchronized, vote has been created in MongoDB but average star ranking has not been computed in MongoDB");
            System.out.println("IllegalArgumentException in StarRanking.votePost: " + e.getMessage());
            return false;
        }
        catch(IllegalStateException e){
            if(created)
                System.err.println("Databases are not synchronized, vote has been created in MongoDB but average star ranking has not been computed in MongoDB");
            System.out.println("IllegalStateException in StarRanking.votePost: " + e.getMessage());
            return false;
        }
        catch(Exception e){
            if(created)
                System.err.println("Databases are not synchronized, vote has been created in MongoDB but average star ranking has not been computed in MongoDB");
            System.out.println("Exception in StarRanking.votePost: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteVote(RegisteredUser user, PostDTO postDTO) {
        boolean deleted = false;
        try{
            deleted = StarRankingDAO.deleteVote(user, postDTO);
            // Compute the new average star ranking
            try{
                StarRankingDAO.computeAverageStarRanking(postDTO);      // A Solution may be to return with this method the new average star ranking, in order to allow the rollback of the vote in case of failure
            }
            catch(MongoException e){
                System.out.println("MongoException in StarRanking.votePost: " + e.getMessage());
                StarRankingDAO.setAverage(user, postDTO);
                return false;
            }
            catch(IllegalArgumentException e){
                System.out.println("IllegalArgumentException in StarRanking.votePost: " + e.getMessage());
                StarRankingDAO.setAverage(user, postDTO);
                return false;
            }
            catch(IllegalStateException e){
                System.out.println("IllegalStateException in StarRanking.votePost: " + e.getMessage());
                StarRankingDAO.setAverage(user, postDTO);
                return false;
            }
            catch(Exception e){
                System.out.println("Exception in StarRanking.votePost: " + e.getMessage());
                StarRankingDAO.setAverage(user, postDTO);
                return false;
            }
        }
        catch(MongoException e){
            if(deleted)
                System.err.println("Databases are not synchronized, vote has been deleted in MongoDB but average star ranking has not been computed in MongoDB");
            System.out.println("MongoException in StarRanking.votePost: " + e.getMessage());
            return false;
        }
        catch(IllegalArgumentException e){
            if(deleted)
                System.err.println("Databases are not synchronized, vote has been deleted in MongoDB but average star ranking has not been computed in MongoDB");
            System.out.println("IllegalArgumentException in StarRanking.votePost: " + e.getMessage());
            return false;
        }
        catch(IllegalStateException e){
            if(deleted)
                System.err.println("Databases are not synchronized, vote has been deleted in MongoDB but average star ranking has not been computed in MongoDB");
            System.out.println("IllegalStateException in StarRanking.votePost: " + e.getMessage());
            return false;
        }
        catch(Exception e){
            if(deleted)
                System.err.println("Databases are not synchronized, vote has been deleted in MongoDB but average star ranking has not been computed in MongoDB");
            System.out.println("Exception in StarRanking.votePost: " + e.getMessage());
            return false;
        }
    }

}