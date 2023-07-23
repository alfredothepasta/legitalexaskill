package com.weber.cs3230.adminapp;

import com.weber.cs3230.adminapp.dialogs.LoginDialog;

import javax.swing.*;

public class AlexaAdminApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginDialog loginDialogue = new LoginDialog();
            loginDialogue.setVisible(true);

            if (loginDialogue.isLoggedIn()) {
                new MainFrame();
            } else {
                System.exit(0);
            }
        });
    }

}
