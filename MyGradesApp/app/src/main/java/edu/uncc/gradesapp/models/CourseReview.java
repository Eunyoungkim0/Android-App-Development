package edu.uncc.gradesapp.models;

import java.io.Serializable;
import java.util.ArrayList;

public class CourseReview implements Serializable {

    String courseId, courseName, courseNumber;
    double hours;
    int reviewCount;
    boolean favorite;
    ArrayList<Review> reviews = new ArrayList<>();
    ArrayList<Favorited> favorited = new ArrayList<>();

    public CourseReview() {

    }

    public CourseReview(String courseId, String courseName, String courseNumber, double hours, int reviewCount, ArrayList<Review> reviews, ArrayList<Favorited> favorited) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseNumber = courseNumber;
        this.hours = hours;
        this.reviewCount = reviewCount;
        this.reviews = reviews;
        this.favorited = favorited;
        this.favorite = false;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public ArrayList<Favorited> getFavorited() {
        return favorited;
    }

    public void setFavorited(ArrayList<Favorited> favorited) {
        this.favorited = favorited;
    }

    public void addFavorited(Favorited favorited){
        this.favorited.add(favorited);
    }

    @Override
    public String toString() {
        return "CourseReview{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseNumber='" + courseNumber + '\'' +
                ", hours=" + hours +
                ", reviewCount=" + reviewCount +
                ", favorite=" + favorite +
                ", reviews=" + reviews +
                ", favorited=" + favorited +
                '}';
    }
}
