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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.regex.Matcher;
import java.util.regex.Pattern;





public class Signup extends AppCompatActivity {
    EditText name,email,password,securityAnswer;
    Button buttonsignup;
    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-8bf9e-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (Build.VERSION.SDK_INT >= 21) {
//            Toast.makeText(this, "Android Versiono is above 21!!", Toast.LENGTH_SHORT).show();
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }


        setContentView(R.layout.activity_signup);
        name=findViewById(R.id.editTextTextPersonName2);
        email=findViewById(R.id.editTextTextEmailAddress2);
        password=findViewById(R.id.editTextTextPassword2);
        securityAnswer=findViewById(R.id.editTextTextAnswer);
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
        String emailSender=email.getText().toString();
        String passwordSender=password.getText().toString();
        String securityAnswerSender=securityAnswer.getText().toString();


        if(emailSender.length()<12 || passwordSender.length()<1 || securityAnswerSender.length()<1 || nameSender.length()<1){
            Toast.makeText(this, "Details Should not be Empty!!", Toast.LENGTH_SHORT).show();
            return ;
        }


        String regex = "^[A-Za-z0-9+_.-]+@(nitc.ac.in+)$";
        Pattern pattern = Pattern.compile(regex);
        String email=emailSender;
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()){
            Toast.makeText(this, "Enter a Correct NITC Email!!", Toast.LENGTH_SHORT).show();
            return ;
        }

        int lengthOfEmail=email.length();
        final String emailSenderAgain=emailSender.substring(0,lengthOfEmail-11);


        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(emailSenderAgain).exists()){
                    Toast.makeText(Signup.this, "Email Already Exists!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("users").child(emailSenderAgain).child("name").setValue(nameSender);
                    databaseReference.child("users").child(emailSenderAgain).child("email").setValue(emailSenderAgain);
                    databaseReference.child("users").child(emailSenderAgain).child("password").setValue(passwordSender);
                    databaseReference.child("users").child(emailSenderAgain).child("mobile").setValue("");
                    databaseReference.child("users").child(emailSenderAgain).child("security_answer").setValue(securityAnswerSender);
                    Toast.makeText(Signup.this, "Sign Up Successful!!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Signup.this,Login.class);
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