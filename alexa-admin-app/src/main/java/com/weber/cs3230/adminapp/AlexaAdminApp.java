package com.weber.cs3230.adminapp;

import com.weber.cs3230.adminapp.dialogs.LoginDialogue;

import javax.swing.*;

public class AlexaAdminApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginDialogue loginDialogue = new LoginDialogue();
            loginDialogue.setVisible(true);

            if (loginDialogue.isLoggedIn()) {
                new MainFrame();
            } else {
                System.exit(0);
            }
        });
    }

}
