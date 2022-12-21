package com.company;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "523599";
    public static final String URL = "jdbc:mysql://localhost:3306/mysql";
    public static Connection connection;
    public static Statement statement;
    public static List<User> users = new LinkedList<>();
    public static List<String> logins = new LinkedList<>();

    static {
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            statement = connection.createStatement();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {

        System.out.println(hash("Тест дял проверки".getBytes()));

        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM store.users");
        while (resultSet.next()) {
            users.add(new User(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)));
            logins.add(resultSet.getString(2));
        }

        EventQueue.invokeLater(() -> {
                    if (UIManager.getLookAndFeel().isSupportedLookAndFeel()) {
                        final String platform = UIManager.getSystemLookAndFeelClassName();
                        if (!UIManager.getLookAndFeel().getName().equals(platform)) {
                            try {
                                UIManager.setLookAndFeel(platform);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }
                    }
                    Login frame = new Login();
                }
        );
    }

    public static String hash(byte[] input) {
        input = Logic.pad(input);

        long[][] blocks = Logic.toBlocks(input);

        long[][] W = Logic.Message(blocks);

        long[] buffer = Constants.IV.clone();

        for (int i = 0; i < blocks.length; i++) {
            long a = buffer[0];
            long b = buffer[1];
            long c = buffer[2];
            long d = buffer[3];
            long e = buffer[4];
            long f = buffer[5];
            long g = buffer[6];
            long h = buffer[7];

            for (int j = 0; j < 80; j++) {
                long t1 = h + Logic.Sigma1(e) + Logic.Ch(e, f, g) + Constants.K[j] + W[i][j];
                long t2 = Logic.Sigma0(a) + Logic.Maj(a, b, c);
                h = g;
                g = f;
                f = e;
                e = d + t1;
                d = c;
                c = b;
                b = a;
                a = t1 + t2;
            }

            buffer[0] = a + buffer[0];
            buffer[1] = b + buffer[1];
            buffer[2] = c + buffer[2];
            buffer[3] = d + buffer[3];
            buffer[4] = e + buffer[4];
            buffer[5] = f + buffer[5];
            buffer[6] = g + buffer[6];
            buffer[7] = h + buffer[7];
        }

        String result = "";
        for (int i = 0; i < 8; i++) {
            result += String.format("%016x", buffer[i]);
        }

        return result;
    }

    public static String convertPassword(char[] password) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < password.length; i++) {
            sb.append(password[i]);
        }
        return sb.toString();
    }
}