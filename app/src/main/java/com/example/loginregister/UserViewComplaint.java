package com.example.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserViewComplaint extends AppCompatActivity {

    private RecyclerView recyclerView;
    user_view_complaint_adapter_class adapter;
    DatabaseReference mbase;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_EMAIL="mypref";
    private static final String KEY_EMAIL="email";
    private static final String KEY_USERTYPE="type";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_complaint);
        getSupportActionBar().hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);



        if (Build.VERSION.SDK_INT >= 21) {
//            Toast.makeText(this, "Android Versiono is above 21!!", Toast.LENGTH_SHORT).show();
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        sharedPreferences=getSharedPreferences(SHARED_PREF_EMAIL,MODE_PRIVATE);
        String shared_email=sharedPreferences.getString(KEY_EMAIL,null);
        String shared_type=sharedPreferences.getString(KEY_USERTYPE,null);

        Login.emailGlobal="1"+shared_email.substring(0,shared_email.length()-11);


        String emailForQuery=Login.emailGlobal.substring(1);
        Query query=FirebaseDatabase.getInstance().getReference("complaint").orderByChild("complainantEmail").equalTo(Login.emailGlobal.substring(1));
        mbase = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("complaint");




        recyclerView = findViewById(R.id.recycler1);


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        FirebaseRecyclerOptions<user_complaint_model_class> options
                = new FirebaseRecyclerOptions.Builder<user_complaint_model_class>().setLifecycleOwner(this)
                .setQuery(query, user_complaint_model_class.class)
                .build();
        adapter = new user_view_complaint_adapter_class(options);
        recyclerView.setAdapter(adapter);
    }

    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }
}
