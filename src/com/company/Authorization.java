package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Authorization extends JFrame {

    private JPanel panel;
    private JTextField textFieldLogin;
    private JPasswordField passwordField;
    private JLabel labelLogin;
    private JLabel labelPassword;
    private JButton buttonAuthorization;
    private JButton buttonBack;

    public Authorization() {
        setContentPane(panel);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setTitle("Авторизация");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        buttonAuthorization.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = textFieldLogin.getText();
                String password = Main.convertPassword(passwordField.getPassword());
                User user = null;
                for(User u: Main.users) {
                    if(u.login.equals(login)) {
                        user = new User(u.login, u.hash, u.salt);
                    }
                }
                if (user != null) {
                    String input = password + user.salt;
                    String hash = Main.hash(input.getBytes());
                    if(user.hash.equals(hash)) {
                        setVisible(false);
                        Compare frame = new Compare();
                    }
                    else {
                        JOptionPane.showMessageDialog(Authorization.this, "Неверный логин или пароль");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(Authorization.this, "Неверный логин или пароль");
                }
            }
        });

        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Login frame = new Login();
            }
        });
    }
}
