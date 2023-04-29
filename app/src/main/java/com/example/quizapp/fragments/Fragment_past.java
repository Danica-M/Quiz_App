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
 * Use the {@link Fragment_past#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class Fragment_past extends Fragment {

    Controller controller;
    private RecyclerView ongoingRecycler;
    private Adapter adapter;
    private String uID;

    private ArrayList<Tournament> pTournaments;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

    public Fragment_past() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        controller = new Controller();
        pTournaments = new ArrayList<>();
        uID = "asasa";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_past, container, false);

        ongoingRecycler = view.findViewById(R.id.paRecycler);
        ongoingRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        getPastTournament();

        adapter = new Adapter(getContext(), pTournaments);
        ongoingRecycler.setAdapter(adapter);

        return view;
    }

    public void getPastTournament(){
        DatabaseReference tourRef =  controller.getReference().child("tournaments");
        Query query = tourRef.orderByChild("startDate");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot tourItems: snapshot.getChildren()){
                    Tournament tournament = tourItems.getValue(Tournament.class);
                    if(tournament != null && tournament.getParticipants() != null){

                        for (TournamentResultRecord result : tournament.getParticipants()) {
                            if (result.getTourPlayerID().equals(uID)) {
                                pTournaments.add(tournament);
                                break;
                            }
                        }

                    }
                }
                if(pTournaments.size()==0){Toast.makeText(getActivity(), "No past tournament", Toast.LENGTH_LONG).show();}
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}