package com.weber.cs3230.adminapp.dialogs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

// well that was a nightmare to make. I'm sorry you have to try to read this.
// Everything works now and I honestly have no idea how it works and I wrote it.
public class AnswersDialog extends JDialog {


    private final ArrayList<String> intentAnswers;
    private final String currentIntent;

    private JTable table;
    DefaultTableModel tableModel;


    private boolean saveClicked = false;
    private JComponent buttonPanel = buttonPanel = new JPanel(new GridLayout(1, 5));;

    private JTextField editTextField;

    private final String[] columnNames = {"Answer"};


    public AnswersDialog(String currentIntent, Map<String, ArrayList<String>> answerMap){
        this.currentIntent = currentIntent;
        if(answerMap.containsKey(currentIntent)) {
            intentAnswers = (ArrayList<String>) answerMap.get(currentIntent).clone();
        } else {
            intentAnswers = new ArrayList<String>();
        }
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
        buttonPanel.add(saveAndCloseButton());
        buttonPanel.add(cancelButton());
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
            if(!editTextField.getText().trim().equals("")) {
                intentAnswers.add(editTextField.getText());
                editTextField.setText("");
                updateTable();
            }
        });

        return addButton;
    }

    private JButton saveAndCloseButton() {
        JButton saveButton = new JButton("Save And Close");
        saveButton.addActionListener(e -> {
            // update the array of answers
            saveClicked = true;

            setVisible(false);
            dispose();
        });
        return saveButton;
    }


    private JButton editButton(){
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(e -> {
            // replace button panel with the edit button panel
            if(table.isColumnSelected(0)) {
                getSelectedRow();
                createEditButtonPanel();
                table.setEnabled(false);
                editTextField.setText(intentAnswers.get(getSelectedRow()));
            }
        });

        return editButton;
    }

    private JButton deleteButton(){
        JButton deleteButton = new JButton("Delete");

        deleteButton.addActionListener(e -> {
            // todo: in a perfect universe where this is going to production, wrap in try/catch
            int row = table.getSelectedRow();
            intentAnswers.remove(row);
            updateTable();
        });

        return deleteButton;
    }

    private JButton cancelButton(){
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e->{
            setVisible(false);
            dispose();
        });
        return cancelButton;
    }

    private Object[][] getTableData(){
        java.util.List<Object[]> rows = new ArrayList<>();

        for (String answer : intentAnswers) {
            rows.add(new Object[]{answer});
        }
        return rows.toArray(new Object[0][0]);
    }
    public ArrayList<String> getIntentAnswers() {
        return intentAnswers;
    }

    private void updateTable(){
        tableModel.setDataVector(getTableData(), columnNames);
    }
    public boolean isSaveClicked() {
        return saveClicked;
    }

    private JButton editSave(){
        JButton editSave = new JButton("Save");
        editSave.addActionListener(e -> {
            intentAnswers.remove(getSelectedRow());
            intentAnswers.add(getSelectedRow(), editTextField.getText());
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
