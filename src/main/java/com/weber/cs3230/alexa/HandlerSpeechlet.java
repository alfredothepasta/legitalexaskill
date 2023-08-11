package com.weber.cs3230.alexa;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.*;
import com.weber.cs3230.AlexaIntentHandler;
import com.weber.cs3230.NoAvailableAnswerException;
import com.weber.cs3230.dto.Answer;
import com.weber.cs3230.MetricsRecorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HandlerSpeechlet implements SpeechletV2 {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    MetricsRecorder recorder = new MetricsRecorder();
    AlexaIntentHandler handler;
    AlexaUtils utils = new AlexaUtils("Ask me something else");

    @Autowired
    public HandlerSpeechlet(AlexaIntentHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
        log.info("Session Started");
        recorder.recordMetric("StartedSession");
    }

    @Override
    public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
        log.info("On Launch Response.");
        return utils.getOnLaunchResponse(requestEnvelope.getSession(), "Whale Facts", "Ask me about whales.");
    }

    @Override
    public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
        try{
            log.info("Question asked: " + requestEnvelope.getRequest().getIntent().getName());

            Answer answer = handler.handleIntent(requestEnvelope.getRequest().getIntent().getName());
            if(answer == null){
                recorder.recordMetric("NullQuestion");
                return utils.getUnrecognizedResponse(requestEnvelope.getSession(), "Whale Facts", "You wot mate?");
            }
            return utils.   getNormalResponse(requestEnvelope.getSession(), "Whale Facts", answer.getText());
        } catch(NoAvailableAnswerException e) {
            log.error("No available answer.", e);
            log.error("Intent: ", e.getIntent());
            return utils.getUnrecognizedResponse(requestEnvelope.getSession(), "Whale Facts", "The programmer is a fool and didn't populate the answers.");

        } catch (Exception e){
            log.error("Unknown Error.", e);
            return utils.getUnrecognizedResponse(requestEnvelope.getSession(), "Whale Facts", "An unknown error has occurred. Abandon hope.");
        }
    }

    @Override
    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
        log.info("Session Ended.");

    }
}