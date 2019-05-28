package GUIController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
/**
 * Класс-контроллер окна добавления комплектующей
 */
public class NewAccessoryController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button accessoryButton;

    @FXML
    private TextField searchAccessoryField;

    @FXML
    private Button searchAccessoryButton;

    @FXML
    private TableView accessoryTable;

    public static String request;

    public static String response;

    //Добавление комплектующих
    @FXML
    void initialize() {
        accessoryButton.setOnAction(event -> NewStage.createStage(accessoryButton, "/fxml/Accessory.fxml", false,"комплектующие"));
        searchAccessoryButton.setOnAction(event -> {
            request = searchAccessoryField.getText();
            String[] partsRequest = request.split(" ");
            if ((partsRequest[0].equals("Тормозной")) && (partsRequest[1].equals("диск"))) {
                if (partsRequest.length == 2) {
                    request = partsRequest[0] + " " + partsRequest[1] + ":" + "%";
                } else {
                    request = partsRequest[0] + " " + partsRequest[1] + ":" + partsRequest[2];
                }
                for (int i = 3; i < partsRequest.length; i++) {
                    request = request + " " + partsRequest[i];
                }
            } else {
                if (partsRequest.length == 1) {
                    request = partsRequest[0] + ":" + "%";
                } else {
                    request = partsRequest[0] + ":" + partsRequest[1];
                }
                for (int i = 2; i < partsRequest.length; i++) {
                    request = request + " " + partsRequest[i];
                }
            }
            Network.ServerConnection.createConnection();
            ObservableList<ObservableList> data = FXCollections.observableArrayList();
            accessoryTable.getColumns().clear();
            response = response.replace("[", "").replace("]", "");
            String[] parts = response.split(":, ");
            String[] columns = parts[0].split(", ");
            String[] rows = parts[1].split(", ");
            try {
                for (int i = 0; i < columns.length; i++) {
                    final int j = i;
                    TableColumn column = new TableColumn(columns[i]);
                    column.setPrefWidth(accessoryTable.getPrefWidth() / columns.length);
                    column.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));
                    accessoryTable.getColumns().addAll(column);
                }
                int k = 0;
                for (int i = 0 ; i < rows.length / columns.length; i++) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int j = 0 ; j < columns.length; j++) {
                        row.add(rows[k]);
                        k = k + 1;
                    }
                    data.add(row);
                }
                accessoryTable.setItems(data);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Ошибка создания таблицы");
            }
        });
        accessoryButton.setOnMouseEntered(event -> accessoryButton.setStyle("-fx-background-color: #F39C03"));
        accessoryButton.setOnMouseExited(event -> accessoryButton.setStyle("-fx-background-color: #F39C63"));
        searchAccessoryButton.setOnMouseEntered(event -> searchAccessoryButton.setStyle("-fx-background-color: #F39C03"));
        searchAccessoryButton.setOnMouseExited(event -> searchAccessoryButton.setStyle("-fx-background-color: #F39C63"));
    }
}
