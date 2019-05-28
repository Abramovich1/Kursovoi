package GUIController;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.ResultSet;

/**
 * Класс создания таблицы на окне приложения
 */
public class NewTable {

    /**
     * Метод доабляющий в существующую таблицу запесей из базы данных
     * @param tableView представление таблицы в которую нужно добавить данные
     * @param executeQuery результат запроса Select
     * @param isAccessory Так как нам не нужно выводить одно из полей в комплектующих, то используя параметр указывающий на то, содержит ли таблица комплекткющие
     */
    public static void createTable(TableView tableView, ResultSet executeQuery, boolean isAccessory) {
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        int columnCount;
        tableView.getColumns().clear();
        try {
            for (int i = 0; i < executeQuery.getMetaData().getColumnCount(); i++) {
                final int j = i;
                if (isAccessory) {
                    columnCount = executeQuery.getMetaData().getColumnCount() - 1;
                    if (i == executeQuery.getMetaData().getColumnCount() - 2) {
                        continue;
                    }
                } else {
                    columnCount = executeQuery.getMetaData().getColumnCount();
                }
                TableColumn column = new TableColumn(executeQuery.getMetaData().getColumnName(i + 1));
                column.setPrefWidth(tableView.getPrefWidth() / columnCount);
                column.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));
                tableView.getColumns().addAll(column);
            }
            while (executeQuery.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1 ; i <= executeQuery.getMetaData().getColumnCount(); i++) {
                    row.add(executeQuery.getString(i));
                }
                data.add(row);
            }
            tableView.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка создания таблицы");
        }
    }
}
