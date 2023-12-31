package com.weber.cs3230.adminapp.dialogs;

import com.weber.cs3230.adminapp.ApplicationController;

import javax.swing.*;
import java.awt.*;

public class AddEditDialog extends JDialog {

    private boolean addingNew;
    private String intentNameEntered;
    private boolean saveClicked;
    private JTextField intentTextField;

    private ApplicationController applicationController;

    public AddEditDialog(boolean addingNew, ApplicationController applicationController){
        this.addingNew = addingNew;
        this.applicationController = applicationController;
        setPreferredSize(new Dimension(300,  200));
        setModalityType(ModalityType.APPLICATION_MODAL);
        intentTextField = new JTextField();
        add(getAddEditPanel());
        pack();
    }

    public AddEditDialog(boolean addingNew, String currentIntent, ApplicationController applicationController){
        this.addingNew = addingNew;
        this.applicationController = applicationController;
        setPreferredSize(new Dimension(300,  200));
        setModalityType(ModalityType.APPLICATION_MODAL);
        intentTextField = new JTextField(currentIntent);
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
        panel.add(intentTextField);
        panel.add(cancelButton());
        panel.add(saveButton());
        return panel;
    }



    private JButton saveButton() {
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            this.intentNameEntered = intentTextField.getText();
            saveClicked = true;

            setVisible(false);
            dispose();
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
