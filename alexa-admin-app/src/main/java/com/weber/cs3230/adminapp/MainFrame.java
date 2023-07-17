package com.weber.cs3230.adminapp;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(){

        JFrame mainFrame = new JFrame();
        mainFrame.setPreferredSize(new Dimension(500, 700));//200=width, 300=height
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(new MainPanel()); //add JPanel
        mainFrame.pack();
        mainFrame.setVisible(true); //hangs here
    }
}
