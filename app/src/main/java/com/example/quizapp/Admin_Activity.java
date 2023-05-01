package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.quizapp.models.Controller;
import com.example.quizapp.models.Tournament;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admin_Activity extends AppCompatActivity {

    RecyclerView adminRecycler;

    Adapter adapter;
    Controller controller;

    FloatingActionButton createButton;
    ArrayList<Tournament> allTournaments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        controller = new Controller();
        allTournaments = new ArrayList<>();
        createButton = findViewById(R.id.create);
        getAllTournament();

        adminRecycler = findViewById(R.id.adminRecycler);
        adminRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new Adapter(getApplicationContext(), allTournaments);
        adminRecycler.setAdapter(adapter);


        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Activity.this, Admin_create_tournament.class);
                startActivity(intent);
            }
        });

    }

    public void getAllTournament(){
        DatabaseReference tourRef = controller.getReference().child("tournaments");
        Query query = tourRef.orderByChild("startDate");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot tourItems: snapshot.getChildren()){
                    Tournament tournament = tourItems.getValue(Tournament.class);
                    if(tournament != null){
                        allTournaments.add(tournament);
                    }
                }
                Log.d("TAG", "size: "+allTournaments.size());
                if(allTournaments.size()==0){Toast.makeText(Admin_Activity.this, "No past tournament", Toast.LENGTH_LONG).show();}

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error Occurred: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}