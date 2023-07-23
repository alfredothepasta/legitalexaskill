package com.weber.cs3230.adminapp;

import com.weber.cs3230.adminapp.dataItems.AnswerDummyData;
import com.weber.cs3230.adminapp.dataItems.IntentTableItem;
import com.weber.cs3230.adminapp.dialogs.AddEditDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Date;

public class MainPanel extends JPanel {

    private final java.util.List<IntentTableItem> intentItems = new ArrayList<>();
    DefaultTableModel tableModel;
    String[] columnNames = {"Intent", "Date Added"};
    JTable table;

    private final Map<String, ArrayList<String>> answers = new AnswerDummyData().getAnswerList();

    public MainPanel() {
        super(new BorderLayout());
        createMainPanel();
    }

    private void createMainPanel() {
        add(new JLabel("Admin Panel"), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JComponent createTablePanel() {

        intentItems.add(new IntentTableItem( "largest_whale_species", "7-9-2023"));
        intentItems.add(new IntentTableItem( "whale_communication", "7-9-2023"));
        intentItems.add(new IntentTableItem( "known_for_displays", "7-9-2023"));
        intentItems.add(new IntentTableItem( "blue_whale_diet", "7-9-2023"));
        intentItems.add(new IntentTableItem( "toothed_vs_baleen", "7-9-2023"));
        intentItems.add(new IntentTableItem( "humpback_song_length", "7-9-2023"));
        intentItems.add(new IntentTableItem( "purpose_of_blowhole", "7-9-2023"));
        intentItems.add(new IntentTableItem( "how_they_migrate", "7-9-2023"));
        intentItems.add(new IntentTableItem( "average_whale_lifespan", "7-9-2023"));
        intentItems.add(new IntentTableItem( "how_whales_use_echolocation", "7-9-2023"));

        tableModel = new DefaultTableModel(getTableData(), columnNames);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        scrollPane.setVisible(true);
        return scrollPane;
    }

    private Object[][] getTableData(){
        java.util.List<Object[]> rows = new ArrayList<>();

        for (IntentTableItem tableItem : intentItems) {
            rows.add(new Object[]{tableItem.getIntentName(), tableItem.getDateAdded()});
        }


        return rows.toArray(new Object[0][0]);
    }

    private JPanel createButtonPanel (){
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        buttonPanel.add(addNewRow());
        buttonPanel.add(editRow());
        buttonPanel.add(deleteRow());
        buttonPanel.add(editAnswers());
        return buttonPanel;

    }

    private JButton addNewRow(){
        JButton button = new JButton("Add Row");
        button.addActionListener(e -> {

            AddEditDialog addDialog = new AddEditDialog(true);

            addDialog.setVisible(true);

            if(addDialog.isSaveClicked()){
                String enteredIntent = addDialog.getIntentNameEntered();
                intentItems.add(new IntentTableItem(enteredIntent, new Date().toString()));
                updateTable();
            }
        });
        return  button;
    }
    private JButton editRow(){
        JButton button = new JButton("Edit Row");

        button.addActionListener(e -> {
            int row = table.getSelectedRow();
            String intentName = intentItems.get(row).getIntentName();

            AddEditDialog editDialog = new AddEditDialog(false, intentName);
            editDialog.setVisible(true);

            if(editDialog.isSaveClicked()){
                String enteredIntent = editDialog.getIntentNameEntered();
                intentItems.set(row, new IntentTableItem(enteredIntent, new Date().toString()));
                updateTable();
            }
        });

        return button;
    }

    private JButton deleteRow(){
        JButton deleteButton = new JButton("Delete Row");
        deleteButton.addActionListener(e -> {
            // todo: in a perfect universe where this is going to production, wrap in try/catch
            int row = table.getSelectedRow();
            intentItems.remove(row);
            updateTable();
        });
        return  deleteButton;
    }

    private JButton editAnswers(){
        JButton editAnswers = new JButton("Edit Answers");


        editAnswers.addActionListener(e -> {
            // pass in the answers and the selected row intent name
            int row = table.getSelectedRow();
            String intentName = intentItems.get(row).getIntentName();

            // todo edit answers opens answers dialogue

        });
        return  editAnswers;
    }

    private void updateTable(){
        tableModel.setDataVector(getTableData(), columnNames);
    }

}
