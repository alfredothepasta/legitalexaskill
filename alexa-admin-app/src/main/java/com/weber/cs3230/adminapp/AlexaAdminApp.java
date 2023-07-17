package com.weber.cs3230.adminapp;

import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AlexaAdminApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame();

        });
        //TODO make a Swing application
//        SwingUtilities.invokeLater(() -> {
//            LoginDialogue loginDialogue = new LoginDialogue();
//            loginDialogue.setVisible(true);
//
//            if (loginDialogue.isLoggedIn()) {
//                new MainFrame();
//            } else {
//                System.exit(0);
//            }
//        });
    }

}
