package com.example.quizapp.models;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private FirebaseDatabase firebaseDatabase;
    private static DatabaseReference reference;

    public Controller() {
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.reference = firebaseDatabase.getReference();
    }


    public static String getApiCategoryLookup() {
        String apiCategoryLookup = "https://opentdb.com/api_category.php";
        return apiCategoryLookup;
    }

    public static String getApiUrl() {
        String apiUrl = "https://opentdb.com/api.php?amount=10";
        return apiUrl;
    }

    public static DatabaseReference getReference() {
        return reference;
    }

    public Tournament addTournament(String tName, String tCategory, String tDifficulty, String tStart, String tEnd, String tStatus, List<Question> questionList){
        try{
            String id = reference.push().getKey();
            Tournament tournament = new Tournament(id, tName, tCategory, tDifficulty, tStart, tEnd, tStatus,0);
            tournament.setQuestions(questionList);
            reference.child("tournaments").child(id).setValue(tournament);
            return tournament;
        }catch(Exception e){
            Log.d("TAG", "error: "+e.getMessage());
            return null;
        }

    }

}
