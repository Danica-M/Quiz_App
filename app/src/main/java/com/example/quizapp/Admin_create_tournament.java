package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Admin_create_tournament extends AppCompatActivity {

    Button cancel, create;
    EditText tourName, startDate, endDate;
    Spinner category;
    RadioGroup difficulty;
    SimpleDateFormat sdf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        setContentView(R.layout.admin_create_tournament);
        cancel = findViewById(R.id.cancelButton);
        create = findViewById(R.id.createButton);
        tourName = findViewById(R.id.tourName);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        category = findViewById(R.id.categorySpinner);
        difficulty = findViewById(R.id.difficulty);

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
                date = sdf.parse(String.valueOf(startDate.getText()));
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_YEAR,1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Create a new date picker dialog and set the minimum date
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                Admin_create_tournament.this,
                new DatePickerDialog.OnDateSetListener() {
                                        @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(year, month, day);
                        // Update the edit text with the selected date
                        String selectedDate = sdf.format(calendar.getTimeInMillis());
                        editText.setText(selectedDate);
                    }
                },
                year, month, day);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        // Show the date picker dialog
        datePickerDialog.show();
    }
}