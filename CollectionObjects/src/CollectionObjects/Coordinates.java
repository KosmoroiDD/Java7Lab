package CollectionObjects;

/**
 * Класс Coordinates представляет координаты с двумя значениями: x (тип float) и y (тип Long).
 */
public class Coordinates {
    private float x; // Координата x
    private Long y;  // Координата y

    /**
     * Конструктор с параметрами для инициализации координат.
     *
     * @param x значение координаты x (тип float).
     * @param y значение координаты y (тип Long).
     */
    public Coordinates(float x, Long y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Возвращает значение координаты x.
     *
     * @return значение координаты x (тип float).
     */
    public float getX() {
        return x;
    }

    /**
     * Устанавливает значение координаты x.
     *
     * @param x значение координаты x (тип float).
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Возвращает значение координаты y.
     *
     * @return значение координаты y (тип Long).
     */
    public Long getY() {
        return y;
    }

    /**
     * Устанавливает значение координаты y.
     *
     * @param y значение координаты y (тип Long).
     */
    public void setY(Long y) {
        this.y = y;
    }

    /**
     * Возвращает строковое представление объекта Coordinates.
     *
     * @return строковое представление объекта в формате: Coordinates{x=значение, y=значение}.
     */
    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}