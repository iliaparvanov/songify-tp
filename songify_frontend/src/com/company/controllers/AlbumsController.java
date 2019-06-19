package com.company.controllers;

import com.company.Album;
import com.company.Artist;
import com.company.ClientConnection;
import com.company.SongifyClient;
import retrofit2.Call;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlbumsController {
    private final static SongifyClient client = new ClientConnection().getClient();
    private static List<Album> currentAlbums = new ArrayList<>();
    private static Album currentAlbum;

    public static List<Album> index() throws IOException {
        currentAlbums.clear();
        for (Artist a : ArtistsController.index()) {
            Call<List<Album>> call = client.getAllAlbums(a.getId());
            List<Album> albumsForArtist = call.execute().body();
            albumsForArtist.stream()
                    .forEach((album) -> album.setArtist(a));
            currentAlbums.addAll(albumsForArtist);
        }
        return currentAlbums;
    }


    public static Album create(String title, Artist artist) throws SQLException, IOException {
        Call<Album> call = client.createAlbum(artist.getId(), title);
        currentAlbum = call.execute().body();
        System.out.println(currentAlbum.getTitle());
        currentAlbum.setArtist(artist);
        return currentAlbum;
    }
/*
    public static void delete(int id) throws SQLException {
        SongsController.delete(AlbumsController.find(id));
        PreparedStatement statement = connection.getConn().prepareStatement("DELETE FROM Album WHERE id = ?");
        statement.setInt(1, id);
        int rowsDeleted = statement.executeUpdate();
        if(rowsDeleted > 0){
            System.out.println("Album deleted succesfully");
        }
    }

    public static void delete(Artist artist) throws SQLException {
        AlbumsController.find(artist).stream().forEach((a) -> {
            try {
                SongsController.delete(a);
            } catch(SQLException e) {
                e.printStackTrace();
            }});
        PreparedStatement statement = connection.getConn().prepareStatement("DELETE FROM Album WHERE ArtistID= ?");
        statement.setString(1, String.valueOf(artist.id));
        int rowsDeleted = statement.executeUpdate();
        if(rowsDeleted > 0){
            System.out.println("Album(s) deleted succesfully");
        }
    }

    public static void update(Album album) throws SQLException{
        PreparedStatement statement = connection.getConn()
                .prepareStatement("UPDATE Album SET title=?, artistId=? WHERE Id=?");

        statement.setString(1, album.getTitle());
        statement.setInt(2, album.getArtist().id);
        statement.setInt(3, album.id);

        int rowsUpdated = statement.executeUpdate();
        if(rowsUpdated > 0){
            System.out.println("Album updated succesfully");
        }
    }




    public static List<Album> find(Artist artist) throws SQLException{
        PreparedStatement statement = connection.getConn().prepareStatement("SELECT * FROM Album WHERE ArtistID = ?");
        statement.setInt(1, artist.id);

        ResultSet result = statement.executeQuery();
        List<Album> albums = new ArrayList<>();
        while (result.next()) {
            albums.add(new Album(result.getInt("Id"), result.getString("Title"), ArtistsController.find(result.getInt("ArtistID"))));
        }
        return albums;
    }

    public static List<Album> find(String title) throws SQLException{
        PreparedStatement statement = connection.getConn().prepareStatement("SELECT * FROM Album WHERE Title LIKE ?");
        statement.setString(1, "%" + title  + "%");

        ResultSet result = statement.executeQuery();
        List<Album> albums = new ArrayList<>();
        while (result.next()) {
            albums.add(new Album(result.getInt("Id"), result.getString("Title"), ArtistsController.find(result.getInt("ArtistID"))));
        }
        return albums;
    }

    public static Album findFirst(String title, Artist artist) throws SQLException {
        PreparedStatement statement = connection.getConn().prepareStatement("SELECT * FROM Album WHERE Title = ? AND ArtistID = ?");
        statement.setString(1, title);
        statement.setInt(2, artist.id);

        ResultSet result = statement.executeQuery();
        List<Album> albums = new ArrayList<>();
        while (result.next()) {
            albums.add(new Album(result.getInt("Id"), result.getString("Title"), ArtistsController.find(result.getInt("ArtistID"))));
        }
        return albums.get(0);
    }

    public static Album find(int id) throws SQLException {
        PreparedStatement statement = connection.getConn().prepareStatement("SELECT * FROM Album WHERE Id = ?");
        statement.setInt(1, id);

        ResultSet result = statement.executeQuery();
        List<Album> albums = new ArrayList<>();
        while (result.next()) {
            albums.add(new Album(id, result.getString("Title"), ArtistsController.find(result.getInt("ArtistID"))));
        }
        return albums.get(0);
    }
    */
}
