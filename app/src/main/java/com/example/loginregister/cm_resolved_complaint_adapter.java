package com.example.loginregister;


import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class cm_resolved_complaint_adapter extends FirebaseRecyclerAdapter<
        cm_resolved_complaint_model_class, cm_resolved_complaint_adapter.personsViewholder> {


    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-8bf9e-default-rtdb.firebaseio.com/");


    public cm_resolved_complaint_adapter(
            @NonNull FirebaseRecyclerOptions<cm_resolved_complaint_model_class> options)
    {
        super(options);
    }

    @Override
    protected void
    onBindViewHolder(@NonNull personsViewholder holder, int position, @NonNull cm_resolved_complaint_model_class model)
    {
        String buildingAndRoomNumber=model.getBuilding()+" - "+model.getRoomNumber();
        holder.building.setText(buildingAndRoomNumber);

        holder.category.setText(model.getComplaintCategory());

        holder.email.setText(model.getComplainantEmail()+"@nitc.ac.in");

        holder.description.setText(model.getComplaintDescription());

        if(model.getStatus().equals("spam")){
            holder.ratingBar.setRating(Float.parseFloat("0"));
        }
        else if(model.getRating().equals("")){
            holder.ratingBar.setRating(Float.parseFloat("0"));
        }
        else {
            holder.ratingBar.setRating(Float.parseFloat(model.getRating()));
        }
        String currentStatus=model.getStatus();
        holder.buttonStatus.setText(currentStatus);

        if(currentStatus.equals("registered")){
            holder.buttonStatus.setTextColor(Color.rgb(0,28,85));
        }
        else if(currentStatus.equals("pending")){
            holder.buttonStatus.setTextColor(Color.rgb(255,215,0));
        }
        else if(currentStatus.equals("resolved")){
            holder.buttonStatus.setTextColor(Color.parseColor("green"));
        }
        else if(currentStatus.equals("spam")){
            holder.buttonStatus.setTextColor(Color.parseColor("red"));
        }


    }

    @NonNull
    @Override
    public personsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cm_resolved_complaint_format, parent, false);
        return new cm_resolved_complaint_adapter.personsViewholder(view);
    }

    class personsViewholder
            extends RecyclerView.ViewHolder {
        //TextView firstname, lastname, age;
        TextView building,roomNumber,category,description,email;
        Button buttonStatus;

        RatingBar ratingBar;
        public personsViewholder(@NonNull View itemView) {
            super(itemView);
            building = itemView.findViewById(R.id.building);
            category = itemView.findViewById(R.id.complaintCategory);
            description = itemView.findViewById(R.id.complaintDescription);
            ratingBar = itemView.findViewById(R.id.rating);
            buttonStatus = itemView.findViewById(R.id.status);
            email=itemView.findViewById(R.id.complainantEmail);


        }

    }
}
