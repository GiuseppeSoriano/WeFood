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
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in CommentDAO.commentPost: " + e.getMessage());
            return false;
        }
    }

    public boolean updateComment(RegisteredUser user, Comment comment, PostDTO postDTO) {
        try{
            return CommentDAO.updateComment(user, comment, postDTO);
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in CommentDAO.updateComment: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteComment(RegisteredUser user, Comment comment, PostDTO postDTO) {
        try{
            return CommentDAO.deleteComment(user, comment, postDTO);
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in CommentDAO.deleteComment: " + e.getMessage());
            return false;
        }
    }

}