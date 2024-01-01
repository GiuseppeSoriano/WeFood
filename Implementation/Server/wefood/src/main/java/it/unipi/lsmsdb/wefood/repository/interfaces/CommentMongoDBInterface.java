package it.unipi.lsmsdb.wefood.repository.interfaces;

import it.unipi.lsmsdb.wefood.model.Comment;
import it.unipi.lsmsdb.wefood.model.Post;

public interface CommentMongoDBInterface {

    boolean commentPost(Comment comment, Post post);

    boolean updateComment(Comment comment);

    boolean deleteComment(Comment comment, Post post);

}
