package com.weber.cs3230.adminapp.dialogs;

import com.weber.cs3230.adminapp.ApplicationController;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {

    JTextField usernameTextField;
    JPasswordField passwordTextField;
    private boolean isLoggedIn;

    private ApplicationController applicationController;

    public LoginDialog(ApplicationController applicationController) {
        this.applicationController = applicationController;
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

            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            SwingWorker<Object, Object> worker = new SwingWorker<Object, Object>() {

                boolean loginCheck = false;

                @Override
                protected Object doInBackground() throws Exception {
                    loginCheck = applicationController.makeApiCall().validateCreds(usernameTextField.getText(), new String(passwordTextField.getPassword()));
                    return null;
                }

                @Override
                protected void done(){
                    setCursor(Cursor.getDefaultCursor());
                    if(loginCheck){
                        isLoggedIn = true;
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(LoginDialog.this, "Login not recognized.", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
            };

            worker.execute();


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
