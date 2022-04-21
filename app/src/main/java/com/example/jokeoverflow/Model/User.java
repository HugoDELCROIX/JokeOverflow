package com.example.jokeoverflow.Model;

public class User {

    private String email;
    private String fullName;
    private String username;
    private int age;

    public User(String email, String fullName, String username, int age){
        this.email = email;
        this.fullName = fullName;
        this.username = username;
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
