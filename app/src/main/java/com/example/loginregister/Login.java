package com.example.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.loginregister.MainActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Login extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-8bf9e-default-rtdb.firebaseio.com/");
    Button buttonlogin,buttonForgotPassword;
    Switch witch;
    EditText email,password;

    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_EMAIL="mypref";
    private static final String KEY_EMAIL="email";
    private static final String KEY_USERTYPE="type";

    public static String emailGlobal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (Build.VERSION.SDK_INT >= 21) {
//            Toast.makeText(this, "Android Versiono is above 21!!", Toast.LENGTH_SHORT).show();
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }


        buttonlogin=findViewById(R.id.button13);
        email=findViewById(R.id.editTextTextEmailAddress);
        password=findViewById(R.id.editTextTextPassword);
        buttonForgotPassword=findViewById(R.id.button24);
        witch = findViewById(R.id.switch1);

        sharedPreferences=getSharedPreferences(SHARED_PREF_EMAIL,MODE_PRIVATE);
        String shared_email=sharedPreferences.getString(KEY_EMAIL,null);
        String shared_type=sharedPreferences.getString(KEY_USERTYPE,null);
        if(shared_email!=null){
            if(shared_type.equals("users")){
                Login.emailGlobal="3"+shared_email;
                Intent intent = new Intent(Login.this, UserDashboard.class);
                startActivity(intent);
            }
            else if(shared_type.equals("SuperAdmin")){

                Login.emailGlobal="1"+shared_email;
                Intent intent = new Intent(Login.this, AdminDashboard.class);
                startActivity(intent);
            }
            else{
                Login.emailGlobal="2"+shared_email;
                Intent intent = new Intent(Login.this, ComplaintManagerDashboard.class);
                startActivity(intent);
            }
        }


        buttonlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_EMAIL,email.getText().toString());
                if(witch.isChecked()){
                    editor.putString(KEY_USERTYPE,"users");
                }
                else if(email.getText().toString().equals("root@nitc.ac.in")){
                    editor.putString(KEY_USERTYPE,"SuperAdmin");
                }
                else{
                    editor.putString(KEY_USERTYPE, "admins");
                }
                editor.apply();




                String emailCheckAgain=email.getText().toString();
                String passwordCheckAgain=password.getText().toString();
                if(emailCheckAgain.length()<12 ){
                    Toast.makeText(Login.this, "Invalid Email ID!!", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(passwordCheckAgain.length()<1){
                    Toast.makeText(Login.this, "Password Field Empty!!", Toast.LENGTH_SHORT).show();
                }

                String regex = "^[A-Za-z0-9+_.-]+@(nitc.ac.in+)$";
                Pattern pattern = Pattern.compile(regex);
                String email1=emailCheckAgain;
                Matcher matcher = pattern.matcher(email1);
                if(!matcher.matches()){
                    Toast.makeText(Login.this, "Enter a Valid NITC Email ID!!", Toast.LENGTH_SHORT).show();
                    return ;
                }



                String emailCheck=emailCheckAgain.substring(0,emailCheckAgain.length()-11);
                String passwordCheck=passwordCheckAgain;
                if(emailCheck.isEmpty() || passwordCheck.isEmpty()){
                    Toast.makeText(Login.this, "Fill all the Details", Toast.LENGTH_SHORT).show();
                }
                else if(emailCheck.equals("root") && passwordCheck.equals("root")){
                    if(!witch.isChecked()) {
                        emailGlobal="1"+emailCheck; // 1 is for admin  2 is for complaint manager and 3 is for users
                        MainActivity.abhiFlag=true;
                        Intent intent = new Intent(Login.this, AdminDashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(Login.this, "You are Admin!! Kids these days!!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    if(witch.isChecked()){
                        databaseReference.child("users").addListenerForSingleValueEvent((new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.hasChild(emailCheck)){
                                    String getPassword=snapshot.child(emailCheck).child("password").getValue(String.class);
                                    if(getPassword.equals(passwordCheck)){
                                        emailGlobal="3"+emailCheck;
                                        Toast.makeText(Login.this, "Login Successful!!", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(Login.this,UserDashboard.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(Login.this, "Password Incorrect!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    Toast.makeText(Login.this, "Email not Registered, Signup!!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        }));
                    }
                    else{
                        databaseReference.child("admins").addListenerForSingleValueEvent((new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot){
                                if(snapshot.hasChild(emailCheck)){
                                    String getPassword=snapshot.child(emailCheck).child("password").getValue(String.class);
                                    if(getPassword.equals(passwordCheck)){
                                        emailGlobal="2"+emailCheck;
                                        Toast.makeText(Login.this, "Login Successful!!", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(Login.this,ComplaintManagerDashboard.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(Login.this, "Password Incorrect!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    Toast.makeText(Login.this, "Email not Registered, Signup!!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        }));                    }
                }
//                Intent intent=new Intent(Login.this,UserDashboard.class);
//                startActivity(intent);
            }
        });
        buttonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,ForgotPassword.class);
                startActivity(intent);
            }
        });
    }
}