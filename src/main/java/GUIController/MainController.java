package GUIController;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Sql.SqlOperation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * Класс-контроллер глваного окна приложения
 */
public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button orderButton;

    @FXML
    private Button customerButton;

    @FXML
    private Button accessoryButton;

    @FXML
    private Hyperlink changeLoginHyperLink;

    @FXML
    private Hyperlink changePasswordHyperLink;

    @FXML
    private Label nameLabel;

    @FXML
    private Label surNameLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private Label telephoneLabel;

    @FXML
    private ImageView photoImageView;

    public static String changeField;

    public static String idSeller;

    //контроллер основного окна
    @FXML
    void initialize() throws SQLException {
        orderButton.setOnAction(event -> NewStage.createStage(orderButton, "/fxml/Order.fxml", false,"Заказы"));
        customerButton.setOnAction(event -> NewStage.createStage(customerButton, "/fxml/Customer.fxml", false,"Покупатели"));
        accessoryButton.setOnAction(event -> NewStage.createStage(accessoryButton, "/fxml/Accessory.fxml", false,"Комплектующие"));
        changeLoginHyperLink.setOnAction(event -> {
            changeField = "Логин";
            NewStage.createStage(orderButton, "/fxml/ChangeLoginPassword.fxml", true,"Изменение логина");
        });
        changePasswordHyperLink.setOnAction(event -> {
            changeField = "Пароль";
            NewStage.createStage(orderButton, "/fxml/ChangeLoginPassword.fxml", true,"Изменение пароля");
        });
        ResultSet executeQuery = SqlOperation.selectSeller("Сотрудники", "*", idSeller);
        executeQuery.next();
        Image image = new Image(getClass().getResource(executeQuery.getString(7)).toExternalForm());
        photoImageView.setImage(image);
        nameLabel.setText(executeQuery.getMetaData().getColumnName(2) + ": " + executeQuery.getString(2));
        surNameLabel.setText(executeQuery.getMetaData().getColumnName(3) + ": " + executeQuery.getString(3));
        positionLabel.setText(executeQuery.getMetaData().getColumnName(6) + ": " + executeQuery.getString(6));
        telephoneLabel.setText(executeQuery.getMetaData().getColumnName(4) + ": " + executeQuery.getString(4));
        orderButton.setOnMouseEntered(event -> orderButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2)"));
        orderButton.setOnMouseExited(event -> orderButton.setStyle("-fx-background-color: #2E3348"));
        customerButton.setOnMouseEntered(event -> customerButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2)"));
        customerButton.setOnMouseExited(event -> customerButton.setStyle("-fx-background-color: #2E3348"));
        accessoryButton.setOnMouseEntered(event -> accessoryButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2)"));
        accessoryButton.setOnMouseExited(event -> accessoryButton.setStyle("-fx-background-color: #2E3348"));
    }
}
