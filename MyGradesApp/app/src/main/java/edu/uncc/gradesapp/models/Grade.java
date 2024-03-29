package edu.uncc.gradesapp.models;

import java.io.Serializable;

public class Grade implements Serializable {
    String gradeId, userId, semester, course, course_name, grade_letter;
    double credit, grade_number;

    public Grade() {

    }

    public Grade(String gradeId, String userId, String semester, String course, String course_name, String grade_letter, double credit, double grade_number) {
        this.gradeId = gradeId;
        this.userId = userId;
        this.semester = semester;
        this.course = course;
        this.course_name = course_name;
        this.grade_letter = grade_letter;
        this.credit = credit;
        this.grade_number = grade_number;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getGrade_letter() {
        return grade_letter;
    }

    public void setGrade_letter(String grade_letter) {
        this.grade_letter = grade_letter;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getGrade_number() {
        return grade_number;
    }

    public void setGrade_number(double grade_number) {
        this.grade_number = grade_number;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "gradeId='" + gradeId + '\'' +
                ", userId='" + userId + '\'' +
                ", semester='" + semester + '\'' +
                ", course='" + course + '\'' +
                ", course_name='" + course_name + '\'' +
                ", grade_letter='" + grade_letter + '\'' +
                ", credit=" + credit +
                ", grade_number=" + grade_number +
                '}';
    }
}
