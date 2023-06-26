package com.weber.cs3230;

import com.weber.cs3230.dto.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class AlexaIntentHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public Answer handleIntent(@PathVariable String intentString) throws NoAvailableAnswerException {
        AlexaIntent intent = AlexaIntent.getIntentFromString(intentString);
        log.info("Intent String: " + intentString);
         if(intent == null){
             return null;
         }
         log.info("Intent Enum: " + intent.name());
         AnswerGenerator answerGenerator = intent.getAnswer();
         return new Answer(answerGenerator.getAnswer());




//        return new Answer("nice job, your service is up and running! Here's what you passed: " + intentString);

    }
}
