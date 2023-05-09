package com.example.quizapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.Adapter;
import com.example.quizapp.R;
import com.example.quizapp.models.Controller;
import com.example.quizapp.models.Tournament;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_upcoming#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_upcoming extends Fragment {
    Controller controller;
    private RecyclerView upcomingRecycler;
    private Adapter adapter;
    private TextView none2;
    private ArrayList<Tournament> tournaments;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_upcoming() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_upcoming.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_upcoming newInstance(String param1, String param2) {
        Fragment_upcoming fragment = new Fragment_upcoming();
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
        tournaments = new ArrayList<>();
        controller = new Controller();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);

        none2 = view.findViewById(R.id.none2);
        upcomingRecycler = view.findViewById(R.id.upRecycler);
        upcomingRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        getUpcomingTournament();

        adapter = new Adapter(getContext(), tournaments);
        upcomingRecycler.setAdapter(adapter);

        return view;
    }


    public void getUpcomingTournament(){
        DatabaseReference tourRef =  controller.getReference().child("tournaments");
        Query query = tourRef.orderByChild("startDate");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

               for(DataSnapshot tourItems: snapshot.getChildren()){
                   Tournament tournament = tourItems.getValue(Tournament.class);
                   if(tournament != null && tournament.getStatus().equals("UPCOMING")){
                       tournaments.add(tournament);
                   }
               }
               if(tournaments.size()==0){
                   none2.setText("No upcoming tournament");
                   none2.setVisibility(View.VISIBLE);
               }
               adapter.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });


    }
}