package com.company;

import javafx.beans.property.SimpleStringProperty;

public class Album {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    private String title;
    private Artist artist;
    private int artist_id;

    public Album(int id, String title, Artist artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    @Override
    public String toString() {
        return  getTitle() + ", by " + artist.getName();
    }

    public int getArtist_id() {
        return artist_id;
    }
}
