package com.rainbowacehardware.paintsalescompetitionmanager.Controllers.MenuItems;

import com.rainbowacehardware.paintsalescompetitionmanager.DatabaseConnection;
import com.rainbowacehardware.paintsalescompetitionmanager.Objects.CurrentLeader;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CurrentTableController implements Initializable {

    @FXML
    public TableView<CurrentLeader> currentLeadersTable;
    @FXML
    public TableColumn<CurrentLeader, Integer> idCol;
    @FXML
    public TableColumn<CurrentLeader, String> associateCol;
    @FXML
    public TableColumn<CurrentLeader, Integer> quantityCol;
    @FXML
    public TableColumn<CurrentLeader, Integer> rankingCol;
    @FXML
    public TableColumn<CurrentLeader, Integer> transactionCol;
    @FXML
    public Button homeBtn;
    @FXML
    public Button logoutBtn;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupCurrentLeadersTableColumns();
        loadDataIntoCurrentLeadersTable();
    }

    private void setupCurrentLeadersTableColumns() {
        idCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        associateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAssociate()));
        quantityCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantitySold()).asObject());
        rankingCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRanking()).asObject());
        transactionCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTransactionCount()).asObject());
    }


    private void loadDataIntoCurrentLeadersTable() {
        currentLeadersTable.getItems().clear();

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        String selectQuery = "SELECT employee, SUM(quantity) AS totalQuantity, COUNT(*) AS transactionCount " +
                "FROM PaintSale " +
                "WHERE MONTH(date) = MONTH(CURRENT_DATE()) AND YEAR(date) = YEAR(CURRENT_DATE()) " +
                "GROUP BY employee " +
                "ORDER BY totalQuantity DESC";


        try (PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {

            ObservableList<CurrentLeader> currentLeadersList = FXCollections.observableArrayList();
            int ranking = 1;
            int id = 1;

            while (resultSet.next()) {
                String employee = resultSet.getString("employee");
                int totalQuantity = resultSet.getInt("totalQuantity");
                int transactionCount = resultSet.getInt("transactionCount");

                CurrentLeader currentLeader = new CurrentLeader(id, employee, totalQuantity, ranking, transactionCount);
                currentLeadersList.add(currentLeader);

                id++;
                ranking++;
            }

            currentLeadersTable.setItems(currentLeadersList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void homeBtnOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/rainbowacehardware/paintsalescompetitionmanager/Home-Page.fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));

            Stage currentStage = (Stage) homeBtn.getScene().getWindow();
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

