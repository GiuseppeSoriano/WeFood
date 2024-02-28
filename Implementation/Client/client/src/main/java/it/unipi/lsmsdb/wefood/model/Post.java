package it.unipi.lsmsdb.wefood.model;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    private Double avgStarRanking;


    public Post(@JsonProperty("username") String username,
                @JsonProperty("description") String description,
                @JsonProperty("timestamp") Date timestamp,
                @JsonProperty("recipe") Recipe recipe) {
        this.username = username;
        this.description = description;
        this.timestamp = timestamp;
        this.comments = new ArrayList<Comment>();
        this.starRankings = new ArrayList<StarRanking>();
        this.recipe = recipe;
        this.avgStarRanking = -1.0;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
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

    public List<Comment> getComments() {
        return comments;
    }

    public List<StarRanking> getStarRankings() {
        return starRankings;
    }

    public Double getAvgStarRanking() {
        return avgStarRanking;
    }

    public void setAvgStarRanking(Double avgStarRanking) {
        this.avgStarRanking = avgStarRanking;
    }
    
}
