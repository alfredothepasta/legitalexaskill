package com.weber.cs3230.adminapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainPanel extends JPanel {

    public MainPanel() {
        super(new BorderLayout());
        add(new JLabel("Admin Panel"), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JComponent createTablePanel() {
        String[] columnNames = {"Intent", "Date Added"};
        Object[][] data = {
                {"largest_whale_species", "7-9-2023"},
                {"whale_communication", "7-9-2023"},
                {"known_for_displays", "7-9-2023"},
                {"blue_whale_diet", "7-9-2023"},
                {"toothed_vs_baleen", "7-9-2023"},
                {"humpback_song_length", "7-9-2023"},
                {"purpose_of_blowhole", "7-9-2023"},
                {"how_they_migrate", "7-9-2023"},
                {"average_whale_lifespan", "7-9-2023"},
                {"how_whales_use_echolocation", "7-9-2023"}
        };
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        scrollPane.setVisible(true);
        return scrollPane;
    }

    private JPanel createButtonPanel (){
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(addNewRow());
        buttonPanel.add(deleteRow());
        buttonPanel.add(editRow());
        return buttonPanel;

    }

    private JButton addNewRow(){
        JButton button = new JButton("Add Row");
        return  button;
    }

    private JButton deleteRow(){
        JButton button = new JButton("Delete Row");
        return  button;
    }

    private JButton editRow(){
        JButton button = new JButton("Edit Row");
        return button;
    }
}
