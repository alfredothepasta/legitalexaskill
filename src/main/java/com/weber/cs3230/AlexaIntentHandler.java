package com.weber.cs3230;

import com.weber.cs3230.db.AlexaDAO;
import com.weber.cs3230.dto.Answer;
import com.weber.cs3230.dto.IntentDetail;
import com.weber.cs3230.dto.IntentDetailList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Component
public class AlexaIntentHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final AnswerGenerator answerGenerator;
    private final List<String> intentDetailList;

    @Autowired
    public AlexaIntentHandler(AnswerGenerator answerGenerator) {
        this.answerGenerator = answerGenerator;
        intentDetailList = new ArrayList<>();
        AlexaDAO alexaDAO = new AlexaDAO();
        for (IntentDetail i : alexaDAO.getIntentList().getIntents()) {
            intentDetailList.add(i.getName());
        }
    }

    public Answer handleIntent(@PathVariable String intentString) throws NoAvailableAnswerException {
        boolean valid = intentDetailList.contains(intentString);
        log.info("Intent String: " + intentString);
         if(!valid){
             return null;
         }
         log.info("Intent Enum: " + intentString);
         return new Answer(answerGenerator.getAnswer(intentString));




//        return new Answer("nice job, your service is up and running! Here's what you passed: " + intentString);
    }
}
