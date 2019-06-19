package com.company.controllers;

import com.company.*;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtistsController {
    private final static SongifyClient client = new ClientConnection().getClient();
    private static List<Artist> currentArtists = new ArrayList<>();
    private static Artist currentArtist;

    public static List<Artist> index() throws NetworkFailureException, IOException {
        Call<List<Artist>> call =
                client.allArtists();

        currentArtists = call.execute().body();
//        call.enqueue(new Callback<>() {
//            @Override
//            public void onResponse(Call<List<Artist>> call, Response<List<Artist>> response) {
//                System.out.println("onResponse");
//                currentArtists = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<List<Artist>> call, Throwable t) {
//                throw new NetworkFailureException();
//            }
//        });
//        System.out.println("before return");
        return currentArtists;
    }

    public static Artist create(String name) throws NetworkFailureException, IOException {
        Call<Artist> call = client.createArtist(name);

        currentArtist = call.execute().body();
//        call.enqueue(new Callback<>() {
//            @Override
//            public void onResponse(Call<Artist> call, Response<Artist> response) {
//                currentArtist = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<Artist> call, Throwable t) {
//                throw new NetworkFailureException();
//            }
//        });

        return currentArtist;

    }

    public static void delete(int id) throws NetworkFailureException, IOException {
        Call<ResponseBody> call = client.deleteArtist(id);

        call.execute().body();
//        call.enqueue(new Callback<>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                throw new NetworkFailureException();
//            }
//        });
    }
    /*
    public static void update(Artist artist) throws SQLException {
        PreparedStatement statement = connection.getConn()
                .prepareStatement("UPDATE Artist SET name=? WHERE Id=?");

        statement.setString(1, artist.getName());
        statement.setInt(2, artist.id);

        int rowsUpdated = statement.executeUpdate();
        if(rowsUpdated > 0){
            System.out.println("Artist updated succesfully");
        }


    }

    public static List<Artist> find(String name) throws SQLException {
        PreparedStatement statement = connection.getConn().prepareStatement("SELECT * FROM Artist WHERE Name LIKE ?");
        statement.setString(1, "%" + name + "%");

        ResultSet result = statement.executeQuery();
        List<Artist> artists = new ArrayList<>();
        while (result.next()) {
            artists.add(new Artist(result.getInt("Id"), result.getString("Name")));
        }
        return artists;
    }

    public static Artist find(int id) throws SQLException {
        PreparedStatement statement = connection.getConn().prepareStatement("SELECT * FROM Artist WHERE Id = ?");
        statement.setInt(1, id);

        ResultSet result = statement.executeQuery();
        Artist artist = null;
        while (result.next()) {
            artist = new Artist(id, result.getString("Name"));
        }
        return artist;
    }

    public static List<Artist> findBySongId(int songId) throws SQLException {
        List<Artist> artists = new ArrayList<>();
        PreparedStatement preparedStatement = connection.getConn().prepareStatement("SELECT * FROM ArtistSong WHERE SongId = ?");
        preparedStatement.setInt(1, songId);
        ResultSet joinTableResult = preparedStatement.executeQuery();
        while (joinTableResult.next()) {
            artists.add(ArtistsController.find(joinTableResult.getInt("ArtistId")));
        }
        return artists;
    }
*/
}
