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

public class ResetPassword extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-8bf9e-default-rtdb.firebaseio.com/");
    Button buttonResetPassword;
    EditText editTextOldPassword,editTextNewPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        getSupportActionBar().hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (Build.VERSION.SDK_INT >= 21) {
//            Toast.makeText(this, "Android Versiono is above 21!!", Toast.LENGTH_SHORT).show();
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }



        buttonResetPassword=findViewById(R.id.button23);
        editTextOldPassword=findViewById(R.id.editTextTextEmailAddress11);
        editTextNewPassword=findViewById(R.id.editTextTextEmailAddress13);
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
        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword=editTextOldPassword.getText().toString();
                String newPassword=editTextNewPassword.getText().toString();
                databaseReference.child(optionAgain).child(Login.emailGlobal.substring(1)).child("password").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String getPassword=snapshot.getValue(String.class);
                        if(getPassword.equals(oldPassword)){
                            databaseReference.child(optionAgain).child(Login.emailGlobal.substring(1)).child("password").setValue(newPassword);
                            Toast.makeText(ResetPassword.this, "Password Updated Successfully!!", Toast.LENGTH_SHORT).show();
                            //return ;
                        }
                        else{
                            Toast.makeText(ResetPassword.this, "Old password is wrong!!", Toast.LENGTH_SHORT).show();
                            //return ;
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