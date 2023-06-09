package com.weber.cs3230;

import java.util.ArrayList;
import java.util.List;

public class Question02 extends QuestionStrings {
    public Question02(){
        answers.add("Whales communicate with each other through a variety of vocalizations and sounds.");
        answers.add("Communication among whales involves the use of vocalizations and sounds.");
        answers.add("The means of communication among whales involve various vocalizations and sounds.");
    }

    @Override
    public List<String> questionVariableName() {
        ArrayList<String> keywords = new ArrayList<>();
        keywords.add("communication");
        keywords.add("communicate");
        keywords.add("talk");
        return keywords;
    }
}
