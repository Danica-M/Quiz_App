package com.example.quizapp.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question {
    private String question;
    private String correctAnswer;
    private List<String> incorrectAnswers;

    public Question(String question, String correctAnswer, List<String> incorrectAnswers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }
    public Question(){}

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(List<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public List<String> getAllAnswers() {
        List<String> allAnswers = new ArrayList<>();
        allAnswers.addAll(incorrectAnswers);
        allAnswers.add(correctAnswer);
        Collections.shuffle(allAnswers); // Randomize the order of the answers
        return allAnswers;
    }
}