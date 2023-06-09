package com.weber.cs3230;

public enum AlexaIntent {
    QUESTION_1("what is the largest species of whale", new Question01()),
    QUESTION_2("how do whales communicate with each other", new Question02()),
    QUESTION_3("which species of whale is known for its spectacular acrobatic displays such as breaching and tail-slapping", new Question03()),
    QUESTION_4("what is the primary diet of a blue whale", new Question04()),
    QUESTION_5("what is the difference between a toothed whale and a baleen whale", new Question05()),
    QUESTION_6("how long can a humpback whale's song last", new Question06()),
    QUESTION_7("what is the purpose of a whale's blowhole", new Question07()),
    QUESTION_8("how do whales migrate over vast distances", new Question08()),
    QUESTION_9("what is the average lifespan of a whale", new Question09()),
    QUESTION_10("how do whales use echolocation to navigate and find food", new Question10());



    private final String intentName;
    private final AnswerGenerator answer;

    public AnswerGenerator getAnswer() {
        return answer;
    }



    AlexaIntent(String intentName, AnswerGenerator answer) {
        this.intentName = intentName;
        this.answer = answer;
    }

    public static AlexaIntent getIntentFromString(String intentString) {
        for (AlexaIntent alexaIntent : AlexaIntent.values()) {
            if (alexaIntent.intentName.equalsIgnoreCase(intentString)) {
                return alexaIntent;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(AlexaIntent.getIntentFromString("what is the largest species of whale").answer.getAnswer());
    }
}
