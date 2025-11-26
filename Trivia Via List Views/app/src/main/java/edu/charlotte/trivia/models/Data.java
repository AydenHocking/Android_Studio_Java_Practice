package edu.charlotte.trivia.models;

import java.util.ArrayList;

public class Data {
    public static ArrayList<Trivia> getAllTrivias(){
        ArrayList<Trivia> trivias = new ArrayList<>();
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("Vienna is the capital of?", new String[]{"Sweden", "Austria", "Germany", "Italy"}, 1, null));
        questions.add(new Question("Euro is not the currency used by?", new String[]{"Spain", "France", "Germany", "United Kingdom"}, 3, "https://www.theappsdr.com/imgs/euro.jpeg"));
        questions.add(new Question("What is the capital of Italy?", new String[]{"Rome", "Milan", "Naples", "Pisa"}, 0, null));
        questions.add(new Question("Which of the following countries is landlocked?", new String[]{"Brazil", "Cuba", "Bolivia", "Colombia"}, 2, null));
        questions.add(new Question("How many metres is one mile?", new String[]{"100 meters", "1000 meters", "1705.212 meters", "1609.344 meters"}, 3, null));
        questions.add(new Question("Renminbi is the currency of which country?", new String[]{"Japan", "China", "Nepal", "Bhutan"}, 1, "https://www.theappsdr.com/imgs/china_flag.png"));
        questions.add(new Question("What is the capital of North Carolina?", new String[]{"Chicago", "New York", "Charlotte", "Raleigh", "Miami"}, 3, "https://www.theappsdr.com/imgs/nc_map.jpeg"));
        trivias.add(new Trivia("Geography Trivia", "Trivia focusing on geography questions", questions));

        questions = new ArrayList<>();
        questions.add(new Question("The United States' Apollo 11 was the first crewed mission to land on the Moon on", new String[]{"July 20, 1968", "July 20, 1969", "May 20, 1969", "April 20, 1971"}, 1, "https://www.theappsdr.com/imgs/apollo_11.jpeg"));
        questions.add(new Question("Who was the first female pilot to fly solo across the Atlantic Ocean?", new String[]{"Bonnie Gann", "Elsie MacGill", "Amelia Earhart", "Linda Godwin"}, 2, "https://www.theappsdr.com/imgs/amelia_earhart.jpeg"));
        questions.add(new Question("Who built the first car in America?", new String[]{"Henry Ford", "Nikola Tesla", "Alexander Graham Bell", "Albert Einstein"}, 0, null));
        questions.add(new Question("UNC Charlotte was established on", new String[]{"Sept. 23, 1946", "Sept. 23, 1949", "Sept. 23, 1950", "Sept. 23, 1959"}, 0, "https://www.theappsdr.com/imgs/uncc.png"));
        trivias.add(new Trivia("History Trivia", "Trivia focusing on history questions", questions));

        questions = new ArrayList<>();
        questions.add(new Question("Who is Lionel Messi?", new String[]{"Olympic Swimmer", "Soccer Player", "Basketball Player"}, 1, "https://www.theappsdr.com/imgs/messi.jpeg"));
        questions.add(new Question("Who is LeBron James?", new String[]{"Olympic Swimmer", "Soccer Player", "Basketball Player"}, 2, null));
        questions.add(new Question("FIFA is a non-profit organization that governs ?", new String[]{"Soccer", "Swimming", "Tennis", "Basketball"}, 0, null));
        questions.add(new Question("Who has won more tennis grand slam titles, Venus Williams or Serena Williams?", new String[]{"Serena Williams", "Venus Williams"}, 0, "https://www.theappsdr.com/imgs/serena_williams.jpeg"));
        questions.add(new Question("The boxer that said 'Float like a butterfly, sting like a bee. Your hands can’t hit what your eyes can’t see.'", new String[]{"Muhammad Ali", "Mike Tyson", "Floyd Mayweather", "George Foreman"}, 0, "https://www.theappsdr.com/imgs/muhammad_ali.jpg"));
        questions.add(new Question("Did you have fun working on this app?", new String[]{"Yes", "No", "No", "No", "No", "No", "No", "No", "No"}, 0, null));
        trivias.add(new Trivia("Sports Trivia", "Trivia focusing on sports questions", questions));
        return trivias;
    }

}
