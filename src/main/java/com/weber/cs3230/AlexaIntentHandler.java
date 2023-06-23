package com.weber.cs3230;

import com.weber.cs3230.dto.Answer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class AlexaIntentHandler {

    public Answer handleIntent(@PathVariable String intentString) {
        AlexaIntent intent = AlexaIntent.getIntentFromString(intentString);


         if(intent == null){
             return null;
         }
        AnswerGenerator answerGenerator = intent.getAnswer();
         return new Answer(answerGenerator.getAnswer());




//        return new Answer("nice job, your service is up and running! Here's what you passed: " + intentString);

    }
}
