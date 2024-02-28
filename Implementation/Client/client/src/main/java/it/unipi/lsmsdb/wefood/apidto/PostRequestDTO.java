package it.unipi.lsmsdb.wefood.apidto;

import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.model.Post;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;

public class PostRequestDTO {
    Post post;
    PostDTO postDTO;
    RegisteredUser user;

    public PostRequestDTO(Post post, PostDTO postDTO, RegisteredUser user) {
        this.post = post;
        this.postDTO = postDTO;
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public PostDTO getPostDTO() {
        return postDTO;
    }

    public void setPostDTO(PostDTO postDTO) {
        this.postDTO = postDTO;
    }

    public RegisteredUser getUser() {
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user = user;
    }
}
