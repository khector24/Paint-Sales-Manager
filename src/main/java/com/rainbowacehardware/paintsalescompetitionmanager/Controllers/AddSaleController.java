package com.rainbowacehardware.paintsalescompetitionmanager.Controllers;

import com.rainbowacehardware.paintsalescompetitionmanager.DatabaseConnection;
import com.rainbowacehardware.paintsalescompetitionmanager.Objects.PaintSale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class AddSaleController implements Initializable {
    @FXML
    public Button clearBtn;
    @FXML
    public Button selectBtn;
    @FXML
    public Button addBtn;
    @FXML
    public Button updateBtn;
    @FXML
    public Button deleteBtn;
    @FXML
    public Button cancelBtn;
    @FXML
    public TableView<PaintSale> salesTable;
    @FXML
    public TableColumn<PaintSale, Integer> idCol;
    @FXML
    public TableColumn<PaintSale, String> nameCol;
    @FXML
    public ComboBox<String> employeeComboBox;
    @FXML
    public TableColumn<PaintSale, LocalDate> dateCol;
    @FXML
    public TableColumn<PaintSale, String> associateCol;
    @FXML
    public TableColumn<PaintSale, String> quantityCol;
    @FXML
    public TextField quantityTfld;
    @FXML
    public TextField receiptTfld;
    @FXML
    public DatePicker datePicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupSalesTableColumns();
        loadDataIntoSalesTable();
        loadEmployeesIntoComboBox();
    }

    public void loadEmployeesIntoComboBox() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        String selectQuery = "SELECT id, name FROM PaintEmployee";

        try (PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {

            List<String> employeesList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String employee = id + " -> " + name;

                employeesList.add(employee);
            }

            ObservableList<String> comboBoxItems1 = FXCollections.observableArrayList(employeesList);
            employeeComboBox.setItems(comboBoxItems1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void setupSalesTableColumns() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("receipt"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        associateCol.setCellValueFactory(new PropertyValueFactory<>("employee"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }


    public void loadDataIntoSalesTable() {

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        String selectQuery = "SELECT id, receipt, date, employee, quantity FROM PaintSale";

        try (PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {

            ObservableList<PaintSale> salesList = FXCollections.observableArrayList();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String receipt = resultSet.getString("receipt");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                String employee = resultSet.getString("employee");
                String quantity = resultSet.getString("quantity");

                PaintSale sale = new PaintSale(id, receipt, date, employee, quantity);
                salesList.add(sale);
            }

            salesTable.setItems(salesList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void addBtnOnAction(ActionEvent event) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        String receipt = receiptTfld.getText();
        LocalDate date = datePicker.getValue();
        String employee = employeeComboBox.getValue();
        String quantity = quantityTfld.getText();

        String insertQuery = "INSERT INTO PaintSale (receipt, date, employee, quantity) VALUES (?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, receipt);
            statement.setDate(2, Date.valueOf(date));
            statement.setString(3, String.valueOf(employee));
            statement.setString(4, quantity);
            statement.executeUpdate();

            clear();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadDataIntoSalesTable();
    }

    @FXML
    public void updateBtnOnAction(ActionEvent event) {
        PaintSale selectedRow = salesTable.getSelectionModel().getSelectedItem();

        if (selectedRow != null) {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();

            String receipt = receiptTfld.getText();
            LocalDate date = datePicker.getValue();
            String employee = employeeComboBox.getValue();
            String quantity = quantityTfld.getText();

            String updateQuery = "UPDATE PaintSale SET receipt = ?, date = ?, employee = ?, quantity = ? WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                statement.setString(1, receipt);
                statement.setDate(2, Date.valueOf(date));
                statement.setString(3, String.valueOf(employee));
                statement.setString(4, quantity);
                statement.setInt(5, selectedRow.getId());
                statement.executeUpdate();

                clear();
                loadDataIntoSalesTable();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    public void deleteBtnOnAction(ActionEvent event) {
        PaintSale selectedRow = salesTable.getSelectionModel().getSelectedItem();

        if (selectedRow != null) {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();

            String deleteQuery = "DELETE FROM PaintSale WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                statement.setInt(1, selectedRow.getId());
                statement.executeUpdate();

                clear();
                loadDataIntoSalesTable();

            } catch (SQLException e) {
                e.printStackTrace();
            }
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

    public void clear() {
        quantityTfld.clear();
        receiptTfld.clear();
        employeeComboBox.setValue(null);
        datePicker.setValue(null);
    }

    public void getSelectedData() {
        PaintSale selectedRow = salesTable.getSelectionModel().getSelectedItem();

        if (selectedRow != null) {
            receiptTfld.setText(selectedRow.getReceipt());
            datePicker.setValue(selectedRow.getDate());
            employeeComboBox.setValue(selectedRow.getEmployee());
            quantityTfld.setText(selectedRow.getQuantity());
        }
    }

    @FXML
    public void cancelBtnOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

}
