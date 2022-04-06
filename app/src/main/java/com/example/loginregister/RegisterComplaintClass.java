package com.example.loginregister;

import java.util.Date;

public class RegisterComplaintClass {
    String complainantEmail;
    String building;
    String roomNumber;
    String complaintCategory;
    String complaintDescription;
    Date date;
    String rating;
    String status;
    String statusFlag;
    String complaintID;

    public RegisterComplaintClass(String complainantEmail, String building, String roomNumber, String complaintCategory, String complaintDescription,  String rating, String status,String statusFlag,String complaintID) {
        this.complainantEmail = complainantEmail;
        this.building = building;
        this.roomNumber = roomNumber;
        this.complaintCategory = complaintCategory;
        this.complaintDescription = complaintDescription;
        //this.date = date;
        this.rating = rating;
        this.status=status;
        this.statusFlag=statusFlag;
        this.complaintID=complaintID;
    }

    public String getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(String complaintID) {
        this.complaintID = complaintID;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(String statusFlag) {
        this.statusFlag = statusFlag;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setComplainantEmail(String complainantEmail) {
        this.complainantEmail = complainantEmail;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setComplaintCategory(String complaintCategory) {
        this.complaintCategory = complaintCategory;
    }

    public void setComplaintDescription(String complaintDescription) {
        this.complaintDescription = complaintDescription;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComplainantEmail() {
        return complainantEmail;
    }

    public String getBuilding() {
        return building;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getComplaintCategory() {
        return complaintCategory;
    }

    public String getComplaintDescription() {
        return complaintDescription;
    }

    public Date getDate() {
        return date;
    }

    public String getRating() {
        return rating;
    }
}
