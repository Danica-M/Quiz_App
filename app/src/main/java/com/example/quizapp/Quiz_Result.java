package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Quiz_Result extends AppCompatActivity {

    TextView result;
    String tourID, quizScore, quizLength;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        Intent intent = getIntent();
        tourID = intent.getStringExtra("tourID");
        quizScore = intent.getStringExtra("score");
        quizLength = intent.getStringExtra("quizLength");

        result = findViewById(R.id.result);

        result.setText(quizScore+ " / "+quizLength);
    }
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(Quiz_Result.this, MainActivity.class);
        startActivity(intent);
    }






}