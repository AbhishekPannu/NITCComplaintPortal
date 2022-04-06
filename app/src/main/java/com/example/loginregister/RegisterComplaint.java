package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterComplaint extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-8bf9e-default-rtdb.firebaseio.com/");
    RadioGroup radioGroup;
    RadioButton radioHostel,radioCollege;
    Spinner spinner,spinnerComplaint;
    Button buttonSubmitComplaint;
    EditText editTextRoomNumber,editTextDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_complaint);
        getSupportActionBar().hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (Build.VERSION.SDK_INT >= 21) {
//            Toast.makeText(this, "Android Versiono is above 21!!", Toast.LENGTH_SHORT).show();
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }


        radioGroup=findViewById(R.id.radioGroup);
        radioHostel=findViewById(R.id.radioButton3);
        radioCollege=findViewById(R.id.radioButton4);
        spinner=findViewById(R.id.spinner);
        spinnerComplaint=findViewById(R.id.spinner2);
        buttonSubmitComplaint=findViewById(R.id.button19);
        editTextRoomNumber=findViewById(R.id.editTextNumber);
        editTextDescription=findViewById(R.id.editTextTextMultiLine);
        ArrayAdapter<CharSequence> spinnerHostel=ArrayAdapter.createFromResource(this,R.array.array_hostel, R.layout.spinner_layout);
        spinnerHostel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> spinnerBlock=ArrayAdapter.createFromResource(this,R.array.array_college, R.layout.spinner_layout);
        spinnerBlock.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> spinnerdefault1=ArrayAdapter.createFromResource(this,R.array.array_default, R.layout.spinner_layout);
        spinnerdefault1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerdefault1);
        ArrayAdapter<CharSequence> spinnerdefault2=ArrayAdapter.createFromResource(this,R.array.array_complaint, R.layout.spinner_layout);
        spinnerdefault2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerComplaint.setAdapter(spinnerdefault2);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(radioHostel.isChecked()){
                    spinner.setAdapter(spinnerHostel);
                }
                else if(radioCollege.isChecked()){
                    spinner.setAdapter(spinnerBlock);
                }
                else{
                    Toast.makeText(RegisterComplaint.this, "Choose either Hostel or College!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonSubmitComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userBuildingOrHostel=spinner.getSelectedItem().toString();
                String userRoomNumber=editTextRoomNumber.getText().toString();
                String userComplaintType=spinnerComplaint.getSelectedItem().toString();
                String userComplaintDescription=editTextDescription.getText().toString();


                makeDatabaseEntryFunction(userBuildingOrHostel,userRoomNumber,userComplaintType,userComplaintDescription);
            }
        });

    }

    public void makeDatabaseEntryFunction(String userBuildingOrHostel,String userRoomNumber,String userComplaintType,String userComplaintDescription){

        if(userBuildingOrHostel.equals("Select your Hostel") || userBuildingOrHostel.equals("Select your Block") || userBuildingOrHostel.equals("Choose between Hostel and College")){
            Toast.makeText(this, "Select Hostel or Building!!", Toast.LENGTH_SHORT).show();
            return ;
        }
        if(userComplaintType.equals("Select Complaint Category")){
            Toast.makeText(this, "Select Complaint Category!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(userRoomNumber.equals("")){
            Toast.makeText(this, "Enter Room Number!!", Toast.LENGTH_SHORT).show();
            return ;
        }
        if(userComplaintDescription.equals("")){
            Toast.makeText(this, "Enter Complaint Description!!", Toast.LENGTH_SHORT).show();
            return ;
        }
        if(userComplaintDescription.length()>65){
            Toast.makeText(this, "Lord Shakespeare, Please Refrain from writing paragraph!!", Toast.LENGTH_LONG).show();
            return ;
        }

        DatabaseReference quizRef = databaseReference.child("complaint");
        String key = quizRef.push().getKey();


        RegisterComplaintClass userComplaint=new RegisterComplaintClass(Login.emailGlobal.substring(1),userBuildingOrHostel,userRoomNumber,userComplaintType,userComplaintDescription,"0","registered","registeredPending",key);
        databaseReference.child("complaint").child(key).setValue(userComplaint);
        Toast.makeText(this, "Complaint Registered!!", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(RegisterComplaint.this,UserDashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
    }

}