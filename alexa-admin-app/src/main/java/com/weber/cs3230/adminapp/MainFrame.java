package com.weber.cs3230.adminapp;

import com.weber.cs3230.adminapp.dialogs.LoginDialog;
import com.weber.cs3230.adminapp.dto.ApiClient;
import com.weber.cs3230.adminapp.thread.LockoutThread;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JFrame mainFrame;


    private ApplicationController applicationController;



    public MainFrame(){
        this.applicationController = new ApplicationController(this);
        applicationController.login();
    }

    public void loginSuccessfulInitMainFrame(){
        mainFrame = new JFrame();
        mainFrame.setPreferredSize(new Dimension(500, 700));//200=width, 300=height
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(new MainPanel(applicationController)); //add JPanel
        mainFrame.pack();
        mainFrame.setVisible(true); //hangs here
    }

    public void die(){
        mainFrame.dispose();
    }

}
