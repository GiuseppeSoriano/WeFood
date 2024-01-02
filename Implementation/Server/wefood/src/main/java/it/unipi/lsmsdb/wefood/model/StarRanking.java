package it.unipi.lsmsdb.wefood.model;

public class StarRanking {
    
    private RegisteredUser user;
    private Double vote;

    public StarRanking(RegisteredUser user, Double vote) {
        this.user = user;
        this.vote = vote;
    }

    public RegisteredUser getUser() {
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user = user;
    }

    public Double getVote() {
        return vote;
    }

    public void setVote(Double vote) {
        this.vote = vote;
    }
}
