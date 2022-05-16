package com.example.jokeoverflow;

import com.example.jokeoverflow.Model.ApiJoke;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCall {
    String english = "Any?blacklistFlags=nsfw,racist,sexist,explicit&type=single";
    @GET(english)
    Call<ApiJoke> getAllData();
}
