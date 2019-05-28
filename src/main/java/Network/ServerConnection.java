package Network;

import GUIController.NewAccessoryController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Класс соединеня с удаленным сервером
 */
public class ServerConnection {
    /**
     * Метод подключения к серверу
     */
    public static void createConnection() {
        try (Socket socket = new Socket("localhost", 3345);
             DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
             DataInputStream ois = new DataInputStream(socket.getInputStream())) {

            System.out.println("Client connected to socket.");
            System.out.println();
            System.out.println("Client writing channel = oos & reading channel = ois initialized.");

// данные появились - работаем
            System.out.println("Client start writing in channel...");
            //Thread.sleep(1000);
            String clientCommand = NewAccessoryController.request;

// пишем данные с консоли в канал сокета для сервера
            oos.writeUTF(clientCommand);
            oos.flush();
            System.out.println("Clien sent message " + clientCommand + " to server.");
            //Thread.sleep(1000);
// ждём чтобы сервер успел прочесть сообщение из сокета и ответить

// проверяем условие выхода из соединения
            if(clientCommand.equalsIgnoreCase("quit")){

// если условие выхода достигнуто разъединяемся
                System.out.println("Client kill connections");
                //Thread.sleep(2000);

// смотрим что нам ответил сервер на последок перед закрытием ресурсов
                System.out.println("reading...");
                String in = ois.readUTF();
                System.out.println(in);

// после предварительных приготовлений выходим из цикла записи чтения
            }

// если условие разъединения не достигнуто продолжаем работу
            System.out.println("Client sent message & start waiting for data from server...");
            //Thread.sleep(2000);

// проверяем, что нам ответит сервер на сообщение(за предоставленное ему время в паузе он должен был успеть ответить)

// если успел забираем ответ из канала сервера в сокете и сохраняем её в ois переменную,  печатаем на свою клиентскую консоль
            System.out.println("reading...");
            NewAccessoryController.response = ois.readUTF();
            System.out.println(NewAccessoryController.response);
// на выходе из цикла общения закрываем свои ресурсы
            System.out.println("Closing connections & channels on clentSide - DONE.");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
