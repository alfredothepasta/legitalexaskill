package com.weber.cs3230.adminapp;

import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AlexaAdminApp {

    public static void main(String[] args) {
        //TODO make a Swing application
        SwingUtilities.invokeLater(() -> {
                    new AlexaAdminApp().showMainFrame();
                });
    }

    private void showMainFrame() {
        // show the login screen first
        LoginDialogue loginDialogue = new LoginDialogue();
        loginDialogue.setVisible(true);

        JFrame mainFrame = new JFrame();
        mainFrame.setPreferredSize(new Dimension(500, 700));//200=width, 300=height
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(new MainPanel()); //add JPanel
        mainFrame.pack();
        mainFrame.setVisible(true); //hangs here
    }

}
