package com.weber.cs3230.questions;

import com.weber.cs3230.QuestionStrings;

import java.util.ArrayList;
import java.util.List;

public class LargestWhaleSpecies extends QuestionStrings {
    public LargestWhaleSpecies(){
        answers.add("The largest species of whale is the blue whale.");
        answers.add("The blue whale holds the title for being the largest species of whale.");
        answers.add("Blue whales are known to be the largest species of whale in existence.");
    }

    @Override
    public List<String> questionVariableName() {
        ArrayList<String> keywords = new ArrayList<>();
        keywords.add("largest");
        keywords.add("biggest");
        return keywords;
    }
}
