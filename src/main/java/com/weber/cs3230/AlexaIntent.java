package com.weber.cs3230;

import com.weber.cs3230.questions.*;

import java.util.Arrays;

public enum AlexaIntent {
    LARGEST_WHALE("largest_whale_species", new LargestWhaleSpecies()),
    WHALE_COMMUNICATION("whale_communication", new WhaleCommunication()),
    HUMPBACK_KNOWN_FOR("known_for_displays", new KnownForDisplays()),
    BLUE_WHALE_DIET("blue_whale_diet", new BlueWhaleDiet()),
    TOOTHED_V_BALEEN("toothed_vs_baleen", new ToothedVsBaleen()),
    HUMPBACK_SONG_LENGTH("humpback_song_length", new HumpbackSongLength()),
    PURPOSE_OF_BLOWHOLE("purpose_of_blowhole", new PurposeOfBlowhole()),
    MIGRATION("how_they_migrate", new HowTheyMigrate()),
    AVG_LIFESPAN("average_whale_lifespan", new AverateWhaleLifespan()),
    ECHOLOCATION("how_whales_use_echolocation", new HowWhalesUseEcholocation());



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
