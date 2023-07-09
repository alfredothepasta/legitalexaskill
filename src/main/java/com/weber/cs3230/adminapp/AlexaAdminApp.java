package com.weber.cs3230.adminapp;

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
        JFrame mainFrame = new JFrame();
        mainFrame.setPreferredSize(new Dimension(500, 700));//200=width, 300=height
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(createMainPanel()); //add JPanel
        mainFrame.pack();
        mainFrame.setVisible(true); //hangs here
    }

    /*private JComponent createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.add(new JLabel("Some label"));
        JButton button = new JButton("What is the best whale?");
        mainPanel.add(button);
        return mainPanel;
    }*/

    private JComponent createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JLabel("Alexa Admin Utility"), BorderLayout.NORTH);
        mainPanel.add(createTablePanel(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        return mainPanel;
    }

    private JComponent createTablePanel() {
        String[] columnNames = {"Intent", "Date Added"};
        Object[][] data = {
                {"Susan", 5000},
                {"Robert", 7000}
        };
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        scrollPane.setVisible(true);
        return scrollPane;
    }


    private JButton createButtonPanel (){
        JButton button = new JButton("text for the button");
        return  button;
    }



}
