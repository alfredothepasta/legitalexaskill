package com.weber.cs3230.adminapp.dialogs;

import com.weber.cs3230.adminapp.ApplicationController;
import com.weber.cs3230.adminapp.dto.IntentAnswer;
import com.weber.cs3230.adminapp.dto.IntentAnswerList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

// well that was a nightmare to make. I'm sorry you have to try to read this.
// Everything works now and I honestly have no idea how it works and I wrote it.
public class AnswersDialog extends JDialog {



    private IntentAnswerList intentAnswerList;
    private final String currentIntent;

    private final long intentID;

    private JTable table;
    private DefaultTableModel tableModel;

    private JComponent buttonPanel = buttonPanel = new JPanel(new GridLayout(1, 5));;

    private JTextField editTextField;

    private final String[] columnNames = {"Answer"};

    private final ApplicationController applicationController;


    public AnswersDialog(String currentIntent, long intentID, IntentAnswerList intentAnswerList, ApplicationController applicationController){
        this.currentIntent = currentIntent;
        this.intentID = intentID;
        this.applicationController = applicationController;
        this.intentAnswerList = intentAnswerList;
        setPreferredSize(new Dimension(700,  200));
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        add(getAnswersDialog());
        pack();
    }

    private JComponent getAnswersDialog(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Viewing answers for " + currentIntent), BorderLayout.NORTH);
        panel.add(createTablePanel(), BorderLayout.CENTER);
        panel.add(createEditPanel(), BorderLayout.SOUTH);
        return panel;
    }

    private JComponent createTablePanel() {
        tableModel = new DefaultTableModel(getTableData(), columnNames);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        scrollPane.setVisible(true);
        return scrollPane;
    }

    private JComponent createEditPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        editTextField = new JTextField();
        panel.add(editTextField, BorderLayout.CENTER);
        createStandardButtonPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void createStandardButtonPanel(){
        buttonPanel.removeAll();
        buttonPanel.add(addButton());
        buttonPanel.add(editButton());
        buttonPanel.add(deleteButton());
        buttonPanel.add(closeButton());
        buttonPanel.revalidate();
    }

    private void createEditButtonPanel(){
        buttonPanel.removeAll();
        buttonPanel.add(editSave());
        buttonPanel.add(editCancel());
        buttonPanel.revalidate();
    }

    private JButton addButton(){
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            applicationController.resetTimeToLockout();

            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(!editTextField.getText().trim().equals("")) {
                SwingWorker<Boolean, Object> worker = new SwingWorker<>() {
                    @Override
                    protected Boolean doInBackground() throws Exception {
                        applicationController.makeApiCall().saveNewAnswer(intentID, editTextField.getText());

                        return true;
                    }

                    @Override
                    protected void done() {
                        try {
                            if(!get()){
                                JOptionPane.showMessageDialog(AnswersDialog.this, "Abandon Hope All Ye Who Enter Here", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            JOptionPane.showMessageDialog(AnswersDialog.this, "Something went wrong: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }

                        editTextField.setText("");
                        updateTable();
                        setCursor(Cursor.getDefaultCursor());
                    }
                };

                worker.execute();
            }
        });

        return addButton;
    }

    private JButton closeButton() {
        applicationController.resetTimeToLockout();

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
        return closeButton;
    }


    private JButton editButton(){
        applicationController.resetTimeToLockout();

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(e -> {

            // replace button panel with the edit button panel
            if(table.isColumnSelected(0)) {
                getSelectedRow();
                createEditButtonPanel();
                table.setEnabled(false);
                editTextField.setText(intentAnswerList.getAnswers().get(getSelectedRow()).getText());
            }
        });

        return editButton;
    }

    private JButton deleteButton(){
        applicationController.resetTimeToLockout();

        JButton deleteButton = new JButton("Delete");

        deleteButton.addActionListener(e -> {

            int row = table.getSelectedRow();
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            SwingWorker<Boolean, Object> worker = new SwingWorker<>() {
                @Override
                protected Boolean doInBackground() throws Exception {
                    applicationController.makeApiCall().deleteAnswer(intentID, intentAnswerList.getAnswers().get(row).getAnswerID());

                    return true;
                }

                @Override
                protected void done() {
                    try {
                        if(!get()){
                            JOptionPane.showMessageDialog(AnswersDialog.this, "Abandon Hope All Ye Who Enter Here", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        JOptionPane.showMessageDialog(AnswersDialog.this, "Something went wrong: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }


                    updateTable();
                    setCursor(Cursor.getDefaultCursor());
                }
            };
            worker.execute();

        });

        return deleteButton;
    }

    private Object[][] getTableData(){
        java.util.List<Object[]> rows = new ArrayList<>();

        for (IntentAnswer answer : intentAnswerList.getAnswers()) {
            rows.add(new Object[]{answer.getText()});
        }
        return rows.toArray(new Object[0][0]);
    }


    private void updateTable(){

        setCursor(Cursor.getDefaultCursor());
        SwingWorker<IntentAnswerList, Object> worker = new SwingWorker<>() {



            @Override
            protected IntentAnswerList doInBackground() throws Exception {
                return applicationController.makeApiCall().getAnswers(intentID);
            }

            @Override
            protected void done(){
                try {
                    intentAnswerList = get();
                    tableModel.setDataVector(getTableData(), columnNames);
                } catch (Exception e){
                    System.out.println(e.getMessage());
                    JOptionPane.showMessageDialog(AnswersDialog.this, "A Network Error Occurred.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
                setCursor(Cursor.getDefaultCursor());

            }
        };

        worker.execute();

    }

    private JButton editSave(){
        JButton editSave = new JButton("Save");
        editSave.addActionListener(e -> {
            applicationController.resetTimeToLockout();

            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            SwingWorker<Boolean, Object> worker = new SwingWorker<>() {
                @Override
                protected Boolean doInBackground() throws Exception {
                    applicationController.makeApiCall().updateAnswer(intentID, intentAnswerList.getAnswers().get(getSelectedRow()).getAnswerID(), editTextField.getText());
                    return true;
                }

                @Override
                protected void done() {
                    try {
                        if(!get()){
                            JOptionPane.showMessageDialog(AnswersDialog.this, "Abandon Hope All Ye Who Enter Here", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        JOptionPane.showMessageDialog(AnswersDialog.this, "Something went wrong: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    super.done();
                    editTextField.setText("");
                    table.setEnabled(true);
                    updateTable();
                    createStandardButtonPanel();
                    setCursor(Cursor.getDefaultCursor());
                }
            };

            worker.execute();

        });

        return editSave;
    }

    private JButton editCancel(){
        applicationController.resetTimeToLockout();

        JButton editCancel = new JButton("Cancel");
        editCancel.addActionListener(e->{
            editTextField.setText("");
            table.setEnabled(true);
            createStandardButtonPanel();
        });


        return editCancel;
    }

    private int getSelectedRow(){
        int row = table.getSelectedRow();
        return row;
    }


}
