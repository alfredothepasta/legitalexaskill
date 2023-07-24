package com.weber.cs3230.adminapp;

import com.weber.cs3230.adminapp.dialogs.LoginDialog;
import com.weber.cs3230.adminapp.thread.LockoutThread;

import javax.swing.*;

public class AlexaAdminApp {



    public static void main(String[] args) {


        SwingUtilities.invokeLater(() -> {

            new MainFrame();

        });
    }

}
