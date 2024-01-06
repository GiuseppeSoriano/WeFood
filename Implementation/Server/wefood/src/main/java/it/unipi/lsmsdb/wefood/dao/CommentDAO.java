package it.unipi.lsmsdb.wefood.dao;

import com.mongodb.MongoException;
import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.model.Comment;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.repository.interfaces.CommentMongoDBInterface;
import it.unipi.lsmsdb.wefood.repository.mongodb.CommentMongoDB;

public class CommentDAO {
    private final static CommentMongoDBInterface commentMongoDBInterface = new CommentMongoDB();

    public static boolean commentPost(RegisteredUser user, Comment comment, PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException {
        return commentMongoDBInterface.commentPost(user, comment, postDTO);
    }

    public static boolean updateComment(RegisteredUser user, Comment comment, PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException {
        return commentMongoDBInterface.updateComment(user, comment, postDTO);
    }

    public static boolean deleteComment(RegisteredUser user, Comment comment, PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException {
        return commentMongoDBInterface.deleteComment(user, comment, postDTO);
    }
}
