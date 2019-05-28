package GUIController;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Sql.SqlOperation;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
/**
 * Класс-контроллер окна добавления покупателей
 */
public class NewCustomerContoller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView customerTable;

    @FXML
    private TextField nameField;

    @FXML
    private TextField telephoneField;

    @FXML
    private TextField emailField;

    @FXML
    private Button customerButton;

    @FXML
    private Button addCustomerButton;

    ResultSet executeQuery;

    String currentCustomer;

    //Добавление клиента
    @FXML
    void initialize() throws SQLException {
        if (NewOrderController.isModal) {
            customerButton.setText("К созданию заказа");
            customerButton.setOnAction(event -> {
                NewOrderController.isModal = false;
                customerButton.getScene().getWindow().hide();
            });
        } else {
            customerButton.setOnAction(event -> NewStage.createStage(customerButton, "/fxml/Customer.fxml", false,"Покупатели"));
        }
        executeQuery = SqlOperation.selectCustomer("Покупатели", "*", "", 2);
        NewTable.createTable(customerTable, executeQuery, false);
        currentCustomer = "";
        customerTable.setOnMouseClicked(event -> currentCustomer = customerTable.getSelectionModel().getSelectedItem().toString());
        addCustomerButton.setOnAction(event -> {
            if (nameField.getText().equals("")) {
                nameField.setStyle("-fx-prompt-text-fill: red");
                nameField.setPromptText("Введите имя");
            }
            if (telephoneField.getText().equals("")) {
                telephoneField.setStyle("-fx-prompt-text-fill: red");
                telephoneField.setPromptText("Введите телефон");
            }
            if (emailField.getText().equals("")) {
                emailField.setStyle("-fx-prompt-text-fill: red");
                emailField.setPromptText("Введите email");
            }
            if ((!nameField.getText().equals("")) && (!telephoneField.getText().equals("")) && (!emailField.getText().equals(""))) {
                String values = "('" + nameField.getText() + "', '" + telephoneField.getText() + "', '" + emailField.getText() + "')";
                try {
                        SqlOperation.insert("Покупатели", values);
                    executeQuery = SqlOperation.selectCustomer("Покупатели", "*", "", 2);
                    NewTable.createTable(customerTable, executeQuery, false);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (NewOrderController.isModal) {
                if ((!nameField.getText().equals("")) && (!telephoneField.getText().equals("")) && (!emailField.getText().equals(""))) {
                    NewOrderController.customerName = customerTable.getItems().get(customerTable.getItems().size() - 1).toString();
                    NewOrderController.isModal = false;
                    customerButton.getScene().getWindow().hide();
                } else if((!currentCustomer.equals("")) && (nameField.getText().equals("")) && (telephoneField.getText().equals("")) && (emailField.getText().equals(""))) {
                    NewOrderController.customerName = currentCustomer;
                    NewOrderController.isModal = false;
                    customerButton.getScene().getWindow().hide();
                }
            }
        });
        Platform.runLater(() -> customerButton.getScene().getWindow().setOnCloseRequest(event -> NewOrderController.isModal = false));
        customerButton.setOnMouseEntered(event -> customerButton.setStyle("-fx-background-color: #F39C03"));
        customerButton.setOnMouseExited(event -> customerButton.setStyle("-fx-background-color: #F39C63"));
        addCustomerButton.setOnMouseEntered(event -> addCustomerButton.setStyle("-fx-background-color: #F39C03"));
        addCustomerButton.setOnMouseExited(event -> addCustomerButton.setStyle("-fx-background-color: #F39C63"));
    }
}
