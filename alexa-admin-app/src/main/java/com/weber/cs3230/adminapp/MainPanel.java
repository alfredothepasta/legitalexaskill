package com.weber.cs3230.adminapp;

import com.weber.cs3230.adminapp.dataItems.AnswerDummyData;
import com.weber.cs3230.adminapp.dialogs.AddEditDialog;
import com.weber.cs3230.adminapp.dialogs.AnswersDialog;
import com.weber.cs3230.adminapp.dialogs.LoginDialog;
import com.weber.cs3230.adminapp.dto.IntentAnswer;
import com.weber.cs3230.adminapp.dto.IntentAnswerList;
import com.weber.cs3230.adminapp.dto.IntentDetail;
import com.weber.cs3230.adminapp.dto.IntentDetailList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainPanel extends JPanel {

    private IntentDetailList intentDetailList;
    DefaultTableModel tableModel;
    String[] columnNames = {"Intent", "Date Added"};
    JTable table;

    private final ApplicationController applicationController;

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
        buttonPanel.add(editIntent());
        buttonPanel.add(deleteIntent());
        buttonPanel.add(editAnswers());
        return buttonPanel;

    }

    private JButton addIntent(){
        JButton button = new JButton("Add Intent");
        button.addActionListener(e -> {
            applicationController.resetTimeToLockout();

            AddEditDialog addDialog = new AddEditDialog(true, applicationController);
            addDialog.setVisible(true);

            if(addDialog.isSaveClicked()){
                String enteredIntent = addDialog.getIntentNameEntered().trim();
                if(!intentListContains(enteredIntent)) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    SwingWorker<Object, Object> worker = new SwingWorker<Object, Object>() {
                        @Override
                        protected Object doInBackground() throws Exception {
                            try {
                                applicationController.makeApiCall().saveNewIntent(enteredIntent);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            return null;
                        }

                        @Override
                        protected void done(){
                            updateTableData();
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
    private JButton editIntent(){
        JButton button = new JButton("Edit Intent");

        button.addActionListener(e -> {
            applicationController.resetTimeToLockout();
            int row = table.getSelectedRow();
            String intentName = intentDetailList.getIntents().get(row).getName();

            AddEditDialog editDialog = new AddEditDialog(false, intentName, applicationController);
            editDialog.setVisible(true);

            if(editDialog.isSaveClicked()){
                String enteredIntent = editDialog.getIntentNameEntered();
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                SwingWorker<Object, Object> worker = new SwingWorker<Object, Object>() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        applicationController.makeApiCall().updateIntent(intentDetailList.getIntents().get(row).getIntentID(), enteredIntent);
                        return null;
                    }

                    @Override
                    protected void done(){
                        updateTableData();
                        setCursor(Cursor.getDefaultCursor());
                    }
                };

                worker.execute();
            }
        });

        return button;
    }

    private JButton deleteIntent(){
        JButton deleteButton = new JButton("Delete Intent");
        deleteButton.addActionListener(e -> {
            applicationController.resetTimeToLockout();
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
                }
            };

            worker.execute();

        });
        return  deleteButton;
    }

    private JButton editAnswers(){
        JButton editAnswers = new JButton("Edit Answers");




        editAnswers.addActionListener(e -> {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            // pass in the answers and the selected row intent name

            applicationController.resetTimeToLockout();
            int row = table.getSelectedRow();
            String intentName = intentDetailList.getIntents().get(row).getName();
            long intentID = intentDetailList.getIntents().get(row).getIntentID();

            SwingWorker<Object, Object> worker = new SwingWorker<Object, Object>() {
                @Override
                protected IntentAnswerList doInBackground() throws Exception {
                    IntentAnswerList answerList = applicationController.makeApiCall().getAnswers(
                            intentDetailList.getIntents().get(row).getIntentID()
                    );
                    return answerList;
                }

                @Override
                protected void done() {
                    try {
                        IntentAnswerList answerList = (IntentAnswerList) get();
                        AnswersDialog answersDialog = new AnswersDialog(intentName, intentID, answerList, applicationController);
                        answersDialog.setVisible(true);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());


                    }
                    setCursor(Cursor.getDefaultCursor());
                }
            };

            worker.execute();

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
                    System.out.println(e.getMessage());
                    JOptionPane.showMessageDialog(MainPanel.this, "A Network Error Occurred.", "Warning", JOptionPane.WARNING_MESSAGE);
                }

            }
        };

        worker.execute();
    }

}