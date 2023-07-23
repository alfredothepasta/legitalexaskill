package com.weber.cs3230.adminapp.dataItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnswerDummyData {
    private final Map<String, ArrayList<String>> AnswerList = new HashMap<>();

    public AnswerDummyData(){
        AnswerList.put("largest_whale_species", dummyAnswers());
        AnswerList.put("whale_communication", dummyAnswers());
        AnswerList.put("known_for_displays", dummyAnswers());
        AnswerList.put("blue_whale_diet", dummyAnswers());
        AnswerList.put("toothed_vs_baleen", dummyAnswers());
        AnswerList.put("humpback_song_length", dummyAnswers());
        AnswerList.put("purpose_of_blowhole", dummyAnswers());
        AnswerList.put("how_they_migrate", dummyAnswers());
        AnswerList.put("average_whale_lifespan", dummyAnswers());
        AnswerList.put("how_whales_use_echolocation", dummyAnswers());
    }

    private ArrayList<String> dummyAnswers(){
        ArrayList<String> dummyAnswers =  new ArrayList<String>();
        dummyAnswers.add("Answer1");
        dummyAnswers.add("Answer2");
        dummyAnswers.add("Answer3");
        return dummyAnswers;
    }

    public Map<String, ArrayList<String>> getAnswerList() {
        return AnswerList;
    }

}
