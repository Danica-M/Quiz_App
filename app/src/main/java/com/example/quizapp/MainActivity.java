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
import com.example.quizapp.models.Controller;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements BottomNavigationView .OnNavigationItemSelectedListener {

    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomAppBar);
        bottomNav.setOnNavigationItemSelectedListener(this);
        bottomNav.setSelectedItemId(R.id.ongoing);


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
        new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                .setTitle("Exit Confirmation")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent nlIntent = new Intent(MainActivity.this, Login.class);
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

}