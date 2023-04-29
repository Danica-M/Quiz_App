package com.example.quizapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.models.Controller;
import com.example.quizapp.models.Tournament;

import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {
    Context context;
    Controller controller;
    ArrayList<Tournament> tournamentList;

    public AdminAdapter(Context context, ArrayList<Tournament> tournamentList){
        this.context = context;
        this.tournamentList = tournamentList;
        controller = new Controller();
    }


    @NonNull
    @Override
    public AdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tournament_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdapter.ViewHolder holder, int position) {
        holder.name.setText(tournamentList.get(position).getName());
        holder.category.setText(tournamentList.get(position).getCategory());
        holder.difficulty.setText(tournamentList.get(position).getDifficulty());
        holder.startDate.setText(tournamentList.get(position).getStartDate());
        holder.endDate.setText(tournamentList.get(position).getEndDate());
        holder.like.setText(tournamentList.get(position).getLike().toString());
        String stat = tournamentList.get(position).getStatus();
        if(stat.equals("ONGOING")){
            holder.tourHolder.setCardBackgroundColor(Color.BLUE);
        }else if( stat.equals("UPCOMING")){
            holder.tourHolder.setCardBackgroundColor(Color.YELLOW);
        }else if(stat.equals("PAST")) {
            holder.tourHolder.setCardBackgroundColor(Color.GRAY);
        }

        final int finalPosition = position;
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                .setTitle("Alert!")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Tournament tournament = tournamentList.get(finalPosition);
                        //delete tournament from database
                        controller.deleteTournament(tournament.getTournamentID(),context);
                        // Delete the item from recycler
                        tournamentList.remove(finalPosition);
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No", null)
                .show();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bIntent = new Intent(context, Admin_update_tournament.class);
                bIntent.putExtra("tournamentID", tournamentList.get(finalPosition).getTournamentID());
                bIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(bIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return tournamentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, category, difficulty, startDate, endDate, like;
        CardView tourHolder;
        Button edit, delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            category = itemView.findViewById(R.id.category);
            difficulty = itemView.findViewById(R.id.difficult);
            startDate = itemView.findViewById(R.id.start);
            endDate = itemView.findViewById(R.id.end);
            like  = itemView.findViewById(R.id.like);
            tourHolder = itemView.findViewById(R.id.tourHolder);
            edit = itemView.findViewById(R.id.editButton);
            delete = itemView.findViewById(R.id.deleteButton);
        }
    }
}
