package com.weber.cs3230;


import com.weber.cs3230.db.AlexaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AnswerGenerator {


    protected String lastUsedAnswer;

    // first we must invoke the 10,000 things
    private final AlexaDAO daoDeJing;

    protected String intentString;

    @Autowired
    public AnswerGenerator(AlexaDAO theDao) {
        daoDeJing = theDao;
    }

    public void setIntent(String intentString){
        this.intentString = intentString;
    }

    public String getAnswer(String intentString) throws NoAvailableAnswerException {

        List<String> answers = daoDeJing.getAnswersForIntent(intentString);

        // this is disgusting
        if(answers.size() == 0){
            throw new NoAvailableAnswerException(AlexaIntent.getIntentFromString(intentString));
        }


        String output;
        //
//        if(answers.size() == 1 && lastUsedAnswer == null){
//            return answers.get(0);
//        }
//        // shuffle
//        Collections.shuffle(answers);
//
//        // set output to random output
//        output = answers.get(0);
//
//        // check if the last used answer has an answer, if yes, put the old one back in
//        if(lastUsedAnswer != null){
//            answers.add(lastUsedAnswer);
//        }
//
//        // take out the current answer and add back in the thing
//        lastUsedAnswer = answers.get(0);
//        answers.remove(0);

        Collections.shuffle(answers);

        // check for the two conditions that allow us to pluck the first answer, if they aren't satisfied, grab the next
        if(lastUsedAnswer == null || !lastUsedAnswer.equals(answers.get(0))){
            output = answers.get(0);
            lastUsedAnswer = answers.get(0);
        } else {
            output = answers.get(1);
            lastUsedAnswer = answers.get(1);
        }

        return output;
    }
}
