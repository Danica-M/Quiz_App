package com.example.quizapp.models;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Controller {

    private FirebaseDatabase firebaseDatabase;
    private static DatabaseReference reference;
    private static SimpleDateFormat sdf;
    public static String currentUser = "admin";


    public Controller() {
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.reference = firebaseDatabase.getReference();
        this.sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    public static SimpleDateFormat getSdf() {
        return sdf;
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
            String tournamentID = reference.push().getKey();
            Tournament tournament = new Tournament(tournamentID, tName, tCategory, tDifficulty, tStart, tEnd, tStatus,0);
            tournament.setQuestions(questionList);
            reference.child("tournaments").child(tournamentID).setValue(tournament);
            return tournament;
        }catch(Exception e){
            Log.d("TAG", "error: "+e.getMessage());
            return null;
        }

    }

    public void deleteTournament(String tournamentId, Context context) {
        DatabaseReference tournamentRef = FirebaseDatabase.getInstance().getReference("tournaments").child(tournamentId);
        tournamentRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Tournament deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to delete tournament", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updateTournament(String tournamentId, String newName, String newStartDate, String newEndDate,String status,Context context) {
        try{
            DatabaseReference tournamentRef = reference.child("tournaments").child(tournamentId);
            tournamentRef.child("name").setValue(newName);
            tournamentRef.child("startDate").setValue(newStartDate);
            tournamentRef.child("endDate").setValue(newEndDate);
            tournamentRef.child("status").setValue(status);
            Toast.makeText(context, "Tournament updated successfully", Toast.LENGTH_SHORT).show();

        }catch(Exception e){
            Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();
        }

    }

}
