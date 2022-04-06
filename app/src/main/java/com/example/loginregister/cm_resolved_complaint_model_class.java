package com.example.loginregister;

import android.widget.RatingBar;

public class cm_resolved_complaint_model_class {
    private String building,roomNumber,complaintCategory,complaintDescription,rating,status,complainantEmail,complaintID;

    public String getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(String complaintID) {
        this.complaintID = complaintID;
    }

    public String getBuilding() {
        return building;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getComplaintCategory() {
        return complaintCategory;
    }

    public void setComplaintCategory(String complaintCategory) {
        this.complaintCategory = complaintCategory;
    }

    public String getComplaintDescription() {
        return complaintDescription;
    }

    public void setComplaintDescription(String complaintDescription) {
        this.complaintDescription = complaintDescription;
    }

    public String getRating() {
        return rating;
    }

    public String getComplainantEmail() {
        return complainantEmail;
    }

    public void setComplainantEmail(String complainantEmail) {
        this.complainantEmail = complainantEmail;
    }

    public void setRating_bar(String rating) {
        this.rating = rating;
    }
//
//    public user_complaint_model_class(String building, String roomNumber, String complaintCategory, String complaintDescription, String rating,String status) {
//        this.building = building;
//        this.roomNumber = roomNumber;
//        this.complaintCategory = complaintCategory;
//        this.complaintDescription = complaintDescription;
//        this.rating = rating;
    //    this.status=status;
//    }

    public cm_resolved_complaint_model_class() {
    }
}
