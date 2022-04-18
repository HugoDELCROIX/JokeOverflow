package com.example.jokeoverflow.Model;

public class Joke {

    private String title;
    private String date;
    private String content;
    private float rating;
    private int userId;

    public Joke(String title, String date, String content, float rating, int userId){
        this.title = title;
        this.date = date;
        this.content = content;
        this.rating = rating;
        this.userId = userId;
    }

    public Joke(String title, String date, String content, int userId){
        this.title = title;
        this.date = date;
        this.content = content;
        this.userId = userId;
    }


    // Getters
    public String getTitle(){
        return this.title;
    }

    public String getDate() {
        return this.date;
    }

    public String getContent() {
        return this.content;
    }

    public float getRating() {
        return this.rating;
    }

    public int getUserId() {
        return this.userId;
    }

    // Setters
    public void setTitle(String title){
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setRating(float rating){
        this.rating = rating;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

}
