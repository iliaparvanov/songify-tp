package com.company.javafx;

import com.company.Authentication;
import com.company.ClientConnection;
import com.company.SongifyClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthenticationController implements Initializable {

    private final static SongifyClient client = new ClientConnection().getClient();

    @FXML
    public TextField signInEmailTextField;
    @FXML public TextField signInPasswordTextField;

    @FXML public TextField signUpEmailTextField;
    @FXML public TextField signUpNameTextField;
    @FXML public PasswordField signUpPasswordField;
    @FXML public PasswordField signUpPasswordConfirmationField;
    @FXML public PasswordField secretPasswordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void signUpButtonPressed(ActionEvent event) throws IOException {
        if (!signUpEmailTextField.getText().equals("")
                && !signUpPasswordField.getText().equals("")
                && !signUpPasswordConfirmationField.getText().equals("")
                && !signUpNameTextField.getText().equals("")) {
            Call<Authentication> call = client.signup(signUpEmailTextField.getText(),
                    signUpNameTextField.getText(),
                    signUpPasswordField.getText(),
                    signUpPasswordConfirmationField.getText(),
                    "secret_key_that_no_one_knows");
            Response<Authentication> res = call.execute();
            if (res.isSuccessful()) {
                Authentication auth = res.body();
                loadMainScene(event, auth);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(res.errorBody().string());

                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields!");

            alert.showAndWait();
        }
    }

    public void signInButtonPressed(ActionEvent event) throws IOException {
        if (!signInEmailTextField.getText().equals("") && !signInPasswordTextField.getText().equals("")) {
            Call<Authentication> call = client.authenticate(signInEmailTextField.getText(), signInPasswordTextField.getText());
            Response<Authentication> res = call.execute();
            if (res.isSuccessful()) {
                Authentication auth = res.body();
                loadMainScene(event, auth);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(res.errorBody().string());

                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields!");

            alert.showAndWait();
        }

    }

    private void loadMainScene(ActionEvent event, Authentication auth) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent sampleViewParent = fxmlLoader.load();


        Controller controller = fxmlLoader.<Controller>getController();
        controller.setAuth(auth);

        Scene sampleViewScene = new Scene(sampleViewParent);

        Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());

        window.setScene(sampleViewScene);
        window.show();
    }
}
