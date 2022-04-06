package com.example.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class myProfile extends AppCompatActivity {

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-8bf9e-default-rtdb.firebaseio.com/");
    TextView textViewName,textViewInitals,textViewMobile,textViewSecurityAnswer;
    Button buttonEmail,buttonPhone,buttonEditProfile,buttonResetPassword;

    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_EMAIL="mypref";
    private static final String KEY_EMAIL="email";
    private static final String KEY_USERTYPE="type";


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


        setContentView(R.layout.activity_my_profile);
        textViewName=findViewById(R.id.textView3);
        buttonEmail=findViewById(R.id.button16);
        textViewInitals=findViewById(R.id.textView5);
        buttonEditProfile=findViewById(R.id.button18);
        textViewMobile=findViewById(R.id.button17);
        buttonResetPassword=findViewById(R.id.button20);
        textViewSecurityAnswer=findViewById(R.id.button15);

        sharedPreferences=getSharedPreferences(SHARED_PREF_EMAIL,MODE_PRIVATE);
        String shared_email=sharedPreferences.getString(KEY_EMAIL,null);
        String shared_type=sharedPreferences.getString(KEY_USERTYPE,null);
        shared_email=shared_email.substring(0,shared_email.length()-11);
//        Toast.makeText(this, "shared type is "+shared_email+" "+shared_type, Toast.LENGTH_SHORT).show();
        String option="users";
        if(shared_type.equals("SuperAdmin")){
            Login.emailGlobal="1"+shared_email;
            option="SuperAdmin";
        }
        else if(shared_type.equals("admins")){
            Login.emailGlobal="2"+shared_email;
            option="admins";
        }
        else if(shared_type.equals("users")){
            Login.emailGlobal="3"+shared_email;
            option="users";
        }

        databaseReference.child(option).child(Login.emailGlobal.substring(1)).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nameofuser=snapshot.getValue(String.class);
                    textViewName.setText(nameofuser);
                    String newEmail=Login.emailGlobal.substring(1)+"@nitc.ac.in";
                    buttonEmail.setText(newEmail);
                    StringBuilder tempNameOfUser = new StringBuilder();
                    String[] splitNameInitial = nameofuser.split(" ");  // this code is for initals finding
                    for (String s : splitNameInitial) {
                        tempNameOfUser.append(s.charAt(0));
                    }
                    textViewInitals.setText(tempNameOfUser.toString().toUpperCase());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        databaseReference.child(option).child(Login.emailGlobal.substring(1)).child("mobile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String mobileofuser=snapshot.getValue(String.class);
                    textViewMobile.setText(mobileofuser);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child(option).child(Login.emailGlobal.substring(1)).child("security_answer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String securityanswerofuser=snapshot.getValue(String.class);
                    textViewSecurityAnswer.setText(securityanswerofuser);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myProfile.this, EditProfile.class);
                startActivity(intent);
            }
        });
        buttonEmail.setText(Login.emailGlobal.substring(1)+"@nitc.ac.in");
        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Login.emailGlobal.charAt(0)=='1'){
                    Toast.makeText(myProfile.this, "You  are Admin, Kids these Days!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(myProfile.this, ResetPassword.class);
                    startActivity(intent);
                }
            }
        });
    }
}