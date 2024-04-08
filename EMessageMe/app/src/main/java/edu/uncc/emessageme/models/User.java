package edu.uncc.emessageme.models;

import java.util.ArrayList;

public class User {
    String userId, userName;
    ArrayList<String> blocked = new ArrayList<>();
    ArrayList<String> blockedBy = new ArrayList<>();

    public User() {
    }

    public User(String userId, String userName, ArrayList<String> blocked, ArrayList<String> blockedBy) {
        this.userId = userId;
        this.userName = userName;
        this.blocked = blocked;
        this.blockedBy = blockedBy;
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

    public ArrayList<String> getBlocked() {
        return blocked;
    }

    public void setBlocked(ArrayList<String> blocked) {
        this.blocked = blocked;
    }

    public ArrayList<String> getBlockedBy() {
        return blockedBy;
    }

    public void setBlockedBy(ArrayList<String> blockedBy) {
        this.blockedBy = blockedBy;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
