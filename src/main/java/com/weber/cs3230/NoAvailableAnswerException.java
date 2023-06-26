package com.weber.cs3230;

public class NoAvailableAnswerException extends Exception {
    private final AlexaIntent intent;

    public NoAvailableAnswerException(AlexaIntent intent) {
        this.intent = intent;
    }

    public AlexaIntent getIntent() {
        return intent;
    }
}
