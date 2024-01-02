package it.unipi.lsmsdb.wefood.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Post {

    private String username;
    private String description;
    private Date timestamp;
    private List<Comment> comments;
    private List<StarRanking> starRankings;
    private Recipe recipe;

    public Post(String username, String description, Date timestamp, Recipe recipe) {
        this.username = username;
        this.description = description;
        this.timestamp = timestamp;
        this.comments = new ArrayList<Comment>();
        this.starRankings = new ArrayList<StarRanking>();
        this.recipe = recipe;
    }

    public String getUserName() {
        return this.username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void deleteComment(Comment comment){
        this.comments.remove(comment);
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
    }

    public void deleteStarRanking(StarRanking starRanking){
        this.starRankings.remove(starRanking);
    }

    public void addStarRanking(StarRanking starRanking){
        this.starRankings.add(starRanking);
    }

}
