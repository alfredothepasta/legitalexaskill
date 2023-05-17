package com.weber.cs3230;

import java.util.Scanner;

public class ChatBotMain {

    public static void main(String[] args) {
        System.out.println("Hey there, ask me a question! Or don't. You can type \"exit\" to close me.");

        ChatBotLoop.programLoop();
        /*
        System.out.println("What's your question, good sir?");

        Scanner input = new Scanner(System.in);
        String question = input.nextLine();

        System.out.println("Looks like here's your question: " + question);
         */

    }



}
