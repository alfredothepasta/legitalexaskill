package com.weber.cs3230;

import java.util.*;

public abstract class AnswerGenerator {

    protected final List<String> answers = new ArrayList<>();
    protected String lastUsedAnswer;

    public String getAnswer() {
        String output;
        // shuffle
        Collections.shuffle(answers);

        // set output to random output
        output = answers.get(0);

        // check if the last used answer has an answer, if yes, put the old one back in
        if(lastUsedAnswer != null){
            answers.add(lastUsedAnswer);
        }

        // take out the current answer and add back in the thing
        lastUsedAnswer = answers.get(0);
        answers.remove(0);
        return output;
    }
}
