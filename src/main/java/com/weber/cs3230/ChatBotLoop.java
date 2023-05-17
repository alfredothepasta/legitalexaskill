package com.weber.cs3230;

import java.util.Scanner;

// I had to think up another class, so I just slapped the loop logic in here. I feel it's pointless to not just put this in the main but I'm following the assignment.
public class ChatBotLoop {
    public static void programLoop(){
        Scanner input = new Scanner(System.in);
        String question = "";

        // while the question doesn't
        while(!question.equals("exit")){
            question = input.nextLine();
            question.toLowerCase();
            System.out.println(QuestionLogic.answerQuestion(question));
        }
    }
}
