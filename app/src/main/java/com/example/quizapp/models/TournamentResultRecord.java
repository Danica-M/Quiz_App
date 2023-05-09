package com.example.quizapp.models;

public class TournamentResultRecord {
    private String tourPlayerID;
    private String tourScore;
    public TournamentResultRecord(String tourPlayerID, String tourScore) {
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

    public String getTourScore() {
        return tourScore;
    }

    public void setTourScore(String tourScore) {
        this.tourScore = tourScore;
    }

}
