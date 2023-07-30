package com.weber.cs3230.adminapp;

import com.weber.cs3230.adminapp.dataItems.AnswerDummyData;
import com.weber.cs3230.adminapp.dataItems.IntentTableItem;
import com.weber.cs3230.adminapp.dialogs.AddEditDialog;
import com.weber.cs3230.adminapp.dialogs.AnswersDialog;
import com.weber.cs3230.adminapp.dto.IntentDetail;
import com.weber.cs3230.adminapp.dto.IntentDetailList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class MainPanel extends JPanel {

    private final java.util.List<IntentTableItem> intentTableItems = new ArrayList<>();
    DefaultTableModel tableModel;
    String[] columnNames = {"Intent", "Date Added"};
    JTable table;

    private final ApplicationController applicationController;


    private final Map<String, ArrayList<String>> answers = new AnswerDummyData().getAnswerList();

    public MainPanel(ApplicationController applicationController) {
        super(new BorderLayout());
        this.applicationController = applicationController;
        createMainPanel();
    }

    private void createMainPanel() {
        add(new JLabel("Admin Panel"), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JComponent createTablePanel() {

        buildTable();

        tableModel = new DefaultTableModel(getTableData(), columnNames);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        scrollPane.setVisible(true);
        return scrollPane;
    }

    private Object[][] getTableData(){
        java.util.List<Object[]> rows = new ArrayList<>();

        for (IntentTableItem tableItem : intentTableItems) {
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
            resetTimeToLockout();

            AddEditDialog addDialog = new AddEditDialog(true, applicationController);

            addDialog.setVisible(true);

            if(addDialog.isSaveClicked()){
                String enteredIntent = addDialog.getIntentNameEntered().trim();
                if(!intentListContains(enteredIntent)) {
                    /*intentTableItems.add(new IntentTableItem(enteredIntent, new Date().toString()));*/
                    // todo : make a thing that adds to the thing
                    updateTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Intent Already Exists.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        return  button;
    }
    private JButton editRow(){
        JButton button = new JButton("Edit Row");

        button.addActionListener(e -> {
            resetTimeToLockout();
            int row = table.getSelectedRow();
            String intentName = intentTableItems.get(row).getIntentName();

            AddEditDialog editDialog = new AddEditDialog(false, intentName, applicationController);
            editDialog.setVisible(true);

            if(editDialog.isSaveClicked()){
                String enteredIntent = editDialog.getIntentNameEntered();
//                intentTableItems.set(row, new IntentTableItem(enteredIntent, new Date().toString()));
                updateTable();
            }
        });

        return button;
    }

    private JButton deleteRow(){
        JButton deleteButton = new JButton("Delete Row");
        deleteButton.addActionListener(e -> {
            resetTimeToLockout();
            // todo: in a perfect universe where this is going to production, wrap in try/catch
            int row = table.getSelectedRow();
            intentTableItems.remove(row);
            updateTable();
        });
        return  deleteButton;
    }

    private JButton editAnswers(){
        JButton editAnswers = new JButton("Edit Answers");


        editAnswers.addActionListener(e -> {
            // pass in the answers and the selected row intent name
            resetTimeToLockout();
            int row = table.getSelectedRow();
            String intentName = intentTableItems.get(row).getIntentName();


            AnswersDialog answersDialog = new AnswersDialog(intentName, answers, applicationController);
            answersDialog.setVisible(true);

            // get the array list from the answers dialog and put the new updated one into the map
            if(answersDialog.isSaveClicked()) {
                answers.put(intentName, answersDialog.getIntentAnswers());
            }

        });
        return  editAnswers;
    }

    private boolean intentListContains(String intent){
        for (IntentTableItem item: intentTableItems) {
            if (intent.equals(item.getIntentName())) {
                return true;
            }
        }
        return false;
    }

    private void updateTable(){
        tableModel.setDataVector(getTableData(), columnNames);
    }

    private void resetTimeToLockout(){
        applicationController.setStartTime(System.currentTimeMillis());
    }

    private void buildTable(){
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SwingWorker<Object, Object> worker = new SwingWorker<Object, Object>() {

            @Override
            protected IntentDetailList doInBackground() throws Exception {
                return applicationController.makeApiCall().getIntents();
            }

            @Override
            protected void done(){
                try {
                    IntentDetailList list = (IntentDetailList) get();
                    for (IntentDetail intent : list.getIntents())
                    {
                        MainPanel.this.intentTableItems.add(new IntentTableItem(intent.getIntentID(), intent.getName(), intent.getDateAdded()));
                    }
                    updateTable();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
                setCursor(Cursor.getDefaultCursor());
            }
        };

        worker.execute();




    }

}