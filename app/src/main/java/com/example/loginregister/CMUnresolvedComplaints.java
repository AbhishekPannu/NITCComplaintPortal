package com.example.loginregister;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class CMUnresolvedComplaints extends AppCompatActivity {

    private RecyclerView recyclerView;
    cm_unresolved_complaint_adapter adapter;
    DatabaseReference mbase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmunresolved_complaints);
        getSupportActionBar().hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (Build.VERSION.SDK_INT >= 21) {
//            Toast.makeText(this, "Android Versiono is above 21!!", Toast.LENGTH_SHORT).show();
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        String emailForQuery=Login.emailGlobal.substring(1);
        Query query=FirebaseDatabase.getInstance().getReference("complaint").orderByChild("statusFlag").equalTo("registeredPending");
        mbase = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("complaint");



        recyclerView = findViewById(R.id.recycler1);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseRecyclerOptions<cm_unresolved_complaint_model_class> options
                = new FirebaseRecyclerOptions.Builder<cm_unresolved_complaint_model_class>().setLifecycleOwner(this)
                .setQuery(query, cm_unresolved_complaint_model_class.class)
                .build();
        adapter = new cm_unresolved_complaint_adapter(options);
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
