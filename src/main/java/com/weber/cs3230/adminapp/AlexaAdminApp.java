package com.weber.cs3230.adminapp;

import javax.swing.*;
import java.awt.*;

public class AlexaAdminApp {

    public static void main(String[] args) {
        //TODO make a Swing application
        SwingUtilities.invokeLater(() -> {
                    new AlexaAdminApp().showMainFrame();
                });
    }

    private void showMainFrame() {
        JFrame mainFrame = new JFrame();
        mainFrame.setPreferredSize(new Dimension(200, 300));//200=width, 300=height
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(createMainPanel()); //add JPanel
        mainFrame.pack();
        mainFrame.setVisible(true); //hangs here
    }

    private JComponent createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.add(new JLabel("Some label"));
        return mainPanel;
    }



}
