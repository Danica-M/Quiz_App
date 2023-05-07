package com.example.quizapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_ongoing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_ongoing extends Fragment {
    Controller controller;
    private RecyclerView ongoingRecycler;
    private Adapter adapter;

    private ArrayList<Tournament> oTournaments;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_ongoing() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_ongoing.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_ongoing newInstance(String param1, String param2) {
        fragment_ongoing fragment = new fragment_ongoing();
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
        oTournaments = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ongoing, container, false);

        ongoingRecycler = view.findViewById(R.id.onRecycler);
        ongoingRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        getOngoingTournament();

        adapter = new Adapter(getContext(), oTournaments);
        ongoingRecycler.setAdapter(adapter);

        return view;
    }


    public void getOngoingTournament(){

        DatabaseReference tourRef =  controller.getReference().child("tournaments");
        Query query = tourRef.orderByChild("startDate");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot tourItems: snapshot.getChildren()){
                    Tournament tournament = tourItems.getValue(Tournament.class);
                    if(tournament != null && tournament.getStatus().equals("ONGOING")){
                        for (TournamentResultRecord result : tournament.getParticipants()) {
                            if (result.getTourPlayerID().equals(Controller.getCurrentUser().getUserID())) {

                            }else{oTournaments.add(tournament);}
                        }

                    }
                }
                if(oTournaments.size()==0){Toast.makeText(getActivity(), "No past tournament", Toast.LENGTH_LONG).show();}
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}