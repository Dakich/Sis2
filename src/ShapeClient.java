import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ShapeClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345)) {
            System.out.println("Подключено к серверу");

            // Потоки для ввода/вывода
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Введите тип фигуры (Circle, Rectangle) или Q для выхода:");
                String shapeType = scanner.nextLine();

                if (shapeType.equalsIgnoreCase("Q")) {
                    // Отправляем серверу сигнал о завершении работы
                    outputStream.writeObject(null);
                    break;
                }

                Shape shape = null;
                if (shapeType.equalsIgnoreCase("Circle")) {
                    System.out.print("Введите радиус круга: ");
                    double radius = scanner.nextDouble();
                    shape = new Circle(radius);
                } else if (shapeType.equalsIgnoreCase("Rectangle")) {
                    System.out.print("Введите длину прямоугольника: ");
                    double length = scanner.nextDouble();
                    System.out.print("Введите ширину прямоугольника: ");
                    double width = scanner.nextDouble();
                    shape = new Rectangle(length, width);
                } else {
                    System.out.println("Неверный тип фигуры.");
                    continue;
                }

                // Отправляем фигуру на сервер
                outputStream.writeObject(shape);

                // Получаем площадь от сервера
                double area = inputStream.readDouble();
                System.out.println("Площадь фигуры: " + area);

                // Ожидаем следующего ввода
                scanner.nextLine(); // Чистим буфер
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
