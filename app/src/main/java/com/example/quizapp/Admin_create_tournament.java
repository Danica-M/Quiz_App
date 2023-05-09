package com.example.quizapp;



import static com.example.quizapp.models.Controller.getApiCategoryLookup;
import static com.example.quizapp.models.Controller.getApiUrl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quizapp.models.Controller;
import com.example.quizapp.models.Question;
import com.example.quizapp.models.Tournament;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Admin_create_tournament extends AppCompatActivity {

    Controller controller;
    Button cancel, create;
    EditText tourName, startDate, endDate;
    Spinner category;
    RadioGroup difficulty;

    ArrayList<Integer> categoryIDs;
    ArrayList<String> categories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = new Controller();
        categories = new ArrayList<>();
        categoryIDs = new ArrayList<>();

        setContentView(R.layout.admin_create_tournament);
        cancel = findViewById(R.id.cancelButton);
        create = findViewById(R.id.updateButton);
        tourName = findViewById(R.id.tourName);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        category = findViewById(R.id.categorySpinner);
        difficulty = findViewById(R.id.difficulty);
        difficulty.check(difficulty.getChildAt(0).getId());
        getAllCategories();


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

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_create_tournament.this, Admin_Activity.class);
                startActivity(intent);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTournament();
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

                Date date = Controller.getSdf().parse(String.valueOf(startDate.getText()));
                assert date != null;
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_YEAR,1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{endDate.setText("");}

        // Create a new date picker dialog and set the minimum date
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                Admin_create_tournament.this,
                new DatePickerDialog.OnDateSetListener() {
                                        @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(year, month, day);
                        // Update the edit text with the selected date
                        String selectedDate = Controller.getSdf().format(calendar.getTimeInMillis());
                        editText.setText(selectedDate);
                    }
                },
                year, month, day);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        // Show the date picker dialog
        datePickerDialog.show();
    }

    public void getAllCategories(){
        StringRequest stringRequest = new StringRequest(getApiCategoryLookup(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jResponse = jsonObject.getJSONArray("trivia_categories");

                            for(int i = 0; i < jResponse.length(); i++){
                                JSONObject arrayItem = jResponse.getJSONObject(i);
                                categoryIDs.add(arrayItem.getInt("id"));
                                categories.add(arrayItem.getString("name"));
                            }
                            category.setAdapter(new ArrayAdapter<>(Admin_create_tournament.this, android.R.layout.simple_spinner_dropdown_item,categories ));
                        } catch (JSONException e) {
                            Toast.makeText(Admin_create_tournament.this, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error occurred during request
                        error.printStackTrace();
                    }
                });

        //Adding request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    public void createTournament(){
        String tName, tDifficulty, tStart, tEnd, tStatus;
        tName = tourName.getText().toString().toUpperCase().trim();
        RadioButton selected = findViewById(difficulty.getCheckedRadioButtonId());
        tDifficulty = selected.getText().toString();
        tStart = startDate.getText().toString();
        tEnd = endDate.getText().toString();
        try{

            if(TextUtils.isEmpty(tName) || TextUtils.isEmpty(tStart) || TextUtils.isEmpty(tEnd)){
                Toast.makeText(Admin_create_tournament.this, "Please Complete all fields", Toast.LENGTH_SHORT).show();
            }else{
                Date sDate = Controller.getSdf().parse(tStart);
                Date eDate = Controller.getSdf().parse(tEnd);
                Date today = new Date();

                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(Objects.requireNonNull(sDate));
                cal1.set(Calendar.HOUR_OF_DAY, 0);
                cal1.set(Calendar.MINUTE, 0);
                cal1.set(Calendar.SECOND, 0);
                cal1.set(Calendar.MILLISECOND, 0);

                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(Objects.requireNonNull(eDate));
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
                    Toast.makeText(Admin_create_tournament.this, "End date must be after start date", Toast.LENGTH_SHORT).show();
                }else {
                    if(cal3.compareTo(cal1)==0){
                        tStatus = "ONGOING";
                    }else{
                        tStatus = "UPCOMING";
                    }
                    Toast.makeText(this, "cat: "+categories.get(category.getSelectedItemPosition()), Toast.LENGTH_SHORT).show();
                    getTournament(tName,category.getSelectedItemPosition(),tDifficulty,tStart,tEnd,tStatus);
                }
            }

        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    public void getTournament(String tName, Integer tCategory, String tDifficulty, String tStart, String tEnd, String tStatus) {

        StringRequest stringRequest = new StringRequest(getApiUrl() + "&category=" + categoryIDs.get(tCategory) + "&difficulty=" + tDifficulty + "&type=multiple",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("TAG", "String: "+getApiUrl() + "category=" + categoryIDs.get(tCategory) + "&difficulty=" + tDifficulty + "&type=multiple");
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            List<Question> questionList = new ArrayList<>();
                            // Loop through the JSON array
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);

                                // Get the details of the question
                                String question = item.getString("question");
                                String correctAnswer = item.getString("correct_answer");
                                JSONArray incorrectAnswersArray = item.getJSONArray("incorrect_answers");
                                List<String> incorrectAnswersList = new ArrayList<>();
                                for (int k = 0; k < incorrectAnswersArray.length(); k++) {
                                    incorrectAnswersList.add(incorrectAnswersArray.getString(k));
                                }
                                // Create a question object
                                Question questionObj = new Question(question, correctAnswer, incorrectAnswersList);
                                questionList.add(questionObj);
                                }
                            Tournament t = controller.addTournament(tName,categories.get(tCategory), tDifficulty, tStart, tEnd, tStatus, questionList);

                            if(t!=null){
                                Toast.makeText(Admin_create_tournament.this, "Tournament created successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Admin_create_tournament.this, Admin_Activity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(Admin_create_tournament.this, "Tournament creation is unsuccessfully", Toast.LENGTH_SHORT).show();
                            }

                            } catch (JSONException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}