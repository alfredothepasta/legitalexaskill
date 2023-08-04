package com.weber.cs3230.adminapp;

import com.weber.cs3230.adminapp.dialogs.LoginDialog;
import com.weber.cs3230.adminapp.dto.ApiClient;
import com.weber.cs3230.adminapp.dto.IntentDetailList;
import com.weber.cs3230.adminapp.thread.LockoutThread;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

public class MainFrame extends JFrame {

    private JFrame mainFrame;


    private ApplicationController applicationController;



    public MainFrame(){
        this.applicationController = new ApplicationController(this);
        applicationController.login();
    }

    public void loginSuccessfulInitMainFrame(){
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SwingWorker<Object, Object> worker = new SwingWorker<Object, Object>() {
            @Override
            protected IntentDetailList doInBackground() throws Exception {
                return applicationController.makeApiCall().getIntents();
            }

            @Override
            protected void done(){
                try {
                    IntentDetailList intentDetailList = (IntentDetailList) get();
                    mainFrame = new JFrame();
                    mainFrame.setPreferredSize(new Dimension(500, 700));//200=width, 300=height
                    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    mainFrame.add(new MainPanel(applicationController, intentDetailList)); //add JPanel
                    mainFrame.pack();
                    mainFrame.setVisible(true);
                    setCursor(Cursor.getDefaultCursor());
                } catch (Exception e) {

                }


            }
        };
        worker.execute();
    }

    public void die(){
        mainFrame.dispose();
    }

}
