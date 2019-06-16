package com.company;

import javafx.beans.property.SimpleStringProperty;

public class Artist {
    private int id;
    private SimpleStringProperty name;

    public Artist(int id, String name) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name.get();
    }
}
