package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.quizapp.models.Controller;
import com.example.quizapp.models.Tournament;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class Admin_update_tournament extends AppCompatActivity {

    FloatingActionButton delete;
    Controller controller;
    EditText tourName, startDate, endDate;
    String tournamentID;
    Button update, cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_update_tournament);
        controller = new Controller();
        tourName = findViewById(R.id.tourName);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        delete = findViewById(R.id.deleteBtn);
        update = findViewById(R.id.updateButton);
        cancel = findViewById(R.id.cancelButton);
        Intent intent = getIntent();
        tournamentID = intent.getStringExtra("tournamentID");
        getTournament(tournamentID);


        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(startDate,0);
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(endDate,1);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(Admin_update_tournament.this)
                        .setTitle("Tournament Deletion Confirmation")
                        .setMessage("Are you sure you want to delete this tournament?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                controller.deleteTournament(tournamentID,getApplicationContext());
                                Intent aIntent = new Intent(Admin_update_tournament.this, Admin_Activity.class);
                                startActivity(aIntent);
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
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTournament();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aIntent = new Intent(Admin_update_tournament.this, Admin_Activity.class);
                startActivity(aIntent);
            }
        });
    }

    public void setDate(EditText editText, int dType) {
        // Get the current date to set it as the minimum date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (dType == 1){
            try {
                Date date = null;
                date = controller.getSdf().parse(String.valueOf(startDate.getText()));
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_YEAR,1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{endDate.setText("");}

        // Create a new date picker dialog and set the minimum date
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                Admin_update_tournament.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(year, month, day);
                        // Update the edit text with the selected date
                        String selectedDate = controller.getSdf().format(calendar.getTimeInMillis());
                        editText.setText(selectedDate);
                    }
                },
                year, month, day);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        // Show the date picker dialog
        datePickerDialog.show();
    }

    public void updateTournament(){
        String tName, tStart, tEnd, tStatus;
        tName = tourName.getText().toString().toUpperCase().trim();
        tStart = startDate.getText().toString();
        tEnd = endDate.getText().toString();
        try{

            if(TextUtils.isEmpty(tName) || TextUtils.isEmpty(tStart) || TextUtils.isEmpty(tEnd)){
                Toast.makeText(Admin_update_tournament.this, "Please Complete all fields", Toast.LENGTH_SHORT).show();
            }else{
                Date sDate = controller.getSdf().parse(tStart);
                Date eDate = controller.getSdf().parse(tEnd);
                Date today = new Date();

                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(sDate);
                cal1.set(Calendar.HOUR_OF_DAY, 0);
                cal1.set(Calendar.MINUTE, 0);
                cal1.set(Calendar.SECOND, 0);
                cal1.set(Calendar.MILLISECOND, 0);

                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(eDate);
                cal2.set(Calendar.HOUR_OF_DAY, 0);
                cal2.set(Calendar.MINUTE, 0);
                cal2.set(Calendar.SECOND, 0);
                cal2.set(Calendar.MILLISECOND, 0);

                Calendar cal3 = Calendar.getInstance();
                cal3.setTime(today);
                cal3.set(Calendar.HOUR_OF_DAY, 0);
                cal3.set(Calendar.MINUTE, 0);
                cal3.set(Calendar.SECOND, 0);
                cal3.set(Calendar.MILLISECOND, 0);

                int result = cal1.compareTo(cal2);
                if(result>0) {
                    Toast.makeText(Admin_update_tournament.this, "End date must be after start date", Toast.LENGTH_SHORT).show();
                }else {
                    if(cal3.compareTo(cal1)==0){
                        tStatus = "ONGOING";
                    }else{
                        tStatus = "UPCOMING";
                    }
                    controller.updateTournament(tournamentID, tName, tStart, tEnd,tStatus, getApplicationContext());
                    Intent aIntent = new Intent(Admin_update_tournament.this, Admin_Activity.class);
                    startActivity(aIntent);
                }
            }

        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    public void getTournament(String tID){
        DatabaseReference tour = Controller.getReference().child("tournaments").child(tID);
        tour.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Tournament tournament = snapshot.getValue(Tournament.class);
                if(tournament!=null){
                    tourName.setText(tournament.getName());
                    startDate.setText(tournament.getStartDate());
                    endDate.setText(tournament.getEndDate());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}