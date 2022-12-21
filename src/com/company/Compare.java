package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Compare extends JFrame {
    private JPanel panel;
    private JButton buttonFile1;
    private JButton buttonFile2;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JButton buttonCompare;
    private JTextField textField1;
    private JTextField textField2;
    private String fileText1 = "";
    private String fileText2 = "";
    private int maxLength = 700;

    public Compare() {
        setContentPane(panel);
        setSize(800, 500);
        setTitle("Сравнение файлов");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setVisible(true);


        buttonFile1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TextFileChooser chooser = new TextFileChooser();
                int result = chooser.showOpenDialog(Compare.this);
                if (result == 0) {
                    try {
                        fileText1 = readFile(chooser.getSelectedFile());
                        if(fileText1.length() > maxLength) {
                            textArea1.setText(fileText1.substring(0, maxLength) + "...");
                        }
                        else {
                            textArea1.setText(fileText1);
                        }
                    }
                    catch (IOException ex) {
                        JOptionPane.showMessageDialog(Compare.this, ex.getMessage());
                    }
                }
            }
        });

        buttonFile2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TextFileChooser chooser = new TextFileChooser();
                int result = chooser.showOpenDialog(Compare.this);
                if (result == 0) {
                    try {
                        fileText2 = readFile(chooser.getSelectedFile());
                        if(fileText2.length() > maxLength) {
                            textArea2.setText(fileText2.substring(0, maxLength) + "...");
                        }
                        else {
                            textArea2.setText(fileText2);
                        }
                    }
                    catch (IOException ex) {
                        JOptionPane.showMessageDialog(Compare.this, ex.getMessage());
                    }
                }
            }
        });

        buttonCompare.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    String hash1 = Main.hash(fileText1.getBytes());
                    String hash2 = Main.hash(fileText2.getBytes());
                    textField1.setText(hash1);
                    textField2.setText(hash2);
                    if(hash1.equals(hash2)) {
                        JOptionPane.showMessageDialog(Compare.this, "Файлы идентичны");
                    }
                    else {
                        JOptionPane.showMessageDialog(Compare.this, "Файлы различны");
                    }
            }
        });


    }

    private String readFile (File file) throws IOException {
        try (FileReader fr = new FileReader(file)) {
            Scanner scanner = new Scanner(fr);
            StringBuilder sb = new StringBuilder();
            while(scanner.hasNextLine()) {
               sb.append(scanner.nextLine());
               sb.append("\r\n");
            }
            return sb.toString();
        }
        catch (IOException ex) {
            throw new IOException("Ошибка при открытии файла");
        }
    }
}
