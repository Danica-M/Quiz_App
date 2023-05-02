package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.models.Controller;
import com.example.quizapp.models.Question;
import com.example.quizapp.models.Tournament;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class User_Tournament_Activity extends AppCompatActivity {

    String tournamentID;
    List<Question> questionList;
    int counter;

    Question ques;
    TextView question, tName;
    Button submitBtn;
    RadioGroup userAnswer;
    RadioButton op1, op2, op3, op4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_tournament);
        Intent intent = getIntent();
        tournamentID = intent.getStringExtra("tourID");

        getQuestions();
        counter = 0;
        tName = findViewById(R.id.tName);
        question = findViewById(R.id.question);
        submitBtn = findViewById(R.id.submitBtn);
        userAnswer = findViewById(R.id.userAnswer);
        op1 = findViewById(R.id.rb1);
        op2 = findViewById(R.id.rb2);
        op3 = findViewById(R.id.rb3);
        op4 = findViewById(R.id.rb4);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioButtonId = userAnswer.getCheckedRadioButtonId();
                RadioButton selected = findViewById(radioButtonId);
                if(selected!=null){
                    if(submitBtn.getText().equals("Submit")){
                        check(selected);
                        submitBtn.setText("Next");
                        counter++;
                    }else{
                        loadQuestions(counter);
                        submitBtn.setText("Submit");
                    }
                }else{
                    Toast.makeText(User_Tournament_Activity.this, "Please select your answer",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    public void getQuestions(){
        DatabaseReference tourRef = Controller.getReference().child("tournaments");
        tourRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot tourItem: snapshot.getChildren()){
                    Tournament tournament = tourItem.getValue(Tournament.class);
                    if(tournament!=null && tournament.getTournamentID().equals(tournamentID)){
                        questionList = tournament.getQuestions();
                        tName.setText(tournament.getName());
                    }
                }

                loadQuestions(counter);
                Log.d("TAG", "question size: "+questionList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void loadQuestions(int counter){
        userAnswer.clearCheck();
        op1.setBackgroundResource(R.drawable.quiz_button);
        op2.setBackgroundResource(R.drawable.quiz_button);
        op3.setBackgroundResource(R.drawable.quiz_button);
        op4.setBackgroundResource(R.drawable.quiz_button);

        ques = questionList.get(counter);
        question.setText(ques.getQuestion());
        List<String> all = ques.getAllAnswers();
        op1.setText(all.get(0));
        op2.setText(all.get(1));
        op3.setText(all.get(2));
        op4.setText(all.get(3));

    }

    public void check(RadioButton selected){
        Log.d("TAG", "s: "+selected.getText());
        Log.d("TAG", "q: "+ques.getQuestion());
        Log.d("TAG", "a: "+ques.getCorrectAnswer());
        if(selected.getText().equals(ques.getCorrectAnswer())){
            selected.setBackgroundResource(R.drawable.correct_option);
        }else{
            selected.setBackgroundResource(R.drawable.incorrect_option);
            for (int i = 0; i < userAnswer.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) userAnswer.getChildAt(i);
                if(radioButton.getText().equals(ques.getCorrectAnswer())) {
                    radioButton.setBackgroundResource(R.drawable.correct_option);

                }

            }
        }


    }

}