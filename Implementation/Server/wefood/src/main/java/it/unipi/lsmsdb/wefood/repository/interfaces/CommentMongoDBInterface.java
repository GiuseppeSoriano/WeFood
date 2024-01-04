package it.unipi.lsmsdb.wefood.repository.interfaces;

import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.model.Comment;

public interface CommentMongoDBInterface {

    boolean commentPost(Comment comment, PostDTO postDTO);

    boolean updateComment(Comment comment, PostDTO postDTO);

    boolean deleteComment(Comment comment, PostDTO postDTO);

}
