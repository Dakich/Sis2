import java.io.Serializable;

// Абстрактный класс для гео фигур
abstract class Shape implements Serializable {
    public abstract double calculateArea();
}