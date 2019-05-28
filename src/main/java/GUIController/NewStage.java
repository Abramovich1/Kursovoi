package GUIController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Класс создания новой сцены приложения
 */
public class NewStage {

    /**
     * Функция создающая новую сцену
     * @param button кнопка, передающаяся как праметр для скрытия сцены с которой осуществляется переход
     * @param fxmlName имя fxml файла
     * @param isModal булевская переменная определяющая модальное ли окно
     * @param title заголовок окна
     */
    public static void createStage(Button button, String fxmlName, boolean isModal,String title) {
        if (!isModal) {
            button.getScene().getWindow().hide();
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(NewStage.class.getResource(fxmlName));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }
}

