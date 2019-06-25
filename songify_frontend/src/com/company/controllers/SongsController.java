package com.company.controllers;

import com.company.*;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SongsController {

    private static SongifyClient client = new ClientConnection().getClient();
    private static List<Song> currentSongs = new ArrayList<>();
    private static Song currentSong;

    public static void authenticateClient(Authentication auth) {
        client = new ClientConnection(auth).getClient();
    }


    public static List<Song> index() throws IOException {
        Call<List<Song>> call =
                client.getAllSongs();

        currentSongs = call.execute().body();

        return currentSongs;
    }

    public static List<Song> index(int pageIndex) throws IOException {
        Call<List<Song>> call =
                client.getSongsForUser(pageIndex);

        currentSongs = call.execute().body();

        for (Song s : currentSongs) {
            System.out.println(s.getArtist_id());
            Artist songArtist = ArtistsController.find(s.getArtist_id());
            s.setArtist(songArtist);
            s.setAlbum(AlbumsController.find(songArtist, s.getAlbum_id()));
        }

        return currentSongs;
    }

    public static int maxPage() throws IOException {
        Call<List<Song>> call = client.getSongsForUser(1);
        Response<List<Song>> res = call.execute();
        Headers headers = res.headers();
        Map<String, List<String>> headersMap = headers.toMultimap();
        for (Map.Entry<String, List<String>> entry : headersMap.entrySet()) {
            if (entry.getKey().equals("x-total-pages")) {
                return Integer.parseInt(entry.getValue().get(0));
            }
        }
        return -1;
    }

    public static void delete(int id) throws SQLException, IOException {
        Call<ResponseBody> call = client.deleteSong(id);
        call.execute().body();

    }

    public static Song create(String title, String length, Album album, Artist artist, String genre) throws SQLException, IOException {
        Call<Song> call = client.createSong(title, length, genre, album.getTitle(), artist.getName());
        Song song = call.execute().body();
        song.setAlbum(album);
        song.setArtist(artist);
        return song;
    }


    public static void update(Song song) throws SQLException, IOException {
        Call<ResponseBody> call = client.updateSong(song.getId(), song.getTitle(), song.getLength(), song.getGenre(),
                song.getAlbum().getTitle(), song.getAlbum().getArtist().getName());
        ResponseBody responseBody = call.execute().body();
    }
    /*



    public static void delete(Album album) throws SQLException {
        String sql = "DELETE FROM Song WHERE albumId=?";

        PreparedStatement statement = connection.getConn().prepareStatement(sql);
        statement.setInt(1, album.id);

        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("A song was deleted successfully!");
        }
    }

    public static void delete(Artist artist) throws SQLException {
        find(artist).stream().forEach((s) -> {
            String sql = "DELETE FROM Song WHERE Id=?";
            try {
                System.out.println("Song title: " + s.getTitle());
                PreparedStatement statement = connection.getConn().prepareStatement(sql);
                statement.setInt(1, s.id);

                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Song(s) was deleted successfully!");
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        });

    }

    //TODO: Change this to use an SQL statement

    public static List<Song> find(String name) throws SQLException {
        PreparedStatement statement = connection.getConn().prepareStatement("SELECT * FROM Song WHERE title like ?");
        statement.setString(1, "%" + name + "%");

        ResultSet result = statement.executeQuery();
        List<Song> songs = new ArrayList<>();
        while (result.next()) {
            songs.add(new Song(result.getInt("Id"),
                    result.getString("title"),
                    result.getString("releaseDate"),
                    result.getString("length"),
                    AlbumsController.find(result.getInt("albumId")),
                    ArtistsController.findBySongId(result.getInt("Id")),
                    GenresController.find(result.getInt("genreId"))));
        }
        return songs;
    }

    public static List<Song> find(Artist artist) throws SQLException {
        PreparedStatement statement = connection.getConn().prepareStatement("SELECT * FROM Song s INNER JOIN ArtistSong ass ON ? = ass.ArtistId AND s.Id = ass.SongId");
        statement.setInt(1, artist.id);

        ResultSet result = statement.executeQuery();
        List<Song> songs = new ArrayList<>();
        while (result.next()) {
            songs.add(new Song(result.getInt("Id"),
                    result.getString("title"),
                    result.getString("releaseDate"),
                    result.getString("length"),
                    AlbumsController.find(result.getInt("albumId")),
                    ArtistsController.findBySongId(result.getInt("Id")),
                    GenresController.find(result.getInt("genreId"))));
        }
        return songs;
    }
    */
}
