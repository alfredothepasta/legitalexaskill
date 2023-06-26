package com.weber.cs3230;

import java.util.*;

public abstract class AnswerGenerator {

    protected final List<String> answers = new ArrayList<>();
    protected String lastUsedAnswer;

    protected String intentString;

    public void setIntent(String intentString){
        this.intentString = intentString;
    }

    public String getAnswer() throws NoAvailableAnswerException {

        // this is disgusting
        if(answers.size() == 0){
            throw new NoAvailableAnswerException(AlexaIntent.getIntentFromString(intentString));
        }


        String output;
        //
        if(answers.size() == 1 && lastUsedAnswer == null){
            return answers.get(0);
        }
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
