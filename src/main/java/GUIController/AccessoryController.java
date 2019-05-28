package GUIController;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Sql.SqlOperation;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Класс-контроллер окна вывода информации о комплектующи
 */
public class AccessoryController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField searchAccessoryField;

    @FXML
    private Button mainButton;

    @FXML
    private TableView accessoryTable;

    @FXML
    private ListView accessoryListView;

    @FXML
    private Button addAccessoryButton;

    @FXML
    private Button addToOrderButton;

    @FXML
    private TextArea accessoryField;

    @FXML
    private Button bmwButton;

    @FXML
    private Button audiButton;

    @FXML
    private Button seatButton;

    @FXML
    private Button opelButton;

    @FXML
    private Button fordButton;

    ResultSet executeQuery;

    ObservableList<String> tables;

    String carName;

    //Класс выводящий комплектующие
    @FXML
    void initialize() {
        if (NewOrderController.isModal) {
            mainButton.setText("К созданию заказа");
            mainButton.setOnAction(event -> {
                NewOrderController.isModal = false;
                mainButton.getScene().getWindow().hide();
            });
        } else {
            mainButton.setOnAction(event -> NewStage.createStage(mainButton, "/fxml/Main.fxml", false,"Главная"));
        }
        addAccessoryButton.setOnAction(event -> NewStage.createStage(addAccessoryButton, "/fxml/newAccessory.fxml", false,"Добавление комплектующих"));
        carName = "";
        tables = FXCollections.observableArrayList("Аккумулятор", "Дворник", "Колесо", "Радиатор", "Тормозной диск", "Шина");
        bmwButton.setOnAction(event -> {
            carName = "BMW";
            accessoryListView.setItems(tables);
        });
        audiButton.setOnAction(event -> {
            carName = "Audi";
            accessoryListView.setItems(tables);
        });
        seatButton.setOnAction(event -> {
            carName = "Seat";
            accessoryListView.setItems(tables);
        });
        opelButton.setOnAction(event -> {
            carName = "Opel";
            accessoryListView.setItems(tables);
        });
        fordButton.setOnAction(event -> {
            carName = "Ford";
            accessoryListView.setItems(tables);
        });
        //Здесь обработка нажатий по списку
        accessoryListView.setOnMouseClicked(event -> {
            searchAccessoryField.setText(accessoryListView.getSelectionModel().getSelectedItem().toString());
            try {
                if (accessoryListView.getSelectionModel().getSelectedItem().toString().equals("Тормозной диск")) {
                    executeQuery = SqlOperation.selectAccessory("[Тормозной диск]", "*", carName, "");
                } else {
                    executeQuery = SqlOperation.selectAccessory(accessoryListView.getSelectionModel().getSelectedItem().toString(), "*", carName, "");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            NewTable.createTable(accessoryTable, executeQuery, true);
        });
        searchAccessoryField.textProperty().addListener((observable, oldValue, newValue) -> {
            //здесь происходит разделение строки поиска на две части через пробел, в первой части строки название таблицы, во второй части строки название самой детали
            ArrayList<String> parts = new ArrayList<>();
            String[] partsOperation = searchAccessoryField.getText().split(" ");
            String partsSearch;
            if (partsOperation.length == 1) {
                parts.add(partsOperation[0]);
                parts.add("");
            } else {
                parts.add(partsOperation[0]);
                parts.add(partsOperation[1]);
                if (partsOperation.length == 3) {
                    parts.add(partsOperation[2]);
                } else {
                    parts.add("");
                }
            }
            if ((partsOperation[0].equals("Тормозной")) && (partsOperation[1].equals("диск"))) {
                if (partsOperation.length == 2) {
                    partsSearch = "%";
                } else {
                    partsSearch = partsOperation[2];
                }
                for (int i = 3; i < partsOperation.length; i++) {
                    partsSearch = partsSearch + " " + partsOperation[i];
                }
            } else {
                if (partsOperation.length == 1) {
                    partsSearch = "%";
                } else {
                    partsSearch = partsOperation[1];
                }
                for (int i = 2; i < partsOperation.length; i++) {
                    partsSearch = partsSearch + " " + partsOperation[i];
                }
            }
            try {
                if ((parts.get(0).equals("Тормозной")) && (parts.get(1).equals("диск"))) {
                    executeQuery = SqlOperation.selectAccessory("[Тормозной диск]", "*", carName, partsSearch);
                } else {
                    executeQuery = SqlOperation.selectAccessory(parts.get(0), "*", carName, partsSearch);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String nameSearch;
            NewTable.createTable(accessoryTable, executeQuery, true);
            if ((parts.get(0).equals("Тормозной")) && (parts.get(1).equals("диск"))) {
                nameSearch = "Тормозной диск";
            } else {
                nameSearch = parts.get(0);
            }
            accessoryTable.setOnMouseClicked(event -> accessoryField.setText(accessoryField.getText() + nameSearch + ":" + accessoryTable.getSelectionModel().getSelectedItem() + "\r\n"));
        });
        if (!NewOrderController.isModal)
        {
            accessoryField.setVisible(false);
            addToOrderButton.setVisible(false);
        }
        addToOrderButton.setOnAction(event -> {
            if (accessoryField.getText().equals("")) {
                accessoryField.setStyle("-fx-prompt-text-fill: red");
                accessoryField.setPromptText("Введите комплектующие");
            } else {
                NewOrderController.accessoryName = NewOrderController.accessoryName + accessoryField.getText();
                NewOrderController.isModal = false;
                mainButton.getScene().getWindow().hide();
            }
        });
        Platform.runLater(() -> mainButton.getScene().getWindow().setOnCloseRequest(event -> NewOrderController.isModal = false));
        mainButton.setOnMouseEntered(event -> mainButton.setStyle("-fx-background-color: #F39C03"));
        mainButton.setOnMouseExited(event -> mainButton.setStyle("-fx-background-color: #F39C63"));
        addAccessoryButton.setOnMouseEntered(event -> addAccessoryButton.setStyle("-fx-background-color: #F39C03"));
        addAccessoryButton.setOnMouseExited(event -> addAccessoryButton.setStyle("-fx-background-color: #F39C63"));
        addToOrderButton.setOnMouseEntered(event -> addToOrderButton.setStyle("-fx-background-color: #F39C03"));
        addToOrderButton.setOnMouseExited(event -> addToOrderButton.setStyle("-fx-background-color: #F39C63"));
    }
}
