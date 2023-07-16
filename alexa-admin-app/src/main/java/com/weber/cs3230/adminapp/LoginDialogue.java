package com.weber.cs3230.adminapp;

import javax.swing.*;
import java.awt.*;

public class LoginDialogue extends JDialog {

    JTextField usernameTextField = new JTextField();
    JPasswordField passwordTextField = new JPasswordField();
    private boolean isLoggedIn = false;

    public LoginDialogue() {
        setPreferredSize(new Dimension(300, 200));
        setModalityType(ModalityType.APPLICATION_MODAL);
        setLayout(new GridLayout(3, 2));
        add(new JLabel("Username: "));
        add(usernameTextField);
        add(new JLabel("Password: "));
        add(passwordTextField);
        add(new JButton("Login"));
        add(new JButton("Cancel"));
//        add(getSomeJPanel());
        pack();
    }

    public boolean getIsLoggedIn(){
        // we just gonna do some checking
        if(usernameTextField.getText().equals("admin") && passwordTextField.getText().equals("donttellsecurity")){
            isLoggedIn = true;
        } else {
            JOptionPane.showMessageDialog(this, "Login not recognized.", "Warning", JOptionPane.WARNING_MESSAGE);
        }

        return isLoggedIn;
    }

    public JButton cancelButton(){
        JButton cancelButton = new JButton("Cancel");
        return cancelButton;
    }

}
