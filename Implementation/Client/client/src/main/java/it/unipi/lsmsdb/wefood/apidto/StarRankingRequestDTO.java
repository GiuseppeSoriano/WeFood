package it.unipi.lsmsdb.wefood.apidto;

import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.model.StarRanking;

public class StarRankingRequestDTO {
    private RegisteredUser user;
    private StarRanking starRanking;
    private PostDTO postDTO;

    public StarRankingRequestDTO(RegisteredUser user, StarRanking starRanking, PostDTO postDTO) {
        this.user = user;
        this.starRanking = starRanking;
        this.postDTO = postDTO;
    }

    public RegisteredUser getUser() {
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user = user;
    }

    public StarRanking getStarRanking() {
        return starRanking;
    }

    public void setStarRanking(StarRanking starRanking) {
        this.starRanking = starRanking;
    }

    public PostDTO getPostDTO() {
        return postDTO;
    }

    public void setPostDTO(PostDTO postDTO) {
        this.postDTO = postDTO;
    }
}
