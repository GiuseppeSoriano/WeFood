package it.unipi.lsmsdb.wefood.repository.mongodb;

import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.model.Comment;
import it.unipi.lsmsdb.wefood.repository.base.BaseMongoDB;
import it.unipi.lsmsdb.wefood.repository.interfaces.CommentMongoDBInterface;
import java.util.List;
import org.bson.Document;

import com.mongodb.MongoException;

public class CommentMongoDB implements CommentMongoDBInterface {
    
    public boolean commentPost(Comment comment, PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException {

        String query = "db.Post.updateOne({\r\n" + //
                       "    _id: " + postDTO.getId() + ",\r\n" + //
                       "}, {\r\n" + //
                       "    $push: {\r\n" + //
                       "        comments: {\r\n" + //
                       "            idUser: " + comment.getUser().getId() + ",\r\n" + //
                       "            username: " + comment.getUser().getUsername() + ",\r\n" + //
                       "            text: " + comment.getText() + ",\r\n" + //
                       "            timestamp: " + comment.getTimestamp().getTime() + "\r\n" + //
                       "        }\r\n" + //
                       "    }\r\n" + //
                       "})"; //
        
        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;

    };

    public boolean updateComment(Comment comment, PostDTO postDTO) {

        String query = "db.Post.updateOne({\r\n" + //
                       "    _id: " + postDTO.getId() + ",\r\n" + //
                       "    comments: {\r\n" + //
                       "        $elemMatch: {\r\n" + //
                       "            idUser: " + comment.getUser().getId() + ",\r\n" + //
                       "            timestamp: " + comment.getTimestamp().getTime() + ",\r\n" + //
                       "        }\r\n" + //
                       "    }\r\n" + //
                       "}, {\r\n" + //
                       "    $set: {\r\n" + //
                       "        \"comments.$.text\": " + comment.getText() + ",\r\n" + //
                       "    }\r\n" + //
                       "})";

        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    };

    public boolean deleteComment(Comment comment, PostDTO postDTO) {
        
        String query = "db.Post.updateOne({\r\n" + //
                       "    _id: " + postDTO.getId() + ",\r\n" + //
                       "}, {\r\n" + //
                       "    $pull: {\r\n" + //
                       "        comments: {\r\n" + //
                       "            idUser: " + comment.getUser().getId() + ",\r\n" + //
                       "            timestamp: " + comment.getTimestamp().getTime() + ",\r\n" + //
                       "        }\r\n" + //
                       "    }\r\n" + //
                       "})";

        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    };
    
}