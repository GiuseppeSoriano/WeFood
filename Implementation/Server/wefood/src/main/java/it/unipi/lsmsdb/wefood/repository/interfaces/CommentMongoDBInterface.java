package it.unipi.lsmsdb.wefood.repository.interfaces;

import com.mongodb.MongoException;
import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.model.Comment;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;

public interface CommentMongoDBInterface {

    boolean commentPost(RegisteredUser user, Comment comment, PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException;

    boolean updateComment(RegisteredUser user, Comment comment, PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException;

    boolean deleteComment(RegisteredUser user, Comment comment, PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException;

}
