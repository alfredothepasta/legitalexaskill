package com.weber.cs3230;

public class QuestionLogic {

    public static String answerQuestion(String input){
        String output;

        input.toLowerCase();
        String appendStatement = "\nThank you for your question, please ask another or type \"exit\" to quit.";

        switch (input) {
            // 1
            case "what is your favorite color?": output = "Blue";
            break;

            // 2
            case "what year is it?": output = "At the time of my programming it is 2023, but I'm not smart enough " +
                    "to know more than that, sorry if it has changed.";
            break;

            // 3
            case "why were you written?": output = "I was written to satisfy the needs of a student assignment.";
            break;

            // 4
            case "who wrote you?": output = "The name of the student who wrote me is Alex.";
            break;

            // 5
            case "what is your favorite animal?": output = "I'm a dumb program, but my creator's favorite animal " +
                    "is the humpback whale.";
            break;

            // 6
            case "where in the world is carmen sandiego?": output = "Carmen Sandiego could be anywhere in the world" +
                    " at any given time.";
            break;

            // 7
            case "why aren't you very helpful?": output = "I only have 10 pre-defined questions and answers. If you" +
                    " misspell words or fail to use proper punctuation, or word the question slightly differently, " +
                    "I am unable to answer.";
            break;

            // 8
            case "do you think that this is what the professor had in mind for questions to ask a chatbot?":
                output = "No, somehow I doubt it is.";
            break;

            // 9
            case "can you come up with a question for me?": output = "That isn't in my job description, but here: " +
                    "Why am I incapable of coming up with my own 10 questions for this chatbot assignment?";
            break;

            // 10
            case "why am i incapable of coming up with my own 10 questions for this chatbot assignment?":
                output = "Did you really just use my example? I'm going to assume it's because you aren't very creative.";
            break;

            case "exit": output = "Thank you for using DumbChatBot.";
            return output;


            default: output = "I'm sorry, I can't answer that question.";
            break;
        }


        return  output + appendStatement;

    }
}
