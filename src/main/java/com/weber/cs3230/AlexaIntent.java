package com.weber.cs3230;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

public enum AlexaIntent {
    LARGEST_WHALE("largest_whale_species", new Question01()),
    WHALE_COMMUNICATION("whale_communication", new Question02()),
    HUMPBACK_KNOWN_FOR("known_for_displays", new Question03()),
    BLUE_WHALE_DIET("blue_whale_diet", new Question04()),
    TOOTHED_V_BALEEN("toothed_vs_baleen", new Question05()),
    HUMPBACK_SONG_LENGTH("humpback_song_length", new Question06()),
    PURPOSE_OF_BLOWHOLE("purpose_of_blowhole", new Question07()),
    MIGRATION("how_they_migrate", new Question08()),
    AVG_LIFESPAN("average_whale_lifespan", new Question09()),
    ECHOLOCATION("how_whales_use_echolocation", new Question10());



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
//        for (AlexaIntent alexaIntent : AlexaIntent.values()) {
//            if (alexaIntent.intentName.equalsIgnoreCase(intentString)) {
//                return alexaIntent;
//            }
//        }

        return Arrays.asList(AlexaIntent.values()).stream()
                .filter(thing -> thing.intentName.equalsIgnoreCase(intentString))
                .findFirst().orElse(null);
    }


    // This is for me and me only
    public static void main(String[] args) {

    }
}
