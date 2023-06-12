package com.example.quizapp.models;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.Admin_update_tournament;
import com.example.quizapp.R;
import com.example.quizapp.User_Tournament_Activity;


import java.util.ArrayList;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    ArrayList<Tournament> tournamentList;

    public Adapter(Context context, ArrayList<Tournament> tournamentList){
        this.context = context;
        this.tournamentList = tournamentList;
    }


    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tournament_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        Tournament clickedTournament = tournamentList.get(position);
        holder.name.setText(tournamentList.get(position).getName());
        holder.category.setText(tournamentList.get(position).getCategory());
        holder.difficulty.setText(tournamentList.get(position).getDifficulty().toUpperCase());
        holder.startDate.setText(tournamentList.get(position).getStartDate());
        holder.endDate.setText(tournamentList.get(position).getEndDate());
        holder.like.setText(String.valueOf(tournamentList.get(position).getLike()));
        String stat = tournamentList.get(position).getStatus();
        switch (stat) {
            case "ONGOING":
                holder.tourHolder.setBackgroundResource(R.color.light_green);
                if (Controller.getCurrentUser().getUserType().equals("player")) {
                    holder.tourHolder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent bIntent = new Intent(view.getContext(), User_Tournament_Activity.class);
                            bIntent.putExtra("tourID", clickedTournament.getTournamentID());
                            bIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(bIntent);
                        }
                    });
                }
                break;
            case "UPCOMING":
                holder.tourHolder.setBackgroundResource(R.color.light_yellow);
                break;
            case "PAST":
                holder.tourHolder.setBackgroundResource(R.color.light_red);
                break;
        }

        if (tournamentList.get(position).getParticipants() != null) {
            for (TournamentResultRecord result : tournamentList.get(position).getParticipants()) {
                if (result.getTourPlayerID().equals(Controller.getCurrentUser().getUserID())) {
                    holder.tourHolder.setBackgroundResource(R.color.light_grey);
                    holder.tourHolder.setClickable(false);
                    break;
                }
            }
        }
        if (Controller.getCurrentUser().getUserType().equals("admin")) {
            holder.tourHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent bIntent = new Intent(view.getContext(), Admin_update_tournament.class);
                    bIntent.putExtra("tournamentID", clickedTournament.getTournamentID());
                    bIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(bIntent);
                }
            });
        }
    }

    public void setFilteredList(ArrayList<Tournament> filteredList){
        this.tournamentList = filteredList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return tournamentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, category, difficulty, startDate, endDate, like;
        ConstraintLayout tourHolder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            category = itemView.findViewById(R.id.category);
            difficulty = itemView.findViewById(R.id.difficult);
            startDate = itemView.findViewById(R.id.start);
            endDate = itemView.findViewById(R.id.end);
            like  = itemView.findViewById(R.id.like);
            tourHolder = itemView.findViewById(R.id.tourHolder);


        }
    }
}
