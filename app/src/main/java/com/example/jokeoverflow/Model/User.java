package com.example.jokeoverflow.Model;

public class User {

    private String email;
    private String fullName;
    private String username;
    private int score;
    private int age;

    public User(){

    }

    public User(String email, String fullName, String username, int score, int age){
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

    public int getScore() {
        return score;
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

    public void setScore(int score) {
        this.score = score;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Methods

    public void upScore(int toAdd){
        this.score -= toAdd;
    }

    public void downScore(int toDown){
        this.score += toDown;
    }

    public int formattedScore(){
        return -this.score;
    }
}
