package com.weber.cs3230.adminapp;

import com.weber.cs3230.adminapp.dialogs.LoginDialog;
import com.weber.cs3230.adminapp.dto.ApiClient;
import com.weber.cs3230.adminapp.thread.LockoutThread;

import javax.swing.*;

public class ApplicationController {

    private final ApiClient apiClient;

    private final MainFrame mainFrame;

    private volatile Long startTime = System.currentTimeMillis();
    public ApplicationController(MainFrame mainFrame) {
        this.apiClient = new ApiClient();
        this.mainFrame = mainFrame;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long timeStart){
        startTime = timeStart;
    }




    public void login(){
        LoginDialog loginDialogue = new LoginDialog(this);
        loginDialogue.setVisible(true);

        if (!loginDialogue.isLoggedIn()) {
            System.exit(0);
        }

        startTime = System.currentTimeMillis();
        Thread lockout = new LockoutThread(this);
        lockout.start();

        mainFrame.loginSuccessfulInitMainFrame();
    }

    public void logout(){
        // If I understood this better, we'd also close all dialogs.
        SwingUtilities.invokeLater(()->{
            mainFrame.dispose();
            JOptionPane.showMessageDialog(null, "Please log in again.", "Timeout", JOptionPane.WARNING_MESSAGE);
            login();
        });
    }

    public ApiClient getApiClient() {
        return apiClient;
    }
}
