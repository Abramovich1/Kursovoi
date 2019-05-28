package GUIController;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Sql.SqlOperation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
/**
 * Класс-контроллер окна замены пароля и логина
 */
public class ChangeLoginPasswordController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label changeLabel;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField newValueField;

    @FXML
    private Button mainButton;

    @FXML
    private Button enterButton;

    @FXML
    private Label infoLabel;

    @FXML
    void initialize() {
        infoLabel.setVisible(false);
        mainButton.setOnAction(event -> mainButton.getScene().getWindow().hide());
        changeLabel.setText(changeLabel.getText() + MainController.changeField);
        if (MainController.changeField.equals("Логин")) {
            loginField.setPromptText("Введите старый логин");
            passwordField.setPromptText("Введите пароль");
            newValueField.setPromptText("Введите новый логин");
        }
        if (MainController.changeField.equals("Пароль")) {
            loginField.setPromptText("Введите логин");
            passwordField.setPromptText("Введите старый пароль");
            newValueField.setPromptText("Введите новый пароль");
        }
        enterButton.setOnAction(event -> {
            if (MainController.changeField.equals("Логин")) {
                try {
                    ResultSet executeQuery = SqlOperation.selectSeller("Сотрудники", "*", MainController.idSeller);
                    executeQuery.next();
                    if (loginField.getText().equals("")) {
                        loginField.setStyle("-fx-prompt-text-fill: red");
                        loginField.setPromptText("Введите старый логин");
                    }
                    if (passwordField.getText().equals("")) {
                        passwordField.setStyle("-fx-prompt-text-fill: red");
                        passwordField.setPromptText("Введите пароль");
                    }
                    if (newValueField.getText().equals("")) {
                        newValueField.setStyle("-fx-prompt-text-fill: red");
                        newValueField.setPromptText("Введите новый логин");
                    }
                    if (((!loginField.getText().equals("")) && (!loginField.getText().equals(executeQuery.getString(8)))) || ((!passwordField.getText().equals("")) && (!passwordField.getText().equals(executeQuery.getString(9))))) {
                        infoLabel.setVisible(true);
                    }
                    if ((loginField.getText().equals(executeQuery.getString(8))) && (passwordField.getText().equals(executeQuery.getString(9)))) {
                        SqlOperation.update("Сотрудники", "Логин", newValueField.getText(), MainController.idSeller);
                        enterButton.getScene().getWindow().hide();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (MainController.changeField.equals("Пароль")) {
                try {
                    ResultSet executeQuery = SqlOperation.selectSeller("Сотрудники", "*", MainController.idSeller);
                    executeQuery.next();
                    if (loginField.getText().equals("")) {
                        loginField.setStyle("-fx-prompt-text-fill: red");
                        loginField.setPromptText("Введите логин");
                    }
                    if (passwordField.getText().equals("")) {
                        passwordField.setStyle("-fx-prompt-text-fill: red");
                        passwordField.setPromptText("Введите старый пароль");
                    }
                    if (newValueField.getText().equals("")) {
                        newValueField.setStyle("-fx-prompt-text-fill: red");
                        newValueField.setPromptText("Введите новый пароль");
                    }
                    if (((!loginField.getText().equals("")) && (!loginField.getText().equals(executeQuery.getString(8)))) || ((!passwordField.getText().equals("")) && (!passwordField.getText().equals(executeQuery.getString(9))))) {
                        infoLabel.setVisible(true);
                    }
                    if ((loginField.getText().equals(executeQuery.getString(8))) && (passwordField.getText().equals(executeQuery.getString(9)))) {
                        SqlOperation.update("Сотрудники", "Логин", newValueField.getText(), MainController.idSeller);
                        enterButton.getScene().getWindow().hide();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
