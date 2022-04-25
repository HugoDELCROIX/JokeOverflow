package com.example.jokeoverflow.Model;

public class Joke {

    private String key;
    private String title;
    private String date;
    private String content;
    private double rating;
    private String userId;

    public Joke(){

    }

    public Joke(String title, String date, String content, double rating, String userId){
        this.title = title;
        this.date = date;
        this.content = content;
        this.rating = rating;
        this.userId = userId;
    }

    public Joke(String title, String date, String content, String userId){
        this.title = title;
        this.date = date;
        this.content = content;
        this.userId = userId;
    }


    // Getters


    public String getKey() {
        return key;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDate() {
        return this.date;
    }

    public String getContent() {
        return this.content;
    }

    public double getRating() {
        return this.rating;
    }

    public String getUserId() {
        return this.userId;
    }

    // Setters


    public void setKey(String key) {
        this.key = key;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setRating(double rating){
        this.rating = rating;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

}
