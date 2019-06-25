package com.company.controllers;

import com.company.*;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AlbumsController {
    private static SongifyClient client = new ClientConnection().getClient();
    private static List<Album> currentAlbums = new ArrayList<>();
    private static Album currentAlbum;

    public static void authenticateClient(Authentication auth) {
        client = new ClientConnection(auth).getClient();
    }

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

    public static List<Album> index(int page) throws NetworkFailureException, IOException {
        Call<List<Album>> call =
                client.getAlbumsOnPage(page);

        currentAlbums = call.execute().body();
        if (currentAlbums == null) {
            System.out.println("NULL!!!!1");
        } else {

            for (Album a : currentAlbums) {
                Artist artist = ArtistsController.find(a.getArtist_id());
                a.setArtist(artist);
//                System.out.println(a.toString());
            }
        }
        return currentAlbums;
    }

    public static int maxPage() throws IOException {
        Call<List<Album>> call = client.getAlbumsOnPage(1);
        Response<List<Album>> res = call.execute();
        Headers headers = res.headers();
        Map<String, List<String>> headersMap = headers.toMultimap();
        for (Map.Entry<String, List<String>> entry : headersMap.entrySet()) {
            if (entry.getKey().equals("x-total-pages")) {
                return Integer.parseInt(entry.getValue().get(0));
            }
        }
        return -1;
    }

    public static Album create(String title, Artist artist) throws IOException {
        Call<Album> call = client.createAlbum(artist.getId(), title);
        currentAlbum = call.execute().body();
        currentAlbum.setArtist(artist);
        return currentAlbum;
    }

    public static void delete(Album album) throws IOException {
        Call<ResponseBody> call = client.deleteAlbum(album.getArtist().getId(), album.getId());
        call.execute().body();
    }

    public static void update(Album album) throws IOException {
        Call<ResponseBody> call = client.updateAlbum(album.getArtist().getId(), album.getId(), album);
        call.execute().body();
    }

    public static Album find(Artist artist, int albumId) throws IOException {
        Call<Album> call = client.getAlbum(artist.getId(), albumId);
        System.out.println("artist id: " + artist.getId());
        currentAlbum = call.execute().body();
//        System.out.println("artist id: " + currentAlbum.getArtist_id());
        currentAlbum.setArtist(ArtistsController.find(currentAlbum.getArtist_id()));
        return currentAlbum;
    }

    /*
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
    */
}
