package com.weber.cs3230.adminapp;

import com.weber.cs3230.adminapp.dialogs.LoginDialog;
import com.weber.cs3230.adminapp.thread.LockoutThread;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JFrame mainFrame;
    private volatile Long startTime = System.currentTimeMillis();

    public MainFrame(){
        login();
    }

    public void logout(){
        // If I understood this better, we'd also close all dialogs.
        SwingUtilities.invokeLater(()->{
            mainFrame.dispose();
            JOptionPane.showMessageDialog(null, "Please log in again.", "Timeout", JOptionPane.WARNING_MESSAGE);
            login();
        });
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long timeStart){
        startTime = timeStart;
    }

    private void loginSuccessfulInitMainFrame(){
        mainFrame = new JFrame();
        mainFrame.setPreferredSize(new Dimension(500, 700));//200=width, 300=height
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(new MainPanel(this)); //add JPanel
        mainFrame.pack();
        mainFrame.setVisible(true); //hangs here
    }


    private void login(){
        LoginDialog loginDialogue = new LoginDialog();
        loginDialogue.setVisible(true);

        if (!loginDialogue.isLoggedIn()) {
            System.exit(0);
        }

        startTime = System.currentTimeMillis();
        Thread lockout = new LockoutThread(this);
        lockout.start();

        loginSuccessfulInitMainFrame();
    }


}
