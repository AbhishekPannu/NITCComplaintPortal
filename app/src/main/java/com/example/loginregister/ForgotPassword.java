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
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-8bf9e-default-rtdb.firebaseio.com/");
    Button buttonAuthenticate,buttonUpdatePassword;
    EditText editTextSecurityAnswer,editTextNewPassword,editTextEmail;
    Switch witch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (Build.VERSION.SDK_INT >= 21) {
//            Toast.makeText(this, "Android Versiono is above 21!!", Toast.LENGTH_SHORT).show();
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }



        editTextEmail=findViewById(R.id.editTextTextEmailAddress9);
        editTextSecurityAnswer=findViewById(R.id.editTextTextEmailAddress7);
        editTextNewPassword=findViewById(R.id.editTextTextEmailAddress8);
        buttonAuthenticate=findViewById(R.id.button21);
        buttonUpdatePassword=findViewById(R.id.button22);
        witch=findViewById(R.id.switch2);
        String option="users";
        if(!witch.isChecked()){
            option="admins";
        }
        final String optionAgain=option;


        buttonAuthenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String forgotEmailAgain=editTextEmail.getText().toString();

                String regex = "^[A-Za-z0-9+_.-]+@(nitc.ac.in+)$";
                Pattern pattern = Pattern.compile(regex);
                String email=forgotEmailAgain;
                Matcher matcher = pattern.matcher(email);
                if(!matcher.matches()){
                    Toast.makeText(ForgotPassword.this, "Enter a Correct NITC Email!!", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if(forgotEmailAgain.length()<12){
                    Toast.makeText(ForgotPassword.this, "Enter a Valid NITC Email ID!!", Toast.LENGTH_SHORT).show();
                    return ;
                }
                String forgotEmail=forgotEmailAgain.substring(0,forgotEmailAgain.length()-11);
                databaseReference.child(optionAgain).child(forgotEmail).child("security_answer").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String yourSecurityAnswer=editTextSecurityAnswer.getText().toString();
                        String databaseSecurityAnswer=snapshot.getValue(String.class);
                        if(yourSecurityAnswer.equals(databaseSecurityAnswer)){
                            editTextEmail.setFocusable(false);
                            editTextSecurityAnswer.setFocusable(false);
                            Toast.makeText(ForgotPassword.this, "Authentication Successful !! Enter new Password!!", Toast.LENGTH_SHORT).show();
                            buttonUpdatePassword.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String yourNewPassword=editTextNewPassword.getText().toString();
                                    databaseReference.child(optionAgain).child(forgotEmail).child("password").setValue(yourNewPassword);
                                    Toast.makeText(ForgotPassword.this, "Password Updated Successfully!!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            Toast.makeText(ForgotPassword.this, "Security Answer Incorrect!! Contact Admin", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}