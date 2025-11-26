package edu.charlotte.assignment15;

public class Comment {
    private String id;
    private String userId;
    private String userName;
    private String text;
    private String createdAt;

    public Comment() {}

    public Comment(String userId, String userName, String text, String createdAt) {
        this.userId = userId;
        this.userName = userName;
        this.text = text;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", text='" + text + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
