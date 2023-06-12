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


import com.example.quizapp.models.Adapter;

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

public class Fragment_past extends Fragment {

    Controller controller;
    private Adapter adapter;
    private TextView none;
    private String uID;

    private ArrayList<Tournament> pTournaments;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public Fragment_past() {
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
        pTournaments = new ArrayList<>();
        uID = Controller.getCurrentUser().getUserID();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_past, container, false);

        none = view.findViewById(R.id.none);
        none.setVisibility(View.GONE);
        RecyclerView ongoingRecycler = view.findViewById(R.id.paRecycler);
        ongoingRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        getPastTournament();

        adapter = new Adapter(getContext(), pTournaments);
        ongoingRecycler.setAdapter(adapter);

        return view;
    }

    public void getPastTournament(){
        DatabaseReference tourRef =  Controller.getReference().child("tournaments");
        Query query = tourRef.orderByChild("startDate");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot tourItems: snapshot.getChildren()){
                    Tournament tournament = tourItems.getValue(Tournament.class);
                    if(tournament != null && tournament.getParticipants() != null){

                        for (TournamentResultRecord result : tournament.getParticipants()) {
                            if (result.getTourPlayerID().equals(uID)) {
                                pTournaments.add(tournament);
                            }
                        }

                    }
                }
                if(pTournaments.size()==0){
                    none.setText("No past participated tournament");
                    none.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}