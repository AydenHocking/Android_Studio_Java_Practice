package edu.charlotte.trivia.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Trivia implements Serializable {
    ArrayList<Question> questions = new ArrayList<>();
    String description, title;

    public Trivia() {
    }

    public Trivia(String title, String description, ArrayList<Question> questions) {
        this.questions = questions;
        this.title = title;
        this.description = description;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
