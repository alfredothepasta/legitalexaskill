package com.weber.cs3230;


import java.util.Arrays;

public enum AlexaIntent {
    LARGEST_WHALE("largest_whale_species"),
    WHALE_COMMUNICATION("whale_communication" ),
    HUMPBACK_KNOWN_FOR("known_for_displays"),
    BLUE_WHALE_DIET("blue_whale_diet"),
    TOOTHED_V_BALEEN("toothed_vs_baleen"),
    HUMPBACK_SONG_LENGTH("humpback_song_length"),
    PURPOSE_OF_BLOWHOLE("purpose_of_blowhole" ),
    MIGRATION("how_they_migrate" ),
    AVG_LIFESPAN("average_whale_lifespan"),
    ECHOLOCATION("how_whales_use_echolocation");
//
//
//
    private final String intentName;
//    private final AnswerGenerator answer;
//
//    public AnswerGenerator getAnswer() {
//        return answer;
//    }

    AlexaIntent(String intentName) {
        this.intentName = intentName;
    }

    public static AlexaIntent getIntentFromString(String intentString) {
//        for (AlexaIntent alexaIntent : AlexaIntent.values()) {
//            if (alexaIntent.intentName.equalsIgnoreCase(intentString)) {
//                return alexaIntent;
//            }
//        }
        MetricsRecorder metricsRecorder1 = new MetricsRecorder();
        metricsRecorder1.recordMetric(intentString);

        return Arrays.stream(AlexaIntent.values())
                .filter(thing -> thing.intentName.equalsIgnoreCase(intentString))
                .findFirst().orElse(null);
    }
//
//
//    // This is for me and me only
//    public static void main(String[] args) {
//
//    }
}
