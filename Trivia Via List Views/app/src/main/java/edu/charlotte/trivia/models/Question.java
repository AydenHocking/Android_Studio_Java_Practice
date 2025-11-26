package edu.charlotte.trivia.models;

import java.io.Serializable;

public class Question implements Serializable {
    String question, imgUrl;
    String[] answers;
    int correctAnswerIndex;

    public Question(String question, String[] answers, int correctAnswerIndex, String imgUrl) {
        this.question = question;
        this.answers = answers;
        this.imgUrl = imgUrl;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public Question() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
