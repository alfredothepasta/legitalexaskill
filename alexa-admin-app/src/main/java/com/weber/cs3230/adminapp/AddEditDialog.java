package com.weber.cs3230.adminapp;

import javax.swing.*;
import java.awt.*;

public class AddEditDialog extends JDialog {

    private boolean addingNew;
    private String intentNameEntered;
    private boolean saveClicked;
    private JTextField intentTextField;

    public AddEditDialog(boolean addingNew){
        this.addingNew = addingNew;
        setPreferredSize(new Dimension(300,  200));
        setModalityType(ModalityType.APPLICATION_MODAL);
        add(getAddEditPanel());
        pack();

    }

    private JComponent getAddEditPanel() {
        JPanel panel = new JPanel(new GridLayout(2,1));
        panel.add(new JLabel(addingNew ? "Add new intent" : "Edit Intent"));
        panel.add(getIntentEntryPanel());
        return panel;
    }

    private JComponent getIntentEntryPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Intent Name: "));
        panel.add(intentTextField = new JTextField());
        panel.add(cancelButton());
        panel.add(saveButton());




        return panel;
    }

    private JButton saveButton() {
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            this.intentNameEntered = intentTextField.getText();
            saveClicked = true;
        });
        return saveButton;
    }

    private JButton cancelButton(){
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {

            setVisible(false);
            dispose();
        });
        return cancelButton;
    }

    public String getIntentNameEntered() {
        return intentNameEntered;
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

}
