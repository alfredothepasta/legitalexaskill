package com.weber.cs3230;

import java.util.Scanner;

// I had to think up another class, so I just slapped the loop logic in here. I feel it's pointless to not just put this in the main but I'm following the assignment.
public class ChatBotLoop {
    private final QuestionLogic questionLogic;

    public ChatBotLoop(){
        this.questionLogic = new QuestionLogic();
    }

    public void programLoop(){
        System.out.println("Hey there, ask me a question! Or don't. You can type \"exit\" to close me.");
        Scanner input = new Scanner(System.in);
        String question = "";

        while(true){
            question = input.nextLine();
            if (question.equals("exit")) break;
            System.out.println(this.questionLogic.answerQuestion(question));

        }

        System.out.println("You asked the following questions:");
        for (String questionAsked : this.questionLogic.getQuestionsAsked()) {
            System.out.println(questionAsked);
        }

        System.out.println("Thank you for using dumb chat bot.");
    }
}
