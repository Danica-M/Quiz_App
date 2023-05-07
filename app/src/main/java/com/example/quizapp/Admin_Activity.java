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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.quizapp.models.Controller;
import com.example.quizapp.models.Tournament;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Admin_Activity extends AppCompatActivity {

    RecyclerView adminRecycler;

    SearchView search;
    Adapter adapter;
    Controller controller;

    ImageButton searchBtn;
    FloatingActionButton createButton, exitButton;
    ArrayList<Tournament> allTournaments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        controller = new Controller();
        allTournaments = new ArrayList<>();
        createButton = findViewById(R.id.create);
        exitButton = findViewById(R.id.exitBtn);
        getAllTournament();
        search = findViewById(R.id.searchView);
        search.clearFocus();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return true;
            }
        });

        adminRecycler = findViewById(R.id.adminRecycler);
        adminRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new Adapter(getApplicationContext(), allTournaments);
        adminRecycler.setAdapter(adapter);

//        searchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String text = searchText.getText().toString();
//                if (TextUtils.isEmpty(text)) {
//                    // Reset the data to the original list
//                    adapter.resetList();
//                } else {
//                    // Filter the data based on the search text
//                    adapter.filterList(text);
//                }
//            }
//        });


        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Activity.this, Admin_create_tournament.class);
                startActivity(intent);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(Admin_Activity.this)
                        .setTitle("Exit Confirmation")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent nlIntent = new Intent(Admin_Activity.this, Login.class);
                                startActivity(nlIntent);
                                finishAffinity();
                                FirebaseAuth.getInstance().signOut();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
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

    public void filterList(String text){
        ArrayList<Tournament> filteredList = new ArrayList<>();
        for(Tournament item:allTournaments){
            if(item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        if(filteredList.isEmpty()){
            adapter.setFilteredList(filteredList);
            Toast.makeText(this, "No Tournament found with this name", Toast.LENGTH_SHORT).show();
        }else{

            adapter.setFilteredList(filteredList);
        }
    }

}