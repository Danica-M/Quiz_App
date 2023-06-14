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
import com.example.quizapp.R;


import java.util.ArrayList;

public class Participated_Adapter extends RecyclerView.Adapter<Participated_Adapter.ViewHolder> {
    Context context;
    ArrayList<Tournament> tournamentList;

    public Participated_Adapter(Context context, ArrayList<Tournament> tournamentList){
        this.context = context;
        this.tournamentList = tournamentList;
    }


    @NonNull
    @Override
    public Participated_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tournament_item, parent, false);
        return new Participated_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tournament clickedTournament = tournamentList.get(position);
        holder.name.setText(tournamentList.get(position).getName());
        holder.category.setText(tournamentList.get(position).getCategory());
        holder.difficulty.setText(tournamentList.get(position).getDifficulty().toUpperCase());
        holder.startDate.setText(tournamentList.get(position).getStartDate());
        holder.endDate.setText(tournamentList.get(position).getEndDate());
        holder.like.setText(String.valueOf(tournamentList.get(position).getLike()));
        holder.tourHolder.setBackgroundResource(R.color.light_grey);
        holder.tourHolder.setClickable(false);

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

