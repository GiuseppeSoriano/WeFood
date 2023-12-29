package it.unipi.lsmsdb.wefood.model;
import java.util.Date;

public class Comment {
    private RegisteredUser user;
    private String text;
    private Date timestamp;

    public Comment(RegisteredUser user, String text, Date timestamp) {
        this.user = user;
        this.text = text;
        this.timestamp = timestamp;
    }

    public RegisteredUser getUser() {
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user = user;
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

}
