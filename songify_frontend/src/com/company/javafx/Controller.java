
package com.company.javafx;

import com.company.*;
import com.company.controllers.AlbumsController;
import com.company.controllers.ArtistsController;
import com.company.controllers.GenresController;
import com.company.controllers.SongsController;
import com.company.db_builder.TableInitializer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Controller implements Initializable {

    @FXML
    public Pagination artistsPagination;
    @FXML
    public Pagination albumsPagination;
    @FXML
    public Pagination songsPagination;

    @FXML
    public TableView<Artist> artistTableView;
    @FXML
    public TableColumn<Artist, String> artistNameColumn;
    @FXML
    public TextField artistNameTextField;

    @FXML
    public TableView<Song> songTableView;
    @FXML
    public TableColumn<Song, String> songTitleColumn;
    @FXML
    public TableColumn<Song, String> songLengthColumn;
    @FXML
    public TableColumn<Song, Album> songAlbumColumn;
    @FXML
    public TableColumn<Song, Artist> songArtistColumn;
    @FXML
    public TableColumn<Song, String> songGenreColumn;

    @FXML
    public TableView<Album> albumTableView;
    @FXML
    public TableColumn<Album, String> albumTitleColumn;
    @FXML
    public TableColumn<Album, Artist> albumArtistColumn;
    @FXML
    public TextField albumTitleTextField;

    //Song creation
    @FXML
    public TextField songTitleTextField;
    @FXML
    public TextField songGenreTextField;
    @FXML
    public ChoiceBox<Integer> songLengthMinutesChoiceBox;
    @FXML
    public ChoiceBox<Integer> songLengthTensOfSecondsChoiceBox;
    @FXML
    public ChoiceBox<Integer> songLengthSecondsChoiceBox;

    private Authentication auth;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(() -> {
            authenticateControllers();
            try {
                fetchAllFromDB();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                initializePagination();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        artistNameColumn.setCellValueFactory(new PropertyValueFactory<Artist, String>("name"));
        artistTableView.setEditable(true);
        artistNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

//        albumTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        albumTitleColumn.setCellValueFactory(new PropertyValueFactory<Album, String>("title"));
        albumArtistColumn.setCellValueFactory(new PropertyValueFactory<Album, Artist>("artist"));
        albumTitleColumn.setCellFactory(TextFieldTableCell.forTableColumn());

//        songTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        songTitleColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
        songTitleColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        songLengthColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("length"));
        songAlbumColumn.setCellValueFactory(new PropertyValueFactory<Song, Album>("album"));
        songArtistColumn.setCellValueFactory(new PropertyValueFactory<Song, Artist>("artist"));
        songGenreColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("genre"));

        songLengthMinutesChoiceBox.setItems(FXCollections.observableList(IntStream.rangeClosed(1, 9).boxed().collect(Collectors.toList())));
        songLengthTensOfSecondsChoiceBox.setItems(FXCollections.observableList(IntStream.rangeClosed(0, 9).boxed().collect(Collectors.toList())));
        songLengthSecondsChoiceBox.setItems(FXCollections.observableList(IntStream.rangeClosed(0, 9).boxed().collect(Collectors.toList())));

        songTableView.setEditable(true);
        /*
        genreNameColumn.setCellValueFactory(new PropertyValueFactory<Genre, String>("name"));
        genreNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        songTitleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        */
    }

    private void authenticateControllers() {
//        System.out.println(auth.getAuth_token());
        AlbumsController.authenticateClient(auth);
        ArtistsController.authenticateClient(auth);
        SongsController.authenticateClient(auth);
    }

    private void initializePagination() throws IOException {
        artistsPagination.setPageFactory((Integer pageIndex) -> createArtistsPage(pageIndex));
        artistsPagination.setMaxPageIndicatorCount(ArtistsController.maxPage());
        artistsPagination.setPageCount(ArtistsController.maxPage());

        albumsPagination.setPageFactory((Integer pageIndex) -> createAlbumsPage(pageIndex));
        albumsPagination.setMaxPageIndicatorCount(AlbumsController.maxPage());
        albumsPagination.setPageCount(AlbumsController.maxPage());

        songsPagination.setPageFactory((Integer pageIndex) -> createSongsPage(pageIndex));
        songsPagination.setMaxPageIndicatorCount(SongsController.maxPage());
        songsPagination.setPageCount(SongsController.maxPage());
    }

    public Node createSongsPage(int pageIndex) {
        pageIndex += 1;
        try {
            songTableView.setItems(FXCollections.observableList(SongsController.index(pageIndex)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BorderPane(songTableView);
    }

    public Node createAlbumsPage(int pageIndex) {
        pageIndex += 1;
        try {
            albumTableView.setItems(FXCollections.observableList(AlbumsController.index(pageIndex)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BorderPane(albumTableView);
    }

    public Node createArtistsPage(int pageIndex) {
        pageIndex += 1;
        try {
            artistTableView.setItems(FXCollections.observableList(ArtistsController.index(pageIndex)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BorderPane(artistTableView);
    }

    public void signout(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("authentication.fxml"));
        Parent sampleViewParent = fxmlLoader.load();

        Scene sampleViewScene = new Scene(sampleViewParent);

        Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());

        window.setScene(sampleViewScene);
        window.show();
    }


    public void changeArtistNameCellEvent(TableColumn.CellEditEvent editted) throws IOException {
        Artist selectedArtist = artistTableView.getSelectionModel().getSelectedItem();
        selectedArtist.setName(editted.getNewValue().toString());
        ArtistsController.update(selectedArtist);
        initializePagination();
    }


    public void changeAlbumTitleCellEvent(TableColumn.CellEditEvent editted) throws IOException {
        Album selectedAlbum = albumTableView.getSelectionModel().getSelectedItem();
        selectedAlbum.setTitle(editted.getNewValue().toString());
        AlbumsController.update(selectedAlbum);
        initializePagination();
//        fetchAllFromDB();
    }

    public void changeSongTitleCellEvent(TableColumn.CellEditEvent editted) throws SQLException, IOException {
        Song selectedSong = songTableView.getSelectionModel().getSelectedItem();
        selectedSong.setTitle(editted.getNewValue().toString());
        System.out.println("SONG: " + selectedSong.toString());
        SongsController.update(selectedSong);
        initializePagination();
    }

    public void changeSongLength() throws SQLException {
//        Song selectedSong = songTableView.getSelectionModel().getSelectedItem();
//        if (songLengthMinutesChoiceBox.getValue() != null
//                && songLengthTensOfSecondsChoiceBox.getValue() != null
//                && songLengthSecondsChoiceBox.getValue() != null) {
//            String length = songLengthMinutesChoiceBox.getValue().toString() +
//                    ":" +
//                    songLengthTensOfSecondsChoiceBox.getValue().toString() +
//                    songLengthSecondsChoiceBox.getValue().toString();
//            selectedSong.setLength(length);
//            SongsController.update(selectedSong);
//            fetchAllFromDB();
//        }
    }


    public void changeSongGenre() throws SQLException {
//        if (songTableView.getSelectionModel().getSelectedItems().size() > 0 && genreTableView.getSelectionModel().getSelectedItems().size() == 1) {
//            for (Song s : songTableView.getSelectionModel().getSelectedItems()) {
//                s.setGenre(genreTableView.getSelectionModel().getSelectedItem());
//                SongsController.update(s);
//            }
//            fetchAllFromDB();
//        }
    }

    public void changeSongAlbum() throws SQLException, IOException {
        if (songTableView.getSelectionModel().getSelectedItems().size() > 0 && albumTableView.getSelectionModel().getSelectedItems().size() == 1) {
            for (Song s : songTableView.getSelectionModel().getSelectedItems()) {
                s.setAlbum(albumTableView.getSelectionModel().getSelectedItem());
                SongsController.update(s);
            }
            initializePagination();
        }
    }

    public void changeSongArtists() throws SQLException {
//        if (songTableView.getSelectionModel().getSelectedItems().size() > 0 && artistTableView.getSelectionModel().getSelectedItems().size() > 0) {
//            for (Song s : songTableView.getSelectionModel().getSelectedItems()) {
//                s.setArtists(artistTableView.getSelectionModel().getSelectedItems());
//                SongsController.update(s);
//            }
//            fetchAllFromDB();
//        }
    }

    public void deleteArtists() throws SQLException, IOException {
        ObservableList<Artist> selectedArtists;
        selectedArtists = artistTableView.getSelectionModel().getSelectedItems();
        for (Artist a : selectedArtists) {
            ArtistsController.delete(a.getId());
        }
        try {
            fetchAllFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initializePagination();
    }

    public void createArtist() throws NetworkFailureException, IOException {
        if (!artistNameTextField.getText().toString().equals("")) {
            Artist artist = ArtistsController.create(artistNameTextField.getText().toString());
            artistTableView.getItems().add(artist);
        }
    }

    public void deleteSongs() throws SQLException, IOException {
        ObservableList<Song> selectedSongs;
        selectedSongs = songTableView.getSelectionModel().getSelectedItems();
        for (Song s : selectedSongs) {
            SongsController.delete(s.getId());
        }
        try {
            initializePagination();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createSong() throws SQLException, IOException {

        if (!songTitleTextField.getText().toString().equals("")
            && songLengthMinutesChoiceBox.getValue() != null
            && songLengthSecondsChoiceBox.getValue() != null
            && songLengthTensOfSecondsChoiceBox.getValue() != null
            && albumTableView.getSelectionModel().getSelectedItems().size() > 0
            && songGenreTextField.getText().toString() != null) {
            String length = songLengthMinutesChoiceBox.getValue().toString() +
                    ":" +
                    songLengthTensOfSecondsChoiceBox.getValue().toString() +
                    songLengthSecondsChoiceBox.getValue().toString();
            Song song = SongsController.create  (songTitleTextField.getText().toString(),
                                                length,
                                                albumTableView.getSelectionModel().getSelectedItem(),
                    albumTableView.getSelectionModel().getSelectedItem().getArtist(),
                    songGenreTextField.getText());
            songTableView.getItems().add(song);
        }

    }

    public void createAlbum() throws SQLException, IOException {
        if (!albumTitleTextField.getText().toString().equals("") && artistTableView.getSelectionModel().getSelectedItems().size() != 0) {
            Album album = AlbumsController.create(albumTitleTextField.getText().toString(), artistTableView.getSelectionModel().getSelectedItems().get(0));
            albumTableView.getItems().add(album);
        }
    }

    public void deleteAlbums() throws SQLException, IOException {
        ObservableList<Album> selectedAlbums;
        selectedAlbums = albumTableView.getSelectionModel().getSelectedItems();
        selectedAlbums.stream().forEach((album -> {
            try {
                AlbumsController.delete(album);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        initializePagination();
    }


    private void fetchAllFromDB() throws IOException {
//        artistTableView.setItems(FXCollections.observableList(ArtistsController.index(1)));
//        System.out.println(ArtistsController.maxPage());

//        albumTableView.setItems(FXCollections.observableList(AlbumsController.index()));
//        songTableView.setItems(FXCollections.observableList(SongsController.index()));
//        genreTableView.setItems(FXCollections.observableList(GenresController.index()));
    }



    public Authentication getAuth() {
        return auth;
    }

    public void setAuth(Authentication auth) {
        this.auth = auth;
    }
}


