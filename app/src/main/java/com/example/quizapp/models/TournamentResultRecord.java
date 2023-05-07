package com.example.quizapp.models;

public class TournamentResultRecord {
    private String tourPlayerID;
    private int tourScore;
    public TournamentResultRecord( String tourPlayerID, int tourScore) {
        this.tourPlayerID = tourPlayerID;
        this.tourScore = tourScore;

    }
    public TournamentResultRecord(){}

    public String getTourPlayerID() {
        return tourPlayerID;
    }

    public void setTourPlayerID(String tourPlayerID) {
        this.tourPlayerID = tourPlayerID;
    }

    public int getTourScore() {
        return tourScore;
    }

    public void setTourScore(int tourScore) {
        this.tourScore = tourScore;
    }

}
