package com.example.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
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
import com.google.firebase.database.ValueEventListener;import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupComplaintManager extends AppCompatActivity {
    EditText name,email,password;
    Button buttonsignup;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-8bf9e-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_complaint_manager);
        getSupportActionBar().hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (Build.VERSION.SDK_INT >= 21) {
//            Toast.makeText(this, "Android Versiono is above 21!!", Toast.LENGTH_SHORT).show();
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }


        name=findViewById(R.id.editTextTextPersonName2);
        email=findViewById(R.id.editTextTextEmailAddress2);
        password=findViewById(R.id.editTextTextPassword2);
        buttonsignup=findViewById(R.id.button13);
        buttonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });
    }
    private void sendData(){
        String nameSender=name.getText().toString();
        String emailSenderAgain=email.getText().toString();
        String passwordSender=password.getText().toString();
        String regex = "^[A-Za-z0-9+_.-]+@(nitc.ac.in+)$";
        Pattern pattern = Pattern.compile(regex);
        String email=emailSenderAgain;
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()){
            Toast.makeText(this, "Enter a Correct NITC Email!!", Toast.LENGTH_SHORT).show();
            return ;
        }
        String emailSender=emailSenderAgain.substring(0,emailSenderAgain.length()-11);
        databaseReference.child("admins").child(emailSender).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Toast.makeText(SignupComplaintManager.this, "Complaint Manager Already Exists!! Kids these days!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("admins").child(emailSender).child("name").setValue(nameSender);
                    databaseReference.child("admins").child(emailSender).child("email").setValue(emailSender);
                    databaseReference.child("admins").child(emailSender).child("password").setValue(passwordSender);
                    databaseReference.child("admins").child(emailSender).child("security_answer").setValue("");
                    databaseReference.child("admins").child(emailSender).child("mobile").setValue("");
                    Toast.makeText(SignupComplaintManager.this, "Complaint Manager Added Successfully!!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignupComplaintManager.this,AdminDashboard.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}