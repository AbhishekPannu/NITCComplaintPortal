package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;import android.view.Window;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static String abhiEmail="";
    public static String abhiPassword="";
    public static boolean abhiFlag=false;
    Button buttonLogin,buttonSignup;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_EMAIL="mypref";
    private static final String KEY_EMAIL="email";
    private static final String KEY_USERTYPE="type";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (Build.VERSION.SDK_INT >= 21) {
//            Toast.makeText(this, "Android Versiono is above 21!!", Toast.LENGTH_SHORT).show();
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        buttonSignup=findViewById(R.id.button12);
        buttonLogin=findViewById(R.id.button11);


        sharedPreferences=getSharedPreferences(SHARED_PREF_EMAIL,MODE_PRIVATE);
        String shared_email=sharedPreferences.getString(KEY_EMAIL,null);
        String shared_type=sharedPreferences.getString(KEY_USERTYPE,null);
        if(shared_email!=null){
            if(shared_type.equals("users")){
                Login.emailGlobal="3"+shared_email;
                Intent intent = new Intent(MainActivity.this, UserDashboard.class);
                startActivity(intent);
            }
            else if(shared_type.equals("SuperAdmin")){
                Login.emailGlobal="1"+shared_email;
                Intent intent = new Intent(MainActivity.this, AdminDashboard.class);
                startActivity(intent);
            }
            else{
                Login.emailGlobal="2"+shared_email;
                Intent intent = new Intent(MainActivity.this, ComplaintManagerDashboard.class);
                startActivity(intent);
            }
        }





        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Signup.class);
                startActivity(intent);
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Login.class);
                startActivity(intent);
            }
        });
    }
}