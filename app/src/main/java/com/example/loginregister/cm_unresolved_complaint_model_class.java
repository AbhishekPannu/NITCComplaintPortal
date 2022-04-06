package com.example.loginregister;

import android.widget.RatingBar;

public class cm_unresolved_complaint_model_class {
    private String building,roomNumber,complaintCategory,complaintDescription,status,complainantEmail,statusFlag, complaintID;

    public String getBuilding() {
        return building;
    }

    public String getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(String complaintID) {
        this.complaintID = complaintID;
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


    public String getComplainantEmail() {
        return complainantEmail;
    }

    public void setComplainantEmail(String complainantEmail) {
        this.complainantEmail = complainantEmail;
    }

//
//    public user_complaint_model_class(String building, String roomNumber, String complaintCategory, String complaintDescription,String status) {
//        this.building = building;
//        this.roomNumber = roomNumber;
//        this.complaintCategory = complaintCategory;
//        this.complaintDescription = complaintDescription;
    //    this.status=status;
//    }

    public cm_unresolved_complaint_model_class() {
    }
}
