package com.company;

public class User {
    public String login;
    public String hash;
    public String salt;

    public User(String login, String hash, String salt) {
        this.login = login;
        this.hash = hash;
        this.salt = salt;
    }
}
