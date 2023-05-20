package com.example.quizapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizapp.models.Controller;
import com.example.quizapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Login extends AppCompatActivity {

    EditText l_email, l_pass;
    Button signIn, signUp;
    FirebaseAuth mAuth;
    Controller controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        l_email = findViewById(R.id.email);
        l_pass = findViewById(R.id.pass1);
        signIn = findViewById(R.id.signInBtn);
        signUp = findViewById(R.id.goToSignUp);
        mAuth = FirebaseAuth.getInstance();

        controller = new Controller();
        Date today = new Date();
        Calendar tDate = Calendar.getInstance();
        tDate.setTime(today);
        tDate.set(Calendar.HOUR_OF_DAY, 0);
        tDate.set(Calendar.MINUTE, 0);
        tDate.set(Calendar.SECOND, 0);
        tDate.set(Calendar.MILLISECOND, 0);

        controller.updateTournamentStatus(tDate);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, pass;
                email = l_email.getText().toString();
                pass = l_pass.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(getBaseContext(), "Login form incomplete", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                getUser();
                            } else {
                                Toast.makeText(Login.this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
            }
        });
    }

    public void getUser() {
        String userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userID);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Controller.setCurrentUser(dataSnapshot.getValue(User.class));
                User currentUser = Controller.getCurrentUser();
                if (currentUser != null) {
                    if (currentUser.getUserType().equals("admin")) {
                        finishAffinity();
                        Intent intent = new Intent(Login.this, Admin_Activity.class);
                        startActivity(intent);
                        Toast.makeText(Login.this, "you are logged in successfully as admin!", Toast.LENGTH_LONG).show();
                    }else{
                        finishAffinity();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(Login.this, "you are logged in successfully", Toast.LENGTH_LONG).show();

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this, "Error Occurred: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}