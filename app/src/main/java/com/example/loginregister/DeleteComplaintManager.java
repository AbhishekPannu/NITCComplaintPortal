package com.example.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteComplaintManager extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-8bf9e-default-rtdb.firebaseio.com/");
    EditText editTextDeleteEmail;
    Button buttonDeleteEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_complaint_manager);
        getSupportActionBar().hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (Build.VERSION.SDK_INT >= 21) {
//            Toast.makeText(this, "Android Versiono is above 21!!", Toast.LENGTH_SHORT).show();
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }


        editTextDeleteEmail=findViewById(R.id.editTextTextEmailAddress6);
        buttonDeleteEmail=findViewById(R.id.button4);
        buttonDeleteEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getComplaintManagerEmailAgain=editTextDeleteEmail.getText().toString();
                String regex = "^[A-Za-z0-9+_.-]+@(nitc.ac.in+)$";
                Pattern pattern = Pattern.compile(regex);
                String email=getComplaintManagerEmailAgain;
                Matcher matcher = pattern.matcher(email);
                if(!matcher.matches()){
                    Toast.makeText(DeleteComplaintManager.this, "Enter a Correct NITC Email!!", Toast.LENGTH_SHORT).show();
                    return ;
                }
                String getComplaintManagerEmail=getComplaintManagerEmailAgain.substring(0,getComplaintManagerEmailAgain.length()-11);
                if(getComplaintManagerEmail.equals("")){
                    Toast.makeText(DeleteComplaintManager.this, "Email Address Empty!! Kids these days!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("admins").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(getComplaintManagerEmail)){
                                databaseReference.child("admins").child(getComplaintManagerEmail).removeValue();
                                Toast.makeText(DeleteComplaintManager.this, "Complaint Manager Deleted Successfully!!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(DeleteComplaintManager.this, "Invalid Email Address!! Kids these days!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}