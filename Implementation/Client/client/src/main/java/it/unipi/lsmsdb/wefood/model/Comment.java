package it.unipi.lsmsdb.wefood.model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Comment {
    private String username;
    private String text;
    private Date timestamp;

    public Comment(@JsonProperty("username") String username,
                   @JsonProperty("text") String text,
                   @JsonProperty("timestamp") Date timestamp) {
        this.username = username;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUser(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String toString(){
        return "Username: " + this.getUsername() + "\n" +
                "Text: " + this.getText() + "\n" +
                "Timestamp: " + this.getTimestamp() + "\n";
    }
}
