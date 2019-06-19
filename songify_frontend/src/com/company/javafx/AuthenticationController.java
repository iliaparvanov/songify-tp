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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import retrofit2.Call;

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
                    signUpPasswordConfirmationField.getText());
            Authentication auth = call.execute().body();
//            System.out.println(auth.getAuth_token());
            if (auth.getAuth_token() != null) {
                loadMainScene(event, auth);
            }
        }
    }

    public void signInButtonPressed(ActionEvent event) throws IOException {
        if (!signInEmailTextField.getText().equals("") && !signInPasswordTextField.getText().equals("")) {
            Call<Authentication> call = client.authenticate(signInEmailTextField.getText(), signInPasswordTextField.getText());
            Authentication auth = call.execute().body();
//            System.out.println(auth.getAuth_token());
            if (auth.getAuth_token() != null) {
                loadMainScene(event, auth);
            }
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
