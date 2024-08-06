package com.baimamboukar.java.app.main;
import com.baimamboukar.java.app.models.User;
public class App{
    public static void main(String[] args) {
        System.out.println("Running App...");
        User user = new User("8113", "Matrix", "20");
        System.out.println("User " + user.getName());
    }
}