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

import com.example.quizapp.R;
import com.example.quizapp.models.Adapter;
import com.example.quizapp.models.Controller;
import com.example.quizapp.models.Tournament;
import com.example.quizapp.models.TournamentResultRecord;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_past#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_past extends Fragment {

    Controller controller;
    private Adapter adapter;
    private TextView none3;
    private ArrayList<Tournament> part_Tournaments;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_past() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_past.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_past newInstance(String param1, String param2) {
        Fragment_past fragment = new Fragment_past();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        controller = new Controller();
        part_Tournaments = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past, container, false);

        none3 = view.findViewById(R.id.none3);
        none3.setVisibility(View.GONE);
        RecyclerView participatedRecycler = view.findViewById(R.id.ptRecycler);
        participatedRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        getPastTournament();
        adapter = new Adapter(getContext(), part_Tournaments);
        adapter.notifyDataSetChanged();
        participatedRecycler.setAdapter(adapter);
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
                    if(tournament != null && tournament.getStatus().equals("PAST")){
                        part_Tournaments.add(tournament);
                    }
                }
                if(part_Tournaments.size()==0){
                    none3.setText("No ongoing tournament");
                    none3.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}