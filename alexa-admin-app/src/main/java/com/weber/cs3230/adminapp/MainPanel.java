package com.weber.cs3230.adminapp;

import com.weber.cs3230.adminapp.dataItems.AnswerDummyData;
import com.weber.cs3230.adminapp.dialogs.AddEditDialog;
import com.weber.cs3230.adminapp.dialogs.AnswersDialog;
import com.weber.cs3230.adminapp.dto.IntentDetail;
import com.weber.cs3230.adminapp.dto.IntentDetailList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class MainPanel extends JPanel {

    private IntentDetailList intentDetailList;
    DefaultTableModel tableModel;
    String[] columnNames = {"Intent", "Date Added"};
    JTable table;

    private final ApplicationController applicationController;


    private final Map<String, ArrayList<String>> answers = new AnswerDummyData().getAnswerList();

    public MainPanel(ApplicationController applicationController, IntentDetailList intentDetailList) {
        super(new BorderLayout());
        this.applicationController = applicationController;
        this.intentDetailList = intentDetailList;
        createMainPanel();
    }

    private void createMainPanel() {
        add(new JLabel("Admin Panel"), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JComponent createTablePanel() {
//        JScrollPane scrollPane = new JScrollPane();

        tableModel = new DefaultTableModel(getTableData(), columnNames);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        scrollPane.setVisible(true);
        return scrollPane;

    }

    private Object[][] getTableData(){
        java.util.List<Object[]> rows = new ArrayList<>();

        for (IntentDetail tableItem : intentDetailList.getIntents()) {
            rows.add(new Object[]{tableItem.getName(), tableItem.getDateAdded()});
        }


        return rows.toArray(new Object[0][0]);
    }

    private JPanel createButtonPanel (){
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        buttonPanel.add(addIntent());
        buttonPanel.add(editRow());
        buttonPanel.add(deleteIntent());
        buttonPanel.add(editAnswers());
        return buttonPanel;

    }

    private JButton addIntent(){
        JButton button = new JButton("Add Intent");
        button.addActionListener(e -> {
            resetTimeToLockout();

            AddEditDialog addDialog = new AddEditDialog(true, applicationController);
            addDialog.setVisible(true);

            if(addDialog.isSaveClicked()){
                String enteredIntent = addDialog.getIntentNameEntered().trim();
                if(!intentListContains(enteredIntent)) {
                    /*intentTableItems.add(new IntentTableItem(enteredIntent, new Date().toString()));*/
                    // todo : make a thing that adds to the thing
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    SwingWorker<Object, Object> worker = new SwingWorker<Object, Object>() {
                        @Override
                        protected Object doInBackground() throws Exception {
                            applicationController.makeApiCall().saveNewIntent(enteredIntent);
                            updateTableData();
                            return null;
                        }

                        @Override
                        protected void done(){
//                            updateTable();
                            setCursor(Cursor.getDefaultCursor());
                        }
                    };
                    worker.execute();
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
            String intentName = intentDetailList.getIntents().get(row).getName();

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

    private JButton deleteIntent(){
        JButton deleteButton = new JButton("Delete Intent");
        deleteButton.addActionListener(e -> {
            resetTimeToLockout();
            int row = table.getSelectedRow();
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            SwingWorker<Object, Object> worker = new SwingWorker<Object, Object>() {
                @Override
                protected Object doInBackground() throws Exception {
                    applicationController.makeApiCall().deleteIntent(intentDetailList.getIntents().get(row).getIntentID());
                    return null;
                }
                @Override
                protected void done(){
                    updateTableData();
                    setCursor(Cursor.getDefaultCursor());
//                    updateTable();
                }
            };

            worker.execute();

            // todo: in a perfect universe where this is going to production, wrap in try/catch

//            intentTableItems.remove(row);
        });
        return  deleteButton;
    }

    private JButton editAnswers(){
        JButton editAnswers = new JButton("Edit Answers");


        editAnswers.addActionListener(e -> {
            // pass in the answers and the selected row intent name
            resetTimeToLockout();
            int row = table.getSelectedRow();
            String intentName = intentDetailList.getIntents().get(row).getName();


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
        for (IntentDetail item: intentDetailList.getIntents()) {
            if (intent.equals(item.getName())) {
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

    private void updateTableData(){

        SwingWorker<Object, Object> worker = new SwingWorker<Object, Object>() {

            @Override
            protected IntentDetailList doInBackground() throws Exception {
                return applicationController.makeApiCall().getIntents();
            }

            @Override
            protected void done(){
                try {
                    intentDetailList = (IntentDetailList) get();
                    tableModel.setDataVector(getTableData(), columnNames);
                } catch (Exception e){
                    // todo: Inform the user tat we failed
                }

            }
        };

        worker.execute();
    }

}