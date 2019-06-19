package com.company;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
//import retrofit2.Response;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;


public class ClientConnection {

    private static final String API_BASE_URL = "http://localhost:3000";
    private SongifyClient client;

    public ClientConnection(Authentication auth) {
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        System.out.println(auth.getAuth_token());
//        httpClient.addInterceptor(chain -> {
//            Request request = chain.request().newBuilder().addHeader("Authorization", auth.getAuth_token()).build();
//            return chain.proceed(request);
//        });

        Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(getHeader(auth.getAuth_token())).build();

        this.client = retrofit.create(SongifyClient.class);
    }

    private OkHttpClient getHeader(final String authorizationValue ) {
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Interceptor.Chain chain) throws IOException {
                                Request request = null;
                                if (authorizationValue != null) {
//                                    Log.d("--Authorization-- ", authorizationValue);

                                    Request original = chain.request();
                                    // Request customization: add request headers
                                    Request.Builder requestBuilder = original.newBuilder()
                                            .addHeader("Authorization", authorizationValue);

                                    request = requestBuilder.build();
                                }
                                return chain.proceed(request);
                            }
                        })
                .build();
//        System.out.println(okClient.toString());
        return okClient;

    }

    public ClientConnection() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        this.client = retrofit.create(SongifyClient.class);
    }

    public SongifyClient getClient() {
        return client;
    }
}
