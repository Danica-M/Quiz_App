package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Tournament_Confirmation extends AppCompatActivity {


    String tournamentID, name, cat, dif, sDate, eDate;
    Button cancel, join;
    TextView tName, category, difficulty, start, end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_confirmation);
        Intent intent = getIntent();
        tournamentID = intent.getStringExtra("tourID");
        name = intent.getStringExtra("name");
        cat = intent.getStringExtra("cat");
        dif = intent.getStringExtra("dif");
        sDate = intent.getStringExtra("sDate");
        eDate = intent.getStringExtra("eDate");
        cancel = findViewById(R.id.can);
        join = findViewById(R.id.part);

        tName = findViewById(R.id.name);
        category = findViewById(R.id.cat);
        difficulty = findViewById(R.id.dif);
        start = findViewById(R.id.stDate);
        end = findViewById(R.id.enDate);

        tName.setText(name);
        category.setText(cat);
        difficulty.setText(dif);
        start.setText("Start: "+sDate);
        end.setText("End: "+eDate);


        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bIntent = new Intent(Tournament_Confirmation.this, User_Tournament_Activity.class);
                bIntent.putExtra("tourID", tournamentID);
                startActivity(bIntent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cIntent = new Intent(Tournament_Confirmation.this, MainActivity.class);
                startActivity(cIntent);
            }
        });

    }
}