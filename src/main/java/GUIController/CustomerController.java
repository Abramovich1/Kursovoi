package GUIController;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Sql.SqlOperation;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Класс-контроллер окна вывода информации о клиениах
 */
public class CustomerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField searchCustomerField;

    @FXML
    private Button mainButton;

    @FXML
    private TableView customerTable;

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button deleteCustomerButton;

    ResultSet executeQuery;

    String currentCustomer;

    String[] parts;

    @FXML
    void initialize() throws SQLException {
        mainButton.setOnAction(event -> NewStage.createStage(mainButton, "/fxml/Main.fxml", false,"Главная"));
        addCustomerButton.setOnAction(event -> NewStage.createStage(addCustomerButton, "/fxml/NewCustomer.fxml", false,"Добавление покупателя"));
        if (searchCustomerField.getText().equals("")) {
            executeQuery = SqlOperation.selectCustomer("Покупатели", "*", searchCustomerField.getText(), 2);
            NewTable.createTable(customerTable, executeQuery, false);
        }
        searchCustomerField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                executeQuery = SqlOperation.selectCustomer("Покупатели", "*", searchCustomerField.getText(), 2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            NewTable.createTable(customerTable, executeQuery, false);
        });
        customerTable.setOnMouseClicked(event -> {
            currentCustomer = customerTable.getSelectionModel().getSelectedItem().toString();
            currentCustomer = currentCustomer.replace("[", "").replace("]", "");
            parts = currentCustomer.split(", ");
        });
        deleteCustomerButton.setOnAction(event -> {
            try {
                SqlOperation.deleteCustomer("Покупатели", "Имя", parts[1], parts[0]);
                executeQuery = SqlOperation.selectCustomer("Покупатели", "*", searchCustomerField.getText(), 2);
                NewTable.createTable(customerTable, executeQuery, false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        mainButton.setOnMouseEntered(event -> mainButton.setStyle("-fx-background-color: #F39C03"));
        mainButton.setOnMouseExited(event -> mainButton.setStyle("-fx-background-color: #F39C63"));
        addCustomerButton.setOnMouseEntered(event -> addCustomerButton.setStyle("-fx-background-color: #F39C03"));
        addCustomerButton.setOnMouseExited(event -> addCustomerButton.setStyle("-fx-background-color: #F39C63"));
        deleteCustomerButton.setOnMouseEntered(event -> deleteCustomerButton.setStyle("-fx-background-color: #F39C03"));
        deleteCustomerButton.setOnMouseExited(event -> deleteCustomerButton.setStyle("-fx-background-color: #F39C63"));
    }
}
