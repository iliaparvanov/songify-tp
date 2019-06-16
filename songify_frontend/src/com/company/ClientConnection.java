package com.company;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.sql.*;
import java.util.Properties;


public class ClientConnection {

    private static final String API_BASE_URL = "https://localhost:3000/";
    private SongifyClient client;

    public ClientConnection() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        this.client =  retrofit.create(SongifyClient.class);
    }

    public SongifyClient getClient() {
        return client;
    }
}
