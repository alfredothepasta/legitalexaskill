package com.weber.cs3230.adminapp.dialogs;

import java.util.ArrayList;
import java.util.Map;

public class AnswersDialog {

    private final ArrayList<String> intentAnswers;

    public AnswersDialog(String currentIntent, Map<String, ArrayList<String>> answerMap){
        intentAnswers = answerMap.get(currentIntent);
    }
}
