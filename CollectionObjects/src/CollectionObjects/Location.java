package CollectionObjects;

/**
 * Класс Location представляет местоположение с координатами (x, y, z) и названием.
 */
public class Location {
    private long x;      // Координата x
    private double y;    // Координата y
    private Integer z;   // Координата z (может быть null)
    private String name; // Название местоположения (может быть null)

    /**
     * Конструктор по умолчанию.
     */
    public Location() {}

    /**
     * Конструктор с параметрами для инициализации координат и названия местоположения.
     *
     * @param x координата x (тип long).
     * @param y координата y (тип double).
     * @param z координата z (тип Integer, может быть null).
     * @param name название местоположения (тип String, может быть null).
     */
    public Location(long x, double y, Integer z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    /**
     * Возвращает значение координаты x.
     *
     * @return значение координаты x (тип long).
     */
    public long getX() {
        return x;
    }

    /**
     * Устанавливает значение координаты x.
     *
     * @param x значение координаты x (тип long).
     */
    public void setX(long x) {
        this.x = x;
    }

    /**
     * Возвращает значение координаты y.
     *
     * @return значение координаты y (тип double).
     */
    public double getY() {
        return y;
    }

    /**
     * Устанавливает значение координаты y.
     *
     * @param y значение координаты y (тип double).
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Возвращает значение координаты z.
     *
     * @return значение координаты z (тип Integer, может быть null).
     */
    public Integer getZ() {
        return z;
    }

    /**
     * Устанавливает значение координаты z.
     *
     * @param z значение координаты z (тип Integer, может быть null).
     */
    public void setZ(Integer z) {
        this.z = z;
    }

    /**
     * Возвращает название местоположения.
     *
     * @return название местоположения (тип String, может быть null).
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает название местоположения.
     *
     * @param name название местоположения (тип String, может быть null).
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает строковое представление объекта Location.
     *
     * @return строковое представление объекта в формате:
     * Location{x=значение, y=значение, z=значение, name='название'}.
     */
    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                "\ny=" + y +
                "\nz=" + z +
                "\nname='" + name + '\'' +
                '}';
    }
}