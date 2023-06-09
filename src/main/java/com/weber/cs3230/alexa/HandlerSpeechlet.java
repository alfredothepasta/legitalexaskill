package com.weber.cs3230.alexa;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.*;
import com.weber.cs3230.AlexaIntentHandler;
import com.weber.cs3230.dto.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HandlerSpeechlet implements SpeechletV2 {

    AlexaIntentHandler handler;
    AlexaUtils utils = new AlexaUtils("Ask me something else");

    @Autowired
    public HandlerSpeechlet(AlexaIntentHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {

    }

    @Override
    public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
        return utils.getOnLaunchResponse(requestEnvelope.getSession(), "Whale Facts", "Ask me about whales.");
    }

    @Override
    public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
        Answer answer = handler.handleIntent(requestEnvelope.getRequest().getIntent().getName());
        if(answer == null){
            return utils.getUnrecognizedResponse(requestEnvelope.getSession(), "Whale Facts", "You wot mate?");
        }
        return utils.getNormalResponse(requestEnvelope.getSession(), "Whale Facts", answer.getText());
    }

    @Override
    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {

    }
}
