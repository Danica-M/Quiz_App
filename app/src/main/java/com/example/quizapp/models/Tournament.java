package com.example.quizapp.models;

import java.util.ArrayList;
import java.util.List;

public class Tournament {
    private String tournamentID;
    private String name;
    private String category;
    private String difficulty;
    private String startDate;
    private String endDate;
    private String status;
    private List<Question> questions;
    private List<TournamentResultRecord> participants;
    private Integer like;

    public Tournament(String tournamentID, String name, String category, String difficulty, String startDate, String endDate, String status, Integer like) {
        this.tournamentID = tournamentID;
        this.name = name;
        this.category = category;
        this.difficulty = difficulty;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.questions = new ArrayList<>();
        this.participants = new ArrayList<>();
        this.like = like;
    }

    public Tournament(){};

    public String getTournamentID() {
        return tournamentID;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public List<TournamentResultRecord> getParticipants() {
        return participants;
    }

    public void setParticipants(List<TournamentResultRecord> participants) {
        this.participants = participants;
    }
}
