package GUIController;

import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Sql.SqlOperation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import javax.swing.*;
/**
 * Класс-контроллер окна вывода информации о сотрудниках
 */
public class EmployeesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView employeesTable;

    @FXML
    private Button deleteElployees;

    @FXML
    private Button addElployees;

    @FXML
    private TextField FirstName;

    @FXML
    private TextField SecondName;

    @FXML
    private TextField Phone;

    @FXML
    private TextField Count;

    @FXML
    private TextField Position;

    @FXML
    private TextField Photo;

    @FXML
    private TextField Login;

    @FXML
    private TextField Password;

    @FXML
    private Label label;

    @FXML
    private Button openPhoto;

    ResultSet executeQuery;
    @FXML
    void initialize() throws SQLException{


        addTable();

        openPhoto.setOnAction(event -> {
            JFileChooser fileopen = new JFileChooser();
            int ret = fileopen.showDialog(null, "Открыть файл");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileopen.getSelectedFile();
                Photo.setText(file.getAbsolutePath());
            }
        });

        deleteElployees.setOnAction(event -> {
            try {
                ObservableList<Data.Employees> productSelected, allProducts;
                allProducts = employeesTable.getItems();
                productSelected = employeesTable.getSelectionModel().getSelectedItems();
                System.out.println(productSelected.get(0).getId());
                SqlOperation.deleteEmployees("Сотрудники","id",(productSelected.get(0).getId()));
                productSelected.forEach(allProducts::remove);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        addElployees.setOnAction(event ->
        {
            if (FirstName.getText().isEmpty() || SecondName.getText().isEmpty() || Count.getText().isEmpty()
                    || Position.getText().isEmpty() || Login.getText().isEmpty() || Password.getText().isEmpty())
            {
                label.setText("Одно из полей пустое");
                label.setVisible(true);
            }
            else {
                int Count1 = Integer.parseInt(Count.getText());
                if (Photo.getText().isEmpty() || Photo.getText().equals("")) {
                    Photo.setText("/Images/Default.jpeg");
                }
                String values = "('" + FirstName.getText() + "','" + SecondName.getText() + "','" + Phone.getText() + "'," + Count.getText()
                        + ",'" + Position.getText() + "','" + Photo.getText() + "','" + Login.getText() + "','" + Password.getText() + "')";
                try {
                    SqlOperation.insert("Сотрудники", values);
                    executeQuery = SqlOperation.selectEmployess("Сотрудники", "*");
                    addTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void addTable() throws SQLException {
        executeQuery = SqlOperation.selectEmployess("Сотрудники", "*");
        ObservableList<Data.Employees> employees = FXCollections.observableArrayList();
        while (executeQuery.next()) {
            employees.add(Data.Employees.newBuilder().setId(executeQuery.getInt(1)).setFirstName(executeQuery.getString(2)).setSecondName(executeQuery.getString(3))
                    .setPhone(executeQuery.getString(4)).setCount(executeQuery.getInt(5)).setPosition(executeQuery.getString(6)).setPhoto(executeQuery.getString(7))
                    .setLogin(executeQuery.getString(8)).setPassword(executeQuery.getString(9)).build());
        }
        employeesTable.setItems(employees);
        employeesTable.setEditable(true);

        String[] Name = {"id","Имя","Фамилия","Телефон","Количество заказов","Должность","Путь к фото","Логин","Пороль"};
        String[] NameDB = {"id","FirstName","SecondName","Phone","Count","Position","Photo","Login","Password"};
        for(int i=0;i<8;i++) {
            TableColumn<Data.Employees, Integer> nameColumn1 = new TableColumn<>(Name[i]);
            nameColumn1.setEditable(true);
            nameColumn1.setMinWidth(200);
            nameColumn1.setCellValueFactory(new PropertyValueFactory<>(NameDB[i]));
            if( i == 4 || i==0)
            {
                int finalI2 = i;
                int finalI3 = i;
                nameColumn1.setOnEditCommit(
                        (TableColumn.CellEditEvent<Data.Employees, Integer> t) -> {
                            try {
                                SqlOperation.updateEmployees(Name[finalI2], t.getNewValue(), t.getOldValue());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            if(finalI3 ==4)
                                t.getTableView().getItems().get(t.getTablePosition().getRow()).newBuilder().setCount(t.getNewValue()).build();
                            else
                                t.getTableView().getItems().get(t.getTablePosition().getRow()).newBuilder().setId(t.getNewValue()).build();
                        });

                employeesTable.getColumns().add(nameColumn1);
            }

            else {
                TableColumn<Data.Employees, String> nameColumn = new TableColumn<>(Name[i]);
                nameColumn.setEditable(true);
                nameColumn.setMinWidth(200);
                nameColumn.setCellValueFactory(new PropertyValueFactory<>(NameDB[i]));
                nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                int finalI = i;
                int finalI1 = i;
                nameColumn.setOnEditCommit(
                        (TableColumn.CellEditEvent<Data.Employees, String> t) -> {
                            try {
                                SqlOperation.updateEmployees(Name[finalI1], t.getNewValue(), t.getOldValue());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            if (finalI == 1)
                                t.getTableView().getItems().get(t.getTablePosition().getRow()).newBuilder().setFirstName(t.getNewValue()).build();
                            if (finalI == 2)
                                t.getTableView().getItems().get(t.getTablePosition().getRow()).newBuilder().setSecondName(t.getOldValue()).build();
                            if (finalI == 3)
                                t.getTableView().getItems().get(t.getTablePosition().getRow()).newBuilder().setPhone(t.getOldValue()).build();
                            if (finalI == 5)
                                t.getTableView().getItems().get(t.getTablePosition().getRow()).newBuilder().setPosition(t.getOldValue()).build();
                            if (finalI == 6)
                                t.getTableView().getItems().get(t.getTablePosition().getRow()).newBuilder().setPhoto(t.getOldValue()).build();
                            if (finalI == 7)
                                t.getTableView().getItems().get(t.getTablePosition().getRow()).newBuilder().setLogin(t.getOldValue()).build();
                            if (finalI == 8)
                                t.getTableView().getItems().get(t.getTablePosition().getRow()).newBuilder().setPassword(t.getOldValue()).build();
                        });

                employeesTable.getColumns().add(nameColumn);
            }
        }
    }
}

