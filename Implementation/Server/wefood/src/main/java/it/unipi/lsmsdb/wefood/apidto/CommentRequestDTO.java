package it.unipi.lsmsdb.wefood.apidto;

import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.model.Comment;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;

public class CommentRequestDTO {
    private RegisteredUser user;
    private Comment comment;
    private PostDTO postDTO;

    public CommentRequestDTO(RegisteredUser user, Comment comment, PostDTO postDTO) {
        this.user = user;
        this.comment = comment;
        this.postDTO = postDTO;
    }

    public RegisteredUser getUser() {
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public PostDTO getPostDTO() {
        return postDTO;
    }

    public void setPostDTO(PostDTO postDTO) {
        this.postDTO = postDTO;
    }
}
