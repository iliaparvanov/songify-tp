package com.company;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface SongifyClient {
    @GET("/artists")
    Call<List<Artist>> getAllArtists();

    @GET("/artists/{id}")
    Call<Artist> getArtist(@Path("id") int id);

    @POST("/artists")
    @FormUrlEncoded
    Call<Artist> createArtist(@Field("name") String name);

    @PUT("/artists/{id}")
    Call<ResponseBody> updateArtist(@Path("id")int id, @Body Artist artist);

    @DELETE("artists/{id}")
    Call<ResponseBody> deleteArtist(@Path("id") int id);

    @GET("/artists/{id}/albums")
    Call<List<Album>> getAllAlbums(@Path("id") int artistId);

    @POST("/artists/{id}/albums")
    @FormUrlEncoded
    Call<Album> createAlbum(@Path("id") int artistId,
                            @Field("title") String title);

    @PUT("/artists/{id}/albums/{albumId}")
    Call<ResponseBody> updateAlbum(@Path("id")int id, @Path("albumId") int albumId, @Body Album album);

    @DELETE("artists/{id}/albums/{albumId}")
    Call<ResponseBody> deleteAlbum(@Path("id") int id,
                                   @Path("albumId") int albumId);
}
