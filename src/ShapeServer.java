import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ShapeServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Сервер ожидает подключения:");

            // Принимаем подключение от клиента
            try (Socket socket = serverSocket.accept()) {
                System.out.println("Клиент подключен");

                // Потоки для ввода/вывода
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

                while (true) {
                    // Получаем объект фигуры от клиента
                    Shape shape = (Shape) inputStream.readObject();
                    if (shape == null) break;  // Если получена пустая фигура, завершаем работу

                    // Рассчитываем площадь фигуры
                    double area = shape.calculateArea();

                    // Отправляем площадь обратно клиенту
                    outputStream.writeDouble(area);
                }
            } catch (EOFException e) {
                System.out.println("Клиент отключился");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
