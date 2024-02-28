package it.unipi.lsmsdb.wefood.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StarRanking {
    
    private String username;
    private Double vote;

    public StarRanking(@JsonProperty("username") String username,
                       @JsonProperty("vote") Double vote) {
        this.username = username;
        this.vote = vote;
    }

    public String getUsername() {
        return username;
    }

    public void setUser(String username) {
        this.username = username;
    }

    public Double getVote() {
        return vote;
    }

    public void setVote(Double vote) {
        this.vote = vote;
    }

    public String toString(){
        return "Username: " + this.getUsername() + "\n" +
                "Vote: " + this.getVote() + "\n";
    }
}
