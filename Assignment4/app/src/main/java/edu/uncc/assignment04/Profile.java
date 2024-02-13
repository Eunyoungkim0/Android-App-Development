package edu.uncc.assignment04;

import java.io.Serializable;

public class Profile extends Identification implements Serializable {
    String education, maritalStatus, livingStatus, income;

    public Profile(String name, String email, String role, String education, String maritalStatus, String livingStatus, String income) {
        super(name, email, role);
        this.education = education;
        this.maritalStatus = maritalStatus;
        this.livingStatus = livingStatus;
        this.income = income;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getLivingStatus() {
        return livingStatus;
    }

    public void setLivingStatus(String livingStatus) {
        this.livingStatus = livingStatus;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "education='" + education + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", livingStatus='" + livingStatus + '\'' +
                ", income='" + income + '\'' +
                '}';
    }
}
