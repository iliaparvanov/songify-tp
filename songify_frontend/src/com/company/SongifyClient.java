package com.company;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface SongifyClient {
    @POST("/auth/login")
    @FormUrlEncoded
    Call<Authentication> authenticate(@Field("email") String email,
                                      @Field("password") String password);

    @POST("/signup")
    @FormUrlEncoded
    Call<Authentication> signup(@Field("email") String email,
                                @Field("name") String name,
                                @Field("password") String password,
                                @Field("password_confirmation") String password_confirmation,
                                @Field("key_for_signup") String key_for_signup);

    @GET("/songs")
    Call<List<Song>> getAllSongs();

    @GET("/songs/{id}")
    Call<Song> getSong(@Path("id") int id);

    @POST("/songs")
    @FormUrlEncoded
    Call<Song> createSong(@Field("title") String title,
                          @Field("length") String length,
                          @Field("genre") String genre,
                          @Field("album") String album,
                          @Field("artist") String artist);

    @PUT("/songs/{id}")
    Call<ResponseBody> updateSong(@Path("id") int id, @Body Song song);

    @DELETE("/songs/{id}")
    Call<ResponseBody> deleteSong(@Path("id") int id);

    @GET("/artists")
    Call<List<Artist>> getAllArtists();

    @GET("/artists")
    Call<List<Artist>> getArtistsOnPage(@Query("page") int page);

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

    @GET("/albums")
    Call<List<Album>> getAlbumsOnPage(@Query("page") int page);

    @POST("/artists/{id}/albums")
    @FormUrlEncoded
    Call<Album> createAlbum(@Path("id") int artistId,
                            @Field("title") String title);

    @PUT("/artists/{id}/albums/{albumId}")
    Call<ResponseBody> updateAlbum(@Path("id")int id, @Path("albumId") int albumId, @Body Album album);

    @DELETE("artists/{id}/albums/{albumId}")
    Call<ResponseBody> deleteAlbum(@Path("id") int id,
                                   @Path("albumId") int albumId);
    @GET("/songs/my_songs")
    Call<List<Song>> getSongsForUser(@Query("page") int page);

}
