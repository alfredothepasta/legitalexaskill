package com.weber.cs3230;

public class NoAvailableAnswerException extends Exception {
    private final String intent;

    public NoAvailableAnswerException(String intent) {
        this.intent = intent;
    }

    public String getIntent() {
        return intent;
    }
}
