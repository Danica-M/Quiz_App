package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.quizapp.models.Controller;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class Admin_update_tournament extends AppCompatActivity {

    Controller controller;
    EditText tourName, startDate, endDate;
    String tournamentID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_update_tournament);
        controller = new Controller();
        tourName = findViewById(R.id.tourName);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        Intent intent = getIntent();
        tournamentID = intent.getStringExtra("tournamentID");

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

    public void createTournament(){
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

}