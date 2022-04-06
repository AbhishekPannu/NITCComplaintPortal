package com.example.loginregister;


import android.content.Context;
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

public class cm_unresolved_complaint_adapter extends FirebaseRecyclerAdapter<
        cm_unresolved_complaint_model_class, cm_unresolved_complaint_adapter.personsViewholder> {


    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-8bf9e-default-rtdb.firebaseio.com/");


    public cm_unresolved_complaint_adapter(
            @NonNull FirebaseRecyclerOptions<cm_unresolved_complaint_model_class> options)
    {
        super(options);
    }

    @Override
    protected void
    onBindViewHolder(@NonNull personsViewholder holder, int position, @NonNull cm_unresolved_complaint_model_class model)
    {
        String buildingAndRoomNumber=model.getBuilding()+" - "+model.getRoomNumber();
        holder.building.setText(buildingAndRoomNumber);

        holder.category.setText(model.getComplaintCategory());

        holder.email.setText(model.getComplainantEmail()+"@nitc.ac.in");

        holder.description.setText(model.getComplaintDescription());
        String currentStatus=model.getStatus();
        holder.buttonStatus.setText(currentStatus);

        String complaintID=model.getComplaintID();

        holder.buttonStatus.setTag(0);
        holder.buttonStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int status =(Integer)view.getTag();

                switch(status){
                    case 0:
                        holder.buttonStatus.setText("pending");
                        holder.buttonStatus.setTextColor(Color.rgb(255,215,0));
                        databaseReference.child("complaint").child(complaintID).child("status").setValue("pending");
                        databaseReference.child("complaint").child(complaintID).child("statusFlag").setValue("registeredPending");

                        view.setTag(1);
                        break;
                    case 1:
                        holder.buttonStatus.setText("resolved");
                        holder.buttonStatus.setTextColor(Color.parseColor("green"));
                        databaseReference.child("complaint").child(complaintID).child("status").setValue("resolved");
                        databaseReference.child("complaint").child(complaintID).child("statusFlag").setValue("resolvedSpam");

                        view.setTag(0);
                        break;
                }
            }
        });

        holder.buttonSpam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("complaint").child(complaintID).child("status").setValue("spam");
                databaseReference.child("complaint").child(complaintID).child("statusFlag").setValue("resolvedSpam");
            }
        });


        if(currentStatus.equals("registered")){
            holder.buttonStatus.setTextColor(Color.rgb(0,28,85));
            model.setStatusFlag("registeredPending");
        }
        else if(currentStatus.equals("pending")){
            holder.buttonStatus.setTextColor(Color.rgb(255,215,0));
            model.setStatusFlag("registeredPending");
        }
        else if(currentStatus.equals("resolved")){
            holder.buttonStatus.setTextColor(Color.parseColor("green"));
            model.setStatusFlag("resolvedSpam");
        }
        else if(currentStatus.equals("spam")){
            holder.buttonStatus.setTextColor(Color.parseColor("red"));
            model.setStatusFlag("resolvedSpam");
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
                .inflate(R.layout.cm_unresolved_complaint_format, parent, false);
        return new cm_unresolved_complaint_adapter.personsViewholder(view);
    }

    class personsViewholder
            extends RecyclerView.ViewHolder {
        //TextView firstname, lastname, age;
        TextView building,roomNumber,category,description,email;
        Button buttonStatus,buttonSpam;

        public personsViewholder(@NonNull View itemView) {
            super(itemView);
            building = itemView.findViewById(R.id.building);
            category = itemView.findViewById(R.id.complaintCategory);
            description = itemView.findViewById(R.id.complaintDescription);
            buttonStatus = itemView.findViewById(R.id.status);
            buttonSpam=itemView.findViewById(R.id.spam);
            email=itemView.findViewById(R.id.complainantEmail);
        }

    }
}
