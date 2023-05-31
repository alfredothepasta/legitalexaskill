package com.weber.cs3230.controller;

import com.weber.cs3230.AlexaIntentHandler;
import com.weber.cs3230.dto.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainRestController {

    private final AlexaIntentHandler alexaIntentHandler;

    @Autowired
    public MainRestController(AlexaIntentHandler alexaIntentHandler) {
        this.alexaIntentHandler = alexaIntentHandler;
    }

    @RequestMapping("/answer/{intentString}")
    public Answer getAnswerForIntent(@PathVariable String intentString) {
        return alexaIntentHandler.handleIntent(intentString);
    }
}
