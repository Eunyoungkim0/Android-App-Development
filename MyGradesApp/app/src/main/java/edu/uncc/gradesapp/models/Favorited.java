package edu.uncc.gradesapp.models;

import java.io.Serializable;

public class Favorited implements Serializable {

    String userId;

    public Favorited() {

    }

    public Favorited(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Favorited{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
