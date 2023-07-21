package com.rainbowacehardware.paintsalescompetitionmanager.Controllers;

import com.rainbowacehardware.paintsalescompetitionmanager.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    @FXML
    public Button employeesBtn;
    @FXML
    public Button addSaleBtn;
    @FXML
    public Label firstPlaceLabel;
    @FXML
    public Label secondPlaceLabel;
    @FXML
    public Label thirdPlaceLabel;
    @FXML
    public Label fourthPlaceLabel;
    @FXML
    public Button logoutBtn;
    @FXML
    public MenuButton rankingsMenuButton;
    @FXML
    public MenuItem currentTableMenuItem;
    @FXML
    public MenuItem allTimeLeadersMenuItem;
    @FXML
    public ProgressBar firstPlaceProgressBar;
    @FXML
    public ProgressBar secondPlaceProgressBar;
    @FXML
    public ProgressBar thirdPlaceProgressBar;
    @FXML
    public ProgressBar fourthPlaceProgressBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTopPerformers();
    }

    private void loadTopPerformers() {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        // Get the current month and year
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();

        // Query the database to get the top 4 performers for the current month and year
        String query = "SELECT employee, SUM(quantity) AS totalQuantity " +
                "FROM PaintSale " +
                "WHERE MONTH(date) = ? AND YEAR(date) = ? " +
                "GROUP BY employee " +
                "ORDER BY totalQuantity DESC " +
                "LIMIT 4";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, currentMonth);
            statement.setInt(2, currentYear);
            ResultSet resultSet = statement.executeQuery();

            // Clear previous data
            firstPlaceLabel.setText("");
            secondPlaceLabel.setText("");
            thirdPlaceLabel.setText("");
            fourthPlaceLabel.setText("");

            // Find the maximum quantity sold by the first-place employee
            int maxQuantity = 0;
            if (resultSet.next()) {
                maxQuantity = resultSet.getInt("totalQuantity");
                firstPlaceLabel.setText("1st: " + resultSet.getString("employee"));
                firstPlaceProgressBar.setProgress(maxQuantity);
            }

            // Set the progress bars proportionally based on the maximum quantity
            while (resultSet.next()) {
                String employee = resultSet.getString("employee");
                int totalQuantity = resultSet.getInt("totalQuantity");
                double progress = totalQuantity / (double) maxQuantity;

                // Set the label text and progress bar for the corresponding position
                switch (resultSet.getRow()) {
                    case 2:
                        secondPlaceLabel.setText("2nd: " + employee);
                        secondPlaceProgressBar.setProgress(progress);
                        break;
                    case 3:
                        thirdPlaceLabel.setText("3rd: " + employee);
                        thirdPlaceProgressBar.setProgress(progress);
                        break;
                    case 4:
                        fourthPlaceLabel.setText("4th: " + employee);
                        fourthPlaceProgressBar.setProgress(progress);
                        break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void employeesBtnOnAction(ActionEvent event) {
        openModal("/com/rainbowacehardware/paintsalescompetitionmanager/Employee.fxml");
    }

    @FXML
    public void addSaleBtnOnAction(ActionEvent event) {
        openModal("/com/rainbowacehardware/paintsalescompetitionmanager/Add-Sale.fxml");
    }

    private void openModal(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.initStyle(StageStyle.UNDECORATED);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(new Scene(root));
            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void currentTableMenuItemOnAction(ActionEvent event) {
        navigateToPage("/com/rainbowacehardware/paintsalescompetitionmanager/Menu-Items/Current-Table.fxml");
    }

    @FXML
    public void allTimeLeadersMenuItemOnAction(ActionEvent event) {
        navigateToPage("/com/rainbowacehardware/paintsalescompetitionmanager/Menu-Items/All-Time-Leaders.fxml");
    }

    private void navigateToPage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));

            Stage currentStage = (Stage) rankingsMenuButton.getScene().getWindow();
            currentStage.close();

            newStage.initStyle(StageStyle.UNDECORATED);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void logoutBtnOnAction(ActionEvent event) {
        try {
            // Load the login page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/rainbowacehardware/paintsalescompetitionmanager/Login-Page.fxml"));
            Parent root = loader.load();

            // Create a new stage for the login page
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.initStyle(StageStyle.UNDECORATED);

            // Close the current stage (associated with the home page view)
            Stage currentStage = (Stage) logoutBtn.getScene().getWindow();
            currentStage.close();

            // Show the login page stage
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
