package com.rainbowacehardware.paintsalescompetitionmanager.Controllers;

import com.rainbowacehardware.paintsalescompetitionmanager.DatabaseConnection;
import com.rainbowacehardware.paintsalescompetitionmanager.Objects.PaintEmployee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {
    @FXML
    public Button clearBtn;
    @FXML
    public Button selectBtn;
    @FXML
    public TextField nameTfld;
    @FXML
    public TextField phoneTfld;
    @FXML
    public TextField roleTfld;
    @FXML
    public Button addBtn;
    @FXML
    public Button updateBtn;
    @FXML
    public Button deleteBtn;
    @FXML
    public Button cancelBtn;
    @FXML
    public TableView<PaintEmployee> employeeTable;
    @FXML
    public TableColumn<PaintEmployee, Integer> idCol;
    @FXML
    public TableColumn<PaintEmployee, String> nameCol;
    @FXML
    public TableColumn<PaintEmployee, String>  phoneNumberCol;
    @FXML
    public TableColumn<PaintEmployee, String>  roleCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupEmployeeTableColumns();
        loadDataIntoEmployeeTable();
    }

    public void setupEmployeeTableColumns() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setCellFactory(tc -> {
            TableCell<PaintEmployee, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(nameCol.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleCol.setCellFactory(tc -> {
            TableCell<PaintEmployee, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(nameCol.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
    }

    public void loadDataIntoEmployeeTable() {
        ObservableList<PaintEmployee> employees = FXCollections.observableArrayList();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        String selectQuery = "SELECT * FROM PaintEmployee";

        try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String phoneNumber = resultSet.getString("phoneNumber");
                String role = resultSet.getString("role");

                PaintEmployee paintEmployee = new PaintEmployee(id, name, phoneNumber, role);
                employees.add(paintEmployee);
            }

            employeeTable.setItems(employees);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clearBtnOnAction(ActionEvent event) {
        clear();
    }

    @FXML
    public void selectBtnOnAction(ActionEvent event) {
        getSelectedData();
    }

    @FXML
    public void addBtnOnAction(ActionEvent event) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        String name = nameTfld.getText();
        String phoneNumber = phoneTfld.getText();
        String role = roleTfld.getText();

        String insertQuery = "INSERT INTO PaintEmployee (name, phoneNumber, role) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(insertQuery)){
            statement.setString(1, name);
            statement.setString(2, phoneNumber);
            statement.setString(3, role);

            statement.executeUpdate();

            clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        loadDataIntoEmployeeTable();
    }

    @FXML
    public void updateBtnOnAction(ActionEvent event) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        PaintEmployee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            String name = nameTfld.getText();
            String phoneNumber = phoneTfld.getText();
            String role = roleTfld.getText();

            String updateQuery = "UPDATE PaintEmployee set name=?, phoneNumber=?, role=? WHERE id=?";

            try (PreparedStatement statement = connection.prepareStatement(updateQuery)){
                statement.setString(1, name);
                statement.setString(2, phoneNumber);
                statement.setString(3, role);
                statement.setInt(4, selectedEmployee.getId());
                statement.executeUpdate();

                clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void deleteBtnOnAction(ActionEvent event) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        PaintEmployee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            String deleteQuery = "DELETE FROM PaintEmployee WHERE id=?";

            try (PreparedStatement statement = connection.prepareStatement(deleteQuery)){
                statement.setInt(1, selectedEmployee.getId());
                statement.executeUpdate();

                clear();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    public void cancelBtnOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void clear(){
        nameTfld.clear();
        phoneTfld.clear();
        roleTfld.clear();
    }

    public void getSelectedData() {
        PaintEmployee selectedRow  = employeeTable.getSelectionModel().getSelectedItem();

        if (selectedRow != null) {
            nameTfld.setText(selectedRow.getName());
            phoneTfld.setText(selectedRow.getPhoneNumber());
            roleTfld.setText(selectedRow.getRole());
        }
    }

}
