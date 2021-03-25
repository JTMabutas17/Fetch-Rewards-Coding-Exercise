package com.example.fetchrewardsassignment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * JSONAPICall is the Java interface used by Retrofit
 */
public interface JSONAPICall {

    @GET("hiring.json")
    Call<List<DataModel>> getData();
}
