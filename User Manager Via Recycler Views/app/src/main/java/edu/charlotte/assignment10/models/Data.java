package edu.charlotte.assignment10.models;

import edu.charlotte.assignment10.R;

public class Data {
    private static String[] ageGroups = {"Under 12 years old", "12-17 years old", "18-24 years old", "25-34 years old", "35-44 years old", "45-54 years old", "55-64 years old", "65-74 years old", "75 years or older"};

    private static Mood[] moods = {
            new Mood("Very Good", R.drawable.very_good),
            new Mood("Good", R.drawable.good),
            new Mood("Ok", R.drawable.ok),
            new Mood("Sad", R.drawable.sad),
            new Mood("Not Well", R.drawable.not_well)
    };

    public static String[] getAgeGroups() {
        return ageGroups;
    }

    public static Mood[] getMoods() {
        return moods;
    }

}
