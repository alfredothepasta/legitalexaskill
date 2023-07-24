package com.weber.cs3230.adminapp.dialogs;

import com.weber.cs3230.adminapp.thread.LockoutThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginDialog extends JDialog {

    JTextField usernameTextField;
    JPasswordField passwordTextField;
    private boolean isLoggedIn;

    public LoginDialog() {
        usernameTextField = new JTextField();
        passwordTextField = new JPasswordField();
        isLoggedIn = false;




        setPreferredSize(new Dimension(300, 200));
        setModalityType(ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout());
        add(new JLabel("Sign in:"), BorderLayout.NORTH);
        add(getCredentialsPanel(), BorderLayout.CENTER);
        pack();
    }

    private JComponent getCredentialsPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Username: "));
        panel.add(usernameTextField);
        panel.add(new JLabel("Password: "));
        panel.add(passwordTextField);
        panel.add(cancelButton());
        panel.add(loginButton());
        return panel;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    private JButton loginButton() {
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            if(usernameTextField.getText().equals("") && passwordTextField.getText().equals("")){
                isLoggedIn = true;
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Login not recognized.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
        return loginButton;
    }

    private JButton cancelButton(){
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
        return cancelButton;
    }

}
