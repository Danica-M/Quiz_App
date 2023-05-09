package com.example.quizapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quizapp.Adapter;
import com.example.quizapp.R;
import com.example.quizapp.models.Controller;
import com.example.quizapp.models.Tournament;
import com.example.quizapp.models.TournamentResultRecord;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class fragment_ongoing extends Fragment {
    Controller controller;
    private Adapter adapter;
    private TextView none1;
    private ArrayList<Tournament> oTournaments;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public fragment_ongoing() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }

        controller = new Controller();
        oTournaments = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ongoing, container, false);

        none1 = view.findViewById(R.id.none1);
        none1.setVisibility(View.GONE);
        RecyclerView ongoingRecycler = view.findViewById(R.id.onRecycler);
        ongoingRecycler.setLayoutManager(new LinearLayoutManager(getContext()));


        getOngoingTournament();
        adapter = new Adapter(getContext(), oTournaments);
        adapter.notifyDataSetChanged();
        ongoingRecycler.setAdapter(adapter);

        return view;
    }


    public void getOngoingTournament(){

        DatabaseReference tourRef =  Controller.getReference().child("tournaments");
        Query query = tourRef.orderByChild("startDate");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot tourItems: snapshot.getChildren()){
                    Tournament tournament = tourItems.getValue(Tournament.class);
                    if(tournament != null && tournament.getStatus().equals("ONGOING")){
                        boolean found = false;
                        if(tournament.getParticipants()!=null){
                            for (TournamentResultRecord result : tournament.getParticipants()) {
                                if (result.getTourPlayerID().equals(Controller.getCurrentUser().getUserID())) {
                                    found = true;
                                    break;

                                }
                            }
                        }

                        if(!found){
                            oTournaments.add(tournament);
                        }


                    }
                }
                if(oTournaments.size()==0){
                    none1.setText("No ongoing tournament");
                    none1.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}