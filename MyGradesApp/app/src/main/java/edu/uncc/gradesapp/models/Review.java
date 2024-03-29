package edu.uncc.gradesapp.models;
import com.google.firebase.Timestamp;
import java.io.Serializable;

public class Review implements Serializable {
    String reviewId, review, username, userId;
    Timestamp postdate;

    public Review(){

    }

    public Review(String reviewId, String review, String username, String userId, Timestamp postdate) {
        this.reviewId = reviewId;
        this.review = review;
        this.username = username;
        this.userId = userId;
        this.postdate = postdate;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getPostdate() {
        return postdate;
    }

    public void setPostdate(Timestamp postdate) {
        this.postdate = postdate;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId='" + reviewId + '\'' +
                ", review='" + review + '\'' +
                ", username='" + username + '\'' +
                ", userId='" + userId + '\'' +
                ", postdate=" + postdate +
                '}';
    }
}
