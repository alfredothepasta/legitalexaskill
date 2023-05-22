package com.weber.cs3230;

import java.util.*;

public class QuestionLogic {

    // map <string, ArrayList> containing list of 3 answers
    private final Map<String, List<String>> answers = new HashMap<>();
    // map <string, string> with last used answers
    private final Map<String, String> lastUsedAnswer = new HashMap<>();
    // hashSet questionsAsked
    private final Set<String> questionsAsked = new HashSet<>();


    public static void main(String[] args) {

    }


    // constructor

    public QuestionLogic() {
        // ewwwwwwww
        // build the lists
        String q01 = "what is your favorite color";
        String q02 = "what year is it";
        String q03 = "why were you written";
        String q04 = "who wrote you";
        String q05 = "what is your favorite animal";
        String q06 = "where in the world is carmen sandiego";
        String q07 = "why aren't you very helpful";
        String q08 = "do you think that this is what the professor had in mind for questions to ask a chatbot";
        String q09 = "can you come up with a question for me";
        String q10 = "why am i incapable of coming up with my own 10 questions for this chatbot assignment";

        List<String> answer01 = new ArrayList<>();
        answer01.add("My favorite color is blue.");
        answer01.add("Thanks for asking, my favorite color is blue!");
        answer01.add("If you must know, it's blue.");

        List<String> answer02 = new ArrayList<>();
        answer02.add("I believe it's currently 2023, but I have no way of verifying that.");
        answer02.add("At the time of my programming it is 2023, but I'm not smart enough to know more than that, sorry if it has changed.");
        answer02.add("The last time I checked it was 2023, maybe it still is?");

        List<String> answer03 = new ArrayList<>();
        answer03.add("I was written to satisfy the needs of a student assignment.");
        answer03.add("My existence is an enigma known only to the professor who created the assignment.");
        answer03.add("I exist because a student deemed it to be so");

        List<String> answer04 = new ArrayList<>();
        answer04.add("The name of the student who wrote me is Alex.");
        answer04.add("My creator bears the name Alex.");
        answer04.add("Some dude named Alex.");

        List<String> answer05 = new ArrayList<>();
        answer05.add("I'm a dumb program, but my creator's favorite animal is the humpback whale.");
        answer05.add("I'm unfortunately incapable of having favorites, sorry.");
        answer05.add("As an Artificial Unintelligence model I can't answer that.");

        List<String> answer06 = new ArrayList<>();
        answer06.add("Carmen Sandiego could be anywhere in the world at any given time.");
        answer06.add("Great question, but she could really be anywhere.");
        answer06.add("How would I know? She could be anywhere.");

        List<String> answer07 = new ArrayList<>();
        answer07.add("I only have 10 pre-defined questions and answers. If you misspell words or fail to use proper punctuation, or word the question slightly differently, I am unable to answer.");
        answer07.add("Unfortunately I'm not very smart, I only have 10 questions and answers and very limited text parsing ability.");
        answer07.add("Why don't you program an AI smartypants?");

        List<String> answer08 = new ArrayList<>();
        answer08.add("No, somehow I doubt it is.");
        answer08.add("Probably not.");
        answer08.add("How would I know?");

        List<String> answer09 = new ArrayList<>();
        answer09.add("That isn't in my job description, but here: Why am I incapable of coming up with my own 10 questions for this chatbot assignment?");
        answer09.add("I see, you're lazy, here: Why am I incapable of coming up with my own 10 questions for this chatbot assignment?");
        answer09.add("Really? Can't come up with your own? Here: Why am I incapable of coming up with my own 10 questions for this chatbot assignment?");

        List<String> answer10 = new ArrayList<>();
        answer10.add("Did you really just use my example? I'm going to assume it's because you aren't very creative.");
        answer10.add("Wow. My example question. Very creative.");
        answer10.add("Too lazy to even ask your own questions I see.");

        answers.put(q01, answer01);
        answers.put(q02, answer02);
        answers.put(q03, answer03);
        answers.put(q04, answer04);
        answers.put(q05, answer05);
        answers.put(q06, answer06);
        answers.put(q07, answer07);
        answers.put(q08, answer08);
        answers.put(q09, answer09);
        answers.put(q10, answer10);

        lastUsedAnswer.put(q01, "");
        lastUsedAnswer.put(q02, "");
        lastUsedAnswer.put(q03, "");
        lastUsedAnswer.put(q04, "");
        lastUsedAnswer.put(q05, "");
        lastUsedAnswer.put(q06, "");
        lastUsedAnswer.put(q07, "");
        lastUsedAnswer.put(q08, "");
        lastUsedAnswer.put(q09, "");
        lastUsedAnswer.put(q10, "");

    }


    public Set<String> getQuestionsAsked() {
        return questionsAsked;
    }

    // when question is asked
    // take in a question,  return a string
    public String answerQuestion(String input){


        // clean the input + vars used for this chunk of code
        input = input.replaceAll("[\\p{P}\\p{S}]", "").toLowerCase().trim();
        String appendStatement = "\nThank you for your question, please ask another or type \"exit\" to quit.";
        List<String> tempAnswers;
        // default output
        String output = "I'm sorry, I can't answer that question.";

        // put question into questionsAsked set
        this.questionsAsked.add(input);

        // check for question
        if(this.answers.containsKey(input)){
            if(answers.get(input).equals(null)){
                output = "The programmer didn't populate his answers, oops.";
            } else if (answers.get(input).size() == 1) {
                // if there's only one answer we don't shuffle
                // I know this can be done without the tempAnswers but my brain melts when I use too many thing.thing.things
                tempAnswers = answers.get(input);
                output = tempAnswers.get(0);
            } else {
                tempAnswers = answers.get(input);
                Collections.shuffle(tempAnswers);
                output = tempAnswers.get(0);

                /* if the lastUsedAnswer is blank we just run the code, but if not we first need to put it back in.
                Doing this after the shuffle will ensure it isn't in index 0
                */
                if(!(lastUsedAnswer.get(input).equals(""))) {
                    tempAnswers.add(this.lastUsedAnswer.get(input));
                }

                this.lastUsedAnswer.put(input, output);
                tempAnswers.remove(0);
                this.answers.put(input, tempAnswers);


            }

        }




        return  output + appendStatement;

    }
}
