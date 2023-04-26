package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.quizapp.fragments.Fragment_past;
import com.example.quizapp.fragments.Fragment_upcoming;
import com.example.quizapp.fragments.fragment_ongoing;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements BottomNavigationView .OnNavigationItemSelectedListener {

    Fragment fragment;
    FloatingActionButton createButton;
    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createButton = findViewById(R.id.createButton);
        bottomNav = findViewById(R.id.bottomAppBar);
        bottomNav.setOnNavigationItemSelectedListener(this);
        bottomNav.setSelectedItemId(R.id.ongoing);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Admin_create_tournament.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ongoing:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, new fragment_ongoing())
                        .commit();
                return true;

            case R.id.upcoming:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, new Fragment_upcoming())
                        .commit();
                return true;

            case R.id.past:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, new Fragment_past())
                        .commit();
                return true;
            case R.id.logout:
                logoutUser();

        }
        return false;
    }


    private void logoutUser(){
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Logout Confirmation")
                .setMessage("Are you sure to exit the application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "logout", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //set what should happen when negative button is clicked
                        //Toast.makeText(getApplicationContext(),"Nothing Happened",Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

}