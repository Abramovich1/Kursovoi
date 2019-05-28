package GUIController;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Sql.SqlOperation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Класс-контроллер окна вывода информации о заказах
 */
public class OrderController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField searchOrderField;

    @FXML
    private Button mainButton;

    @FXML
    private TableView orderTable;

    @FXML
    private Button addOrderButton;

    @FXML
    private Button deleteOrderButton;

    ResultSet executeQuery;

    String currentOrder;

    String[] parts;

    //Класс выводящий клиентов
    @FXML
    void initialize() throws SQLException {
        mainButton.setOnAction(event -> NewStage.createStage(mainButton, "/fxml/Main.fxml", false,"Главная"));
        addOrderButton.setOnAction(event -> NewStage.createStage(addOrderButton, "/fxml/newOrder.fxml", false,"Добавление заказа"));
        if (searchOrderField.getText().equals("")) {
            executeQuery = SqlOperation.selectOrder("");
            NewTable.createTable(orderTable, executeQuery, false);
        }
        searchOrderField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                executeQuery = SqlOperation.selectOrder(searchOrderField.getText());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            NewTable.createTable(orderTable, executeQuery, false);
        });
        orderTable.setOnMouseClicked(event -> {
            currentOrder = orderTable.getSelectionModel().getSelectedItem().toString();
            currentOrder = currentOrder.replace("[", "").replace("]", "");
            parts = currentOrder.split(", ");
        });
        deleteOrderButton.setOnAction(event -> {
            try {
                SqlOperation.deleteOrder("Заказы", "id", parts[0]);
                executeQuery = SqlOperation.selectOrder("");
                NewTable.createTable(orderTable, executeQuery, false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        mainButton.setOnMouseEntered(event -> mainButton.setStyle("-fx-background-color: #F39C03"));
        mainButton.setOnMouseExited(event -> mainButton.setStyle("-fx-background-color: #F39C63"));
        addOrderButton.setOnMouseEntered(event -> addOrderButton.setStyle("-fx-background-color: #F39C03"));
        addOrderButton.setOnMouseExited(event -> addOrderButton.setStyle("-fx-background-color: #F39C63"));
        deleteOrderButton.setOnMouseEntered(event -> deleteOrderButton.setStyle("-fx-background-color: #F39C03"));
        deleteOrderButton.setOnMouseExited(event -> deleteOrderButton.setStyle("-fx-background-color: #F39C63"));
    }
}
