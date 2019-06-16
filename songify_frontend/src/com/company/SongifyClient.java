package com.company;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface SongifyClient {
    @GET("/artists")
    Call<List<Artist>> allArtists();
}
