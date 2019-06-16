package com.company.db_builder;

import com.company.*;
import com.company.controllers.AlbumsController;
import com.company.controllers.ArtistsController;
import com.company.controllers.GenresController;
import com.company.controllers.SongsController;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class TableInitializer {
/*
    public static void seedDb() throws SQLException {
        GenresController.create("Indie");
        GenresController.create("Pop");

        Artist eden = ArtistsController.create("EDEN");
        ArtistsController.create("Alec Benjamin");
        Artist billie = ArtistsController.create("Billie Eilish");

        Album vertigo = AlbumsController.create("vertigo", eden);
        Album billieAlbum = AlbumsController.create("WHEN WE ALL FALL ASLEEP WHERE DO WE GO", billie);

        Genre depresso = GenresController.create("depresso");
        Genre wannaDie = GenresController.create("Wanna die");

        SongsController.create("wrong", "2018-01-19", "1:04", vertigo, Arrays.asList(eden), depresso);
        SongsController.create("take care", "2018-01-19", "3:16", vertigo, Arrays.asList(eden), depresso);
        SongsController.create("bad guy", "2019-03-29", "3:14", billieAlbum, Arrays.asList(billie), wannaDie);
        SongsController.create("xanny", "2019-03-29", "4:04", billieAlbum, Arrays.asList(billie), wannaDie);
    }
    */
}
