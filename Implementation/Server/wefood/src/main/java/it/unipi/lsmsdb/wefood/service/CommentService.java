package it.unipi.lsmsdb.wefood.service;

import it.unipi.lsmsdb.wefood.dao.CommentDAO;
import it.unipi.lsmsdb.wefood.dto.PostDTO;

import org.springframework.stereotype.Service;

import com.mongodb.MongoException;

import it.unipi.lsmsdb.wefood.model.Comment;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;

@Service
public class CommentService {

    public boolean commentPost(RegisteredUser user, Comment comment, PostDTO postDTO) {
        try{
            return CommentDAO.commentPost(user, comment, postDTO);
        }
        catch(MongoException e){
            System.out.println("MongoException in Comment.commentPost: " + e.getMessage());
            return false;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in Comment.commentPost: " + e.getMessage());
            return false;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in Comment.commentPost: " + e.getMessage());
            return false;
        }
        catch(Exception e){
            System.out.println("Exception in Comment.commentPost: " + e.getMessage());
            return false;
        }
    }

    public boolean updateComment(RegisteredUser user, Comment comment, PostDTO postDTO) {
        try{
            return CommentDAO.commentPost(user, comment, postDTO);
        }
        catch(MongoException e){
            System.out.println("MongoException in Comment.updateComment: " + e.getMessage());
            return false;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in Comment.updateComment: " + e.getMessage());
            return false;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in Comment.updateComment: " + e.getMessage());
            return false;
        }
        catch(Exception e){
            System.out.println("Exception in Comment.updateComment: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteComment(RegisteredUser user, Comment comment, PostDTO postDTO) {
        try{
            return CommentDAO.commentPost(user, comment, postDTO);
        }
        catch(MongoException e){
            System.out.println("MongoException in Comment.deleteComment: " + e.getMessage());
            return false;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in Comment.deleteComment: " + e.getMessage());
            return false;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in Comment.deleteComment: " + e.getMessage());
            return false;
        }
        catch(Exception e){
            System.out.println("Exception in Comment.deleteComment: " + e.getMessage());
            return false;
        }
    }

}