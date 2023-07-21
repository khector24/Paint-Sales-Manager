package com.rainbowacehardware.paintsalescompetitionmanager.Controllers;

import com.rainbowacehardware.paintsalescompetitionmanager.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginPageController {
    @FXML
    public TextField usernameTxtfld;
    @FXML
    public PasswordField passwordFld;
    @FXML
    public Button loginBtn;
    @FXML
    public Button closeBtn;
    @FXML
    public Label warningLbl;

    @FXML
    public void loginButtonOnAction(ActionEvent event) {
        validateLogin();
    }

    @FXML
    public void closeButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM userAccounts WHERE username = ? AND password = ?";

        try {
            PreparedStatement statement = connectDB.prepareStatement(verifyLogin);
            statement.setString(1, usernameTxtfld.getText());
            statement.setString(2, passwordFld.getText());

            ResultSet queryResult = statement.executeQuery();

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/rainbowacehardware/paintsalescompetitionmanager/Home-Page.fxml"));
                        Parent root = loader.load();

                        // Create a new stage for the home page
                        Stage homePageStage = new Stage();
                        homePageStage.setScene(new Scene(root));

                        // Close the current stage (associated with the login page view)
                        Stage currentStage = (Stage) loginBtn.getScene().getWindow();
                        currentStage.close();

                        // Set the stage to be undecorated
                        homePageStage.initStyle(StageStyle.UNDECORATED);

                        // Show the home page stage
                        homePageStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    warningLbl.setText("Invalid Login. Please try again.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
