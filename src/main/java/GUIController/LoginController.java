package GUIController;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import FileOperation.*;
import Sql.SqlOperation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Класс-контролеер формы авторизации
 */
public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitButton;

    @FXML
    private Button enterButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label infoLabel;

    //контроллер входа
    @FXML
    void initialize() {
        infoLabel.setVisible(false);
        enterButton.setOnAction(event -> {
            if (loginField.getText().equals("")) {
                loginField.setStyle("-fx-prompt-text-fill: red");
                loginField.setPromptText("Введите логин");
            }
            if (passwordField.getText().equals("")) {
                passwordField.setStyle("-fx-prompt-text-fill: red");
                passwordField.setPromptText("Введите пароль");
            }
            DataSourceDecorator encoded = new CompressionDecorator(
                    new EncryptionDecorator(
                            new FileDataSource("Login.txt")));
            String loginText = encoded.readData();
            String[] parts = loginText.split("\n");
            if(loginField.getText().equals(parts[0]) && passwordField.getText().equals(parts[1]))
                NewStage.createStage(enterButton, "/fxml/Employees.fxml", false,"Редактирование сотрудников");
            else {
                try {
                    ResultSet executeQuery = SqlOperation.selectSeller("Сотрудники", "*", "");
                    while (executeQuery.next()) {
                        if ((loginField.getText().equals(executeQuery.getString(8))) && (passwordField.getText().equals(executeQuery.getString(9)))) {
                            MainController.idSeller = executeQuery.getString(1);
                            NewStage.createStage(enterButton, "/fxml/Main.fxml", false,"Главная");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if ((!loginField.getText().equals("")) && (!passwordField.getText().equals(""))) {
                infoLabel.setVisible(true);
            }
        });
        exitButton.setOnAction(event -> enterButton.getScene().getWindow().hide());
        enterButton.setOnMouseEntered(event -> enterButton.setStyle("-fx-background-color: #F39C03"));
        enterButton.setOnMouseExited(event -> enterButton.setStyle("-fx-background-color: #F39C63"));
        exitButton.setOnMouseEntered(event -> exitButton.setStyle("-fx-background-color: #F39C03"));
        exitButton.setOnMouseExited(event -> exitButton.setStyle("-fx-background-color: #F39C63"));
    }
}
