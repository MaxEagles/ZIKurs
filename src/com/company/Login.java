package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JButton buttonAuthorization;
    private JButton buttonRegistration;
    private JPanel panel;

    public Login() {
        setContentPane(panel);
        setSize(800, 500);
        setTitle("Вход");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        buttonAuthorization.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Authorization frame = new Authorization();
            }
        });

        buttonRegistration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Registration frame = new Registration();
            }
        });
    }
}
