package com.weber.cs3230.adminapp.dialogs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class AnswersDialog extends JDialog {


    private final ArrayList<String> intentAnswers;
    private final String currentIntent;

    private JTable table;
    DefaultTableModel tableModel;


    private boolean saveClicked = false;

    private JButton addButton = addButton();
    private JButton editButton = editButton();
    private JButton deleteButton = deleteButton();
    private JButton saveAndCloseButton = saveAndCloseButton();
    private JButton cancelButton = cancelButton();

    private JComponent buttonPanel = createButtonPanel();

    private JTextField editTextField;

    private String[] columnNames = {"Intent", "Date Added"};


    public AnswersDialog(String currentIntent, Map<String, ArrayList<String>> answerMap){
        this.currentIntent = currentIntent;
        intentAnswers = (ArrayList<String>) answerMap.get(currentIntent).clone();
        setPreferredSize(new Dimension(500,  200));
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
        panel.add(buttonPanel, BorderLayout.SOUTH);


        return panel;
    }

    private JComponent createButtonPanel(){
        JPanel panel = new JPanel(new GridLayout(1, 5));
        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(saveAndCloseButton);
        panel.add(cancelButton);
        return panel;

    }

    private JComponent createEditButtonPanel(){
        JPanel panel = new JPanel(new GridLayout(1, 5));
        panel.add(editSave());
        panel.add(editCancel());


        return panel;
    }

    private JButton addButton(){
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            intentAnswers.add(editTextField.getText());
            updateTable();
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
            buttonPanel = createEditButtonPanel();
            repaint();

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
        // todo: add action listener

        return editSave;
    }

    private JButton editCancel(){
        JButton editCancel = new JButton("Cancel");
        // todo: add action listener


        return editCancel;
    }

    private void swapButtonPanel(){

    }


}
