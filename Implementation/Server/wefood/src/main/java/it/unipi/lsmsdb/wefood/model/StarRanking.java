package it.unipi.lsmsdb.wefood.model;

public class StarRanking {
    
    private String username;
    private Double vote;

    public StarRanking(String username, Double vote) {
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
