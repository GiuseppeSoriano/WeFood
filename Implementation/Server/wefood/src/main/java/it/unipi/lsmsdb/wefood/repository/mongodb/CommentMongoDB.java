package it.unipi.lsmsdb.wefood.repository.mongodb;

import it.unipi.lsmsdb.wefood.model.Comment;
import it.unipi.lsmsdb.wefood.model.Post;
import it.unipi.lsmsdb.wefood.repository.base.BaseMongoDB;
import it.unipi.lsmsdb.wefood.repository.interfaces.CommentMongoDBInterface;
import java.util.List;
import org.bson.Document;

public class CommentMongoDB implements CommentMongoDBInterface {
    private final String collectionName = "Post";
    
    public boolean commentPost(Comment comment, Post post) {
        String query = ""; //mettere query
        List<Document> result = BaseMongoDB.executeQuery(query);
        if(result.isEmpty()) {
            return false;
        } else {
            return true;
        }
    };

    public boolean updateComment(Comment comment) {
        String query = "";
        BaseMongoDB.executeQuery(query);
        List<Document> result = BaseMongoDB.executeQuery(query);
        if(result.isEmpty()) {
            return false;
        } else {
            return true;
        }
    };

    public boolean deleteComment(Comment comment, Post post) {
        String query = "";
        BaseMongoDB.executeQuery(query);
        List<Document> result = BaseMongoDB.executeQuery(query);
        if(result.isEmpty()) {
            return false;
        } else {
            return true;
        }
    };
}