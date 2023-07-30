package com.weber.cs3230.adminapp.dialogs;

import com.weber.cs3230.adminapp.ApplicationController;
import com.weber.cs3230.adminapp.dto.IntentAnswer;
import com.weber.cs3230.adminapp.dto.IntentAnswerList;
import com.weber.cs3230.adminapp.dto.IntentDetailList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

// well that was a nightmare to make. I'm sorry you have to try to read this.
// Everything works now and I honestly have no idea how it works and I wrote it.
public class AnswersDialog extends JDialog {


//    private final ArrayList<String> intentAnswers;

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
//        buttonPanel = new JPanel(new GridLayout(1, 5));
        buttonPanel.removeAll();
        buttonPanel.add(addButton());
        buttonPanel.add(editButton());
        buttonPanel.add(deleteButton());
        buttonPanel.add(closeButton());
        buttonPanel.revalidate();
    }

    private void createEditButtonPanel(){
//        buttonPanel = new JPanel(new GridLayout(1, 5));
        buttonPanel.removeAll();
        buttonPanel.add(editSave());
        buttonPanel.add(editCancel());
        buttonPanel.revalidate();
    }

    private JButton addButton(){
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(!editTextField.getText().trim().equals("")) {
                SwingWorker<Object, Object> worker = new SwingWorker<Object, Object>() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        // add a new answer
                        applicationController.makeApiCall().saveNewAnswer(intentID, editTextField.getText());

                        return null;
                    }

                    @Override
                    protected void done() {
                        super.done();
                        // todo do update table things
                        editTextField.setText("");
                        updateTable();
                        setCursor(Cursor.getDefaultCursor());
                    }
                };

                worker.execute();
//                intentAnswerList.getAnswers().add(editTextField.getText());
//                editTextField.setText("");
//                updateTable();
            }
        });

        return addButton;
    }

    private JButton closeButton() {
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {

            // update the array of answers
//            saveClicked = true;

            setVisible(false);
            dispose();
        });
        return closeButton;
    }


    private JButton editButton(){
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
        JButton deleteButton = new JButton("Delete");

        deleteButton.addActionListener(e -> {
            // todo: in a perfect universe where this is going to production, wrap in try/catch
            int row = table.getSelectedRow();
            // todo actually do this in the apiclient
            intentAnswerList.getAnswers().remove(row);
            updateTable();
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
        SwingWorker<Object, Object> worker = new SwingWorker<Object, Object>() {

            @Override
            protected IntentAnswerList doInBackground() throws Exception {
                return applicationController.makeApiCall().getAnswers(intentID);
            }

            @Override
            protected void done(){
                try {
                    intentAnswerList = (IntentAnswerList) get();
                    tableModel.setDataVector(getTableData(), columnNames);
                } catch (Exception e){
                    // todo: Inform the user that a network error occurred
                }

            }
        };

        worker.execute();

    }

    private JButton editSave(){
        JButton editSave = new JButton("Save");
        editSave.addActionListener(e -> {
            intentAnswerList.getAnswers().remove(getSelectedRow());
            intentAnswerList.getAnswers().add(new IntentAnswer());
            editTextField.setText("");
            table.setEnabled(true);
            updateTable();
            createStandardButtonPanel();
        });

        return editSave;
    }

    private JButton editCancel(){
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
