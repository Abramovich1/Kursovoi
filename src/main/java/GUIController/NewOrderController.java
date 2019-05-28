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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Класс-контроллер окна добавления заказа
 */
public class NewOrderController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button orderButton;

    @FXML
    private TableView orderTable;

    @FXML
    private Button addOrderButton;

    @FXML
    private Button addAccessoryButton;

    @FXML
    private Button addCustomerButton;

    @FXML
    private TextArea accessoryField;

    @FXML
    private TextField customerField;

    ResultSet executeQuery;

    public static String accessoryName;

    public static String customerName;

    String[] accessoryParts;

    String[] customerParts;

    String[] accessoryPartsRow;

    public static boolean isModal;

    //Добавление заказа
    @FXML
    void initialize() throws SQLException {
        orderButton.setOnAction(event -> NewStage.createStage(orderButton, "/fxml/Order.fxml", false,"Заказы"));
        executeQuery = SqlOperation.selectOrder("");
        NewTable.createTable(orderTable, executeQuery, false);
        addAccessoryButton.setOnAction(event -> {
            isModal = true;
            NewStage.createStage(addAccessoryButton, "/fxml/Accessory.fxml", true,"Добавление комплектующие");
        });
        addCustomerButton.setOnAction(event -> {
            isModal = true;
            NewStage.createStage(addCustomerButton, "/fxml/NewCustomer.fxml", true,"Добавление покупателя");
        });
        addOrderButton.setOnAction(event -> {
            if (accessoryField.getText().equals("")) {
                accessoryField.setStyle("-fx-prompt-text-fill: red");
                accessoryField.setPromptText("Введите комплектующие");
            }
            if (customerField.getText().equals("")) {
                customerField.setStyle("-fx-prompt-text-fill: red");
                customerField.setPromptText("Введите клиента");
            }
            if ((!accessoryField.getText().equals("")) && (!customerField.getText().equals(""))) {
                for (int i = 0; i < accessoryPartsRow.length; i++) {
                    accessoryParts = accessoryPartsRow[i].split(":|, ");
                    String values = "('" + accessoryParts[0] + "', '" + accessoryParts[1] + "', '" + customerParts[0] + "', '" + MainController.idSeller + "', '" + accessoryParts[accessoryParts.length - 1] + "')";
                    try {
                        SqlOperation.insert("Заказы", values);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    executeQuery = SqlOperation.selectOrder("");
                    NewTable.createTable(orderTable, executeQuery, false);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        accessoryName = "";
        customerName = "";
        Platform.runLater(() -> {
            Stage stage = (Stage)orderButton.getScene().getWindow();
            stage.focusedProperty().addListener((ov, onHidden, onShown) -> {
                accessoryName = accessoryName.replace("[", "").replace("]", "");
                customerName = customerName.replace("[", "").replace("]", "");
                accessoryPartsRow = accessoryName.split("\n");
                accessoryField.setText("");
                float sum = 0;
                for (int i = 0; i < accessoryPartsRow.length; i++) {
                    accessoryParts = accessoryPartsRow[i].split(":|, ");
                    if (!accessoryName.equals("")) {
                        accessoryField.setText(accessoryField.getText() + accessoryParts[0] + " " + accessoryParts[2] + " " + accessoryParts[accessoryParts.length - 1] + "\r\n");
                        sum = sum + Float.parseFloat(accessoryParts[accessoryParts.length - 1]);
                    }
                }
                if (!accessoryField.getText().equals("")) {
                    accessoryField.setText(accessoryField.getText() + "Общая сумма " + sum);
                }
                customerParts = customerName.split(", ");
                if (!customerName.equals("")) {
                    customerField.setText(customerParts[1]);
                }
            });
        });
        orderButton.setOnMouseEntered(event -> orderButton.setStyle("-fx-background-color: #F39C03"));
        orderButton.setOnMouseExited(event -> orderButton.setStyle("-fx-background-color: #F39C63"));
        addOrderButton.setOnMouseEntered(event -> addOrderButton.setStyle("-fx-background-color: #F39C03"));
        addOrderButton.setOnMouseExited(event -> addOrderButton.setStyle("-fx-background-color: #F39C63"));
    }
}
