package com.example.quizapp.models;

public class TournamentResultRecord {
    private String trrID;
    private String tourID;
    private String tourPlayerID;
    private int tourScore;
    private boolean like;

    public TournamentResultRecord(String trrID, String tourID, String tourPlayerID, int tourScore,boolean like) {
        this.trrID = trrID;
        this.tourID = tourID;
        this.tourPlayerID = tourPlayerID;
        this.tourScore = tourScore;
        this.like = like;
    }

    public String getTrrID() {
        return trrID;
    }

    public void setTrrID(String trrID) {
        this.trrID = trrID;
    }

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

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
}
