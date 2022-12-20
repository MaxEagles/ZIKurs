package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Registration extends JFrame {
    private JTextField textFieldLastName;
    private JTextField textFieldFirstName;
    private JTextField textFieldSecondName;
    private JTextField textFieldAdress;
    private JTextField textFieldPhone;
    private JTextField textFieldPassport;
    private JTextField textFieldIndex;
    private JTextField textFieldLogin;
    private JPasswordField passwordField;
    private JPasswordField passwordField2;
    private JLabel labelLastName;
    private JLabel labelFirstName;
    private JLabel labelSecondName;
    private JLabel labelAdress;
    private JLabel labelPhone;
    private JLabel labelPassport;
    private JLabel labelIndex;
    private JLabel labelLogin;
    private JLabel labelPassword;
    private JLabel labelPassword2;
    private JPanel panel;
    private JButton buttonRegistration;
    private JButton buttonBack;

    public Registration() {
        setContentPane(panel);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setTitle("Регистрация");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        buttonRegistration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textFieldLastName.getText().length() == 0 ||
                textFieldFirstName.getText().length() == 0 ||
                textFieldSecondName.getText().length() == 0 ||
                textFieldAdress.getText().length() == 0 ||
                textFieldPhone.getText().length() == 0 ||
                textFieldPassport.getText().length() == 0 ||
                textFieldIndex.getText().length() == 0 ||
                textFieldLogin.getText().length() == 0 ||
                passwordField.getPassword().length == 0 ||
                passwordField2.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(Registration.this, "Все поля должны быть заполнены!");
                }
                else if(!Main.convertPassword(passwordField.getPassword()).equals(Main.convertPassword(passwordField2.getPassword()))) {
                    JOptionPane.showMessageDialog(Registration.this, "Пароли не совпадают!");
                }
                else if(Main.logins.contains(textFieldLogin.getText())) {
                    JOptionPane.showMessageDialog(Registration.this, "Пользователь с таким логином уже существует!");
                }
                else {
                    String login = textFieldLogin.getText();
                    StringBuilder saltBuilder = new StringBuilder();
                    saltBuilder.append(textFieldLastName.getText());
                    saltBuilder.append(textFieldFirstName.getText());
                    saltBuilder.append(textFieldSecondName.getText());
                    saltBuilder.append(textFieldAdress.getText());
                    saltBuilder.append(textFieldPhone.getText());
                    saltBuilder.append(textFieldPassport.getText());
                    saltBuilder.append(textFieldIndex.getText());
                    String salt = saltBuilder.toString();
                    salt = Main.hash((salt.getBytes()));
                    String input = Main.convertPassword(passwordField.getPassword()) + salt;
                    String hash = Main.hash(input.getBytes());
                    try {
                        Main.statement.executeUpdate("INSERT INTO store.users (login, hash, salt) values ('" + login + "','" + hash + "','" + salt + "')");
                        Main.logins.add(login);
                        Main.users.add(new User(login, hash, salt));
                        JOptionPane.showMessageDialog(Registration.this, "Регистрация успешна");
                        setVisible(false);
                        Login frame = new Login();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(Registration.this, "Ошибка при записи в базу данных");
                    }
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
