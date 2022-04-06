package com.example.loginregister;

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
import android.widget.ImageView;

public class ComplaintManagerDashboard extends AppCompatActivity {

    ImageView imageViewlogout;
    Button buttonMyProfile,buttonResolvedComplaint,buttonUnresolvedComplaint;

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


        setContentView(R.layout.activity_complaint_manager_dashboard);
        imageViewlogout=findViewById(R.id.imageView5);
        buttonMyProfile=findViewById(R.id.button);
        buttonResolvedComplaint=findViewById(R.id.button5);
        buttonUnresolvedComplaint=findViewById(R.id.button14);


        sharedPreferences=getSharedPreferences(SHARED_PREF_EMAIL,MODE_PRIVATE);
        String shared_email=sharedPreferences.getString(KEY_EMAIL,null);
        String shared_type=sharedPreferences.getString(KEY_USERTYPE,null);


        buttonMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ComplaintManagerDashboard.this,myProfile.class);
                startActivity(intent);
            }
        });
        imageViewlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent=new Intent(ComplaintManagerDashboard.this,MainActivity.class);
                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
            }
        });
        buttonResolvedComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ComplaintManagerDashboard.this,CMResolvedComplaints.class);
                startActivity(intent);
            }
        });

        buttonUnresolvedComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ComplaintManagerDashboard.this,CMUnresolvedComplaints.class);
                startActivity(intent);
            }
        });

    }
}