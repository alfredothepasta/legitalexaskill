package com.weber.cs3230.adminapp.dto;

import java.util.ArrayList;
import java.util.List;

public class IntentAnswerList {

    private List<IntentAnswer> answers = new ArrayList<>();

    public List<IntentAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<IntentAnswer> answers) {
        this.answers = answers;
    }
}
