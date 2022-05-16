package com.example.jokeoverflow.Model;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Joke {

    private String key;
    private String title;
    private long date;
    private String content;
    private String category;
    private double rating;
    private String userId;

    public Joke(){

    }

    public Joke(String title, long date, String content, String category, double rating, String userId){
        this.title = title;
        this.date = -date;
        this.content = content;
        this.category = category;
        this.rating = -rating;
        this.userId = userId;
    }

    public Joke(String title, long date, String content, String category, String userId){
        this.title = title;
        this.date = -date;
        this.content = content;
        this.category = category;
        this.userId = userId;
    }


    // Getters


    public String getKey() {
        return key;
    }

    public String getTitle(){
        return this.title;
    }

    public long getDate() {
        return this.date;
    }

    public String getContent() {
        return this.content;
    }

    public String getCategory() {
        return this.category;
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

    public void setDate(long date) {
        this.date = date;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setRating(double rating){
        this.rating = rating;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    // Methods

    // Method to get the formatted date as we store negative timestamp in order to be able to order posts by date because firebase only order by ascending
    public String formattedDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(-date);
        return formatter.format(calendar.getTime());
    }

    public double formattedRating(){
        return -this.rating;
    }

    // Addition and substraction are inverted as jokes are stored with negative number to display them with right order in bestjoke fragment
    public void upRate(){
        this.rating = (double) Math.round((this.rating - 0.3) * 100) / 100;
    }

    public void downRate(){
        this.rating = (double) Math.round((this.rating + 0.3) * 100) / 100;
    }

}
