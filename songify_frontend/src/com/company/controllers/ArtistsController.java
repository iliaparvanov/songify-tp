package com.company.controllers;

import com.company.*;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Header;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArtistsController {
    private static SongifyClient client = new ClientConnection().getClient();
    private static List<Artist> currentArtists = new ArrayList<>();
    private static Artist currentArtist;

    public static void authenticateClient(Authentication auth) {
        client = new ClientConnection(auth).getClient();
    }

    public static List<Artist> index() throws NetworkFailureException, IOException {
        Call<List<Artist>> call =
                client.getAllArtists();

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

    public static List<Artist> index(int page) throws NetworkFailureException, IOException {
        Call<List<Artist>> call =
                client.getArtistsOnPage(page);

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
        if (currentArtists == null) {
            System.out.println("NULL!!!!1");
        } else {

            for (Artist a : currentArtists) {
                System.out.println(a.toString());
            }
        }
        return currentArtists;
    }

    public static int maxPage() throws IOException {
        Call<List<Artist>> call = client.getArtistsOnPage(1);
        Response<List<Artist>> res = call.execute();
        Headers headers = res.headers();
        Map<String, List<String>> headersMap = headers.toMultimap();
        for (Map.Entry<String, List<String>> entry : headersMap.entrySet()) {
            if (entry.getKey().equals("x-total-pages")) {
                return Integer.parseInt(entry.getValue().get(0));
            }
        }
        return -1;
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

    public static void update(Artist artist) throws IOException {
        Call<ResponseBody> call = client.updateArtist(artist.getId(), artist);
        ResponseBody body = call.execute().body();
    }

    public static void delete(int id) throws NetworkFailureException, IOException {
        Call<ResponseBody> call = client.deleteArtist(id);

        ResponseBody body =  call.execute().body();
    }

    public static Artist find(int id) throws IOException {
        Call<Artist> call = client.getArtist(id);
        return call.execute().body();
    }
}
