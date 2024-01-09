package it.unipi.lsmsdb.wefood.service;

import it.unipi.lsmsdb.wefood.dao.PostDAO;
import it.unipi.lsmsdb.wefood.dao.StarRankingDAO;
import it.unipi.lsmsdb.wefood.dto.PostDTO;

import org.springframework.stereotype.Service;

import com.mongodb.MongoException;

import it.unipi.lsmsdb.wefood.model.StarRanking;
import it.unipi.lsmsdb.wefood.model.Post;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;

@Service
public class StarRankingService {
    
    public boolean votePost(RegisteredUser user, StarRanking starRanking, PostDTO postDTO) {
        boolean created = false;
        try{
            Post post = PostDAO.findPostByPostDTO(postDTO); // to update redundancies
            created = StarRankingDAO.votePost(user, starRanking, postDTO);
            try{
                // Computing the new average star ranking
                Double oldAvgStarRanking = post.getAvgStarRanking();
                Double oldNumStarRanking = (double) post.getStarRankings().size();
                Double newAvgStarRanking = starRanking.getVote();
                if(oldNumStarRanking > 0.0)
                    newAvgStarRanking = (oldAvgStarRanking * oldNumStarRanking + starRanking.getVote()) / (oldNumStarRanking + 1);
                StarRankingDAO.updateAvgStarRanking(postDTO, newAvgStarRanking);
                return true;
            }
            catch(MongoException e){
                System.out.println("MongoException in StarRanking.votePost: " + e.getMessage());
                StarRankingDAO.deleteVote(starRanking, postDTO);
                return false;
            }
            catch(IllegalArgumentException e){
                System.out.println("IllegalArgumentException in StarRanking.votePost: " + e.getMessage());
                StarRankingDAO.deleteVote(starRanking, postDTO);
                return false;
            }
            catch(IllegalStateException e){
                System.out.println("IllegalStateException in StarRanking.votePost: " + e.getMessage());
                StarRankingDAO.deleteVote(starRanking, postDTO);
                return false;
            }
            catch(Exception e){
                System.out.println("Exception in StarRanking.votePost: " + e.getMessage());
                StarRankingDAO.deleteVote(starRanking, postDTO);
                return false;
            }
        }
        catch(MongoException e){
            if(created)
                System.err.println("MongoDB is not consistent, vote has been created in MongoDB but average star ranking has not been computed in MongoDB");
            System.out.println("MongoException in StarRanking.votePost: " + e.getMessage());
            return false;
        }
        catch(IllegalArgumentException e){
            if(created)
                System.err.println("MongoDB is not consistent, vote has been created in MongoDB but average star ranking has not been computed in MongoDB");
            System.out.println("IllegalArgumentException in StarRanking.votePost: " + e.getMessage());
            return false;
        }
        catch(IllegalStateException e){
            if(created)
                System.err.println("MongoDB is not consistent, vote has been created in MongoDB but average star ranking has not been computed in MongoDB");
            System.out.println("IllegalStateException in StarRanking.votePost: " + e.getMessage());
            return false;
        }
        catch(Exception e){
            if(created)
                System.err.println("MongoDB is not consistent, vote has been created in MongoDB but average star ranking has not been computed in MongoDB");
            System.out.println("Exception in StarRanking.votePost: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteVote(RegisteredUser user, StarRanking starRanking, PostDTO postDTO) {
        boolean deleted = false;
        try{
            Post post = PostDAO.findPostByPostDTO(postDTO); // to update redundancies
            deleted = StarRankingDAO.deleteVote(starRanking, postDTO);
            // Compute the new average star ranking
            try{
                Double oldAvgStarRanking = post.getAvgStarRanking();
                Double oldNumStarRanking = (double) post.getStarRankings().size();
                if(oldNumStarRanking == 1.0)
                    StarRankingDAO.unsetStarRankings(postDTO);    
                else{
                    Double newAvgStarRanking = (oldAvgStarRanking * oldNumStarRanking - starRanking.getVote()) / (oldNumStarRanking - 1);
                    StarRankingDAO.updateAvgStarRanking(postDTO, newAvgStarRanking);
                }           
                return true;         
            }
            catch(MongoException e){
                System.out.println("MongoException in StarRanking.votePost: " + e.getMessage());
                StarRankingDAO.votePost(user, starRanking, postDTO);
                return false;
            }
            catch(IllegalArgumentException e){
                System.out.println("IllegalArgumentException in StarRanking.votePost: " + e.getMessage());
                StarRankingDAO.votePost(user, starRanking, postDTO);
                return false;
            }
            catch(IllegalStateException e){
                System.out.println("IllegalStateException in StarRanking.votePost: " + e.getMessage());
                StarRankingDAO.votePost(user, starRanking, postDTO);
                return false;
            }
            catch(Exception e){
                System.out.println("Exception in StarRanking.votePost: " + e.getMessage());
                StarRankingDAO.votePost(user, starRanking, postDTO);
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