package edu.charlotte.assignment15;

import java.io.Serializable;
import java.util.ArrayList;

public class Forum implements Serializable {
    private String title;
    private String createdBy;
    private String text;
    private String likesAndDate;
    private String id;
    private String userID;
    private int likes = 0;
    private ArrayList<String> likedBy = new ArrayList<>();
    public Forum() {
    }

    public Forum(String title, String createdBy, String text, String likesAndDate, String userID, int likes, ArrayList<String> likedBy) {
        this.title = title;
        this.createdBy = createdBy;
        this.text = text;
        this.likesAndDate = likesAndDate;
        this.userID = userID;
        this.likes = likes;
        this.likedBy = likedBy;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public ArrayList<String> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(ArrayList<String> likedBy) {
        this.likedBy = likedBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLikesAndDate() {
        return likesAndDate;
    }

    public void setLikesAndDate(String likesAndDate) {
        this.likesAndDate = likesAndDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Forum{" +
                "title='" + title + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", text='" + text + '\'' +
                ", likesAndDate='" + likesAndDate + '\'' +
                ", id='" + id + '\'' +
                ", userID='" + userID + '\'' +
                '}';
    }
}
