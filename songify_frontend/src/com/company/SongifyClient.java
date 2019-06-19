package com.company;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface SongifyClient {
    @GET("/artists")
    Call<List<Artist>> allArtists();

    @POST("/artists")
    @FormUrlEncoded
    Call<Artist> createArtist(@Field("name") String name);

    @DELETE("artists/{id}")
    Call<ResponseBody> deleteArtist(@Path("id") int artistId);
}
