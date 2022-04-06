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
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-8bf9e-default-rtdb.firebaseio.com/");
    EditText editTextName,editTextMobile,editTextSecurityAnswer;
//    String nameofuser,mobileofuser,securityanswerofuser;
    Button buttonUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (Build.VERSION.SDK_INT >= 21) {
//            Toast.makeText(this, "Android Versiono is above 21!!", Toast.LENGTH_SHORT).show();
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }



        editTextName=findViewById(R.id.editTextTextEmailAddress3);
        editTextMobile=findViewById(R.id.editTextTextEmailAddress4);
        editTextSecurityAnswer=findViewById(R.id.editTextTextEmailAddress5);
        buttonUpdate=findViewById(R.id.button6);
        String option="users";
        if(Login.emailGlobal.charAt(0)=='1'){
            option="SuperAdmin";
        }
        else if(Login.emailGlobal.charAt(0)=='2'){
            option="admins";
        }
        else if(Login.emailGlobal.charAt(0)=='3'){
            option="users";
        }
        final String optionAgain=option;
        databaseReference.child(option).child(Login.emailGlobal.substring(1)).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nameofuser=snapshot.getValue(String.class);
                    editTextName.setText(nameofuser);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child(option).child(Login.emailGlobal.substring(1)).child("mobile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                if (snapshot.exists()) {
                    String mobileofuser = snapshot.getValue(String.class);
                    editTextMobile.setText(mobileofuser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child(option).child(Login.emailGlobal.substring(1)).child("security_answer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                if (snapshot.exists()) {
                    String securityanswerofuser = snapshot.getValue(String.class);
                    editTextSecurityAnswer.setText(securityanswerofuser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedName=editTextName.getText().toString();
                String updatedMobile=editTextMobile.getText().toString();
                String updatedSecurityAnswer=editTextSecurityAnswer.getText().toString();
                databaseReference.child(optionAgain).child(Login.emailGlobal.substring(1)).child("name").setValue(updatedName);
                databaseReference.child(optionAgain).child(Login.emailGlobal.substring(1)).child("mobile").setValue(updatedMobile);
                databaseReference.child(optionAgain).child(Login.emailGlobal.substring(1)).child("security_answer").setValue(updatedSecurityAnswer);
                Toast.makeText(EditProfile.this, "Successfully Updated!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}