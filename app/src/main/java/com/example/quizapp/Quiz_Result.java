package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizapp.models.Controller;
import com.example.quizapp.models.TournamentResultRecord;


public class Quiz_Result extends AppCompatActivity {

    Controller controller;
    TextView result;
    String tourID, quizScore, quizLength;
    Button likeBtn, dislikeBtn;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        controller = new Controller();
        Intent intent = getIntent();
        tourID = intent.getStringExtra("tourID");
        quizScore = intent.getStringExtra("score");
        quizLength = intent.getStringExtra("quizLength");
        likeBtn = findViewById(R.id.likeBtn);
        dislikeBtn = findViewById(R.id.dislikeBtn);

        result = findViewById(R.id.result);

        result.setText(quizScore+ " / "+quizLength);

        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            TournamentResultRecord rec = new TournamentResultRecord(Controller.getCurrentUser().getUserID(), quizScore);
            controller.addTournamentParticipants(tourID, rec, 1);
                Intent intent1 = new Intent(Quiz_Result.this, MainActivity.class);
                startActivity(intent1);
            }
        });

        dislikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TournamentResultRecord rec = new TournamentResultRecord(Controller.getCurrentUser().getUserID(), quizScore);
                controller.addTournamentParticipants(tourID, rec, 0);
                Intent intent1 = new Intent(Quiz_Result.this, MainActivity.class);
                startActivity(intent1);
            }
        });


    }
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(Quiz_Result.this, MainActivity.class);
        startActivity(intent);
    }

}