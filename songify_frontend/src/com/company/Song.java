package com.company;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

public class Song {
    public int id;
    private String title;
    private String releaseDate;
    private String length;
    private Album album;
    private Artist artist;
    private String genre;

    public Song(int id, String title, String releaseDate, String length, Album album, Artist artist, String genre) throws SQLException {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.length = length;
        this.album = album;
        this.artist = artist;
        this.genre = genre;
    }

    public Album getAlbum() {
        return album;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getLength() {
        return length;
    }

    public Artist getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void setArtists(List<Artist> artists) {
        this.artist = artist;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        String aStrings = "";
        aStrings += artist.toString();
        String albumString = "";
        if (album != null)
            albumString = album.toString();
        return  title + ", by " + aStrings + " with length " + this.length + " from album " + albumString;
    }
}
