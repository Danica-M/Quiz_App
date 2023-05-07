package com.example.quizapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.models.Controller;
import com.example.quizapp.models.Tournament;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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
        holder.difficulty.setText(tournamentList.get(position).getDifficulty());
        holder.startDate.setText(tournamentList.get(position).getStartDate());
        holder.endDate.setText(tournamentList.get(position).getEndDate());
        holder.like.setText(tournamentList.get(position).getLike().toString());
        String stat = tournamentList.get(position).getStatus();
        if(stat.equals("ONGOING")){
//            holder.tourHolder.setCardBackgroundColor(Color.BLUE);
            holder.tourHolder.setBackgroundResource(R.drawable.one);
            if(Controller.getCurrentUser().getUserType().equals("player")){
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
        }else if( stat.equals("UPCOMING")){
//            holder.tourHolder.setBackgroundColor(Color.YELLOW);
            holder.tourHolder.setBackgroundResource(R.drawable.two);
        }else{
//            holder.tourHolder.setBackgroundColor(Color.GRAY);
            holder.tourHolder.setBackgroundResource(R.drawable.three);
        }

        if(Controller.getCurrentUser().getUserType().equals("admin") ){
            holder.tourHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Log.d("TAG", "tId: "+clickedTournament.getTournamentID());
                    Intent bIntent = new Intent(view.getContext(), Admin_update_tournament.class);
                    bIntent.putExtra("tournamentID", clickedTournament.getTournamentID());
                    bIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(bIntent);
                }
            });
        }else{

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
