package com.example.jokeoverflow;

import com.example.jokeoverflow.Model.ApiJoke;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCall {
    @GET("Any?blacklistFlags=nsfw,racist,sexist,explicit&type=single")
    Call<ApiJoke> getAllData();
}
