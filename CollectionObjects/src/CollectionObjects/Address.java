package CollectionObjects;

/**
 * Класс Address представляет адрес с улицей и местоположением (городом).
 */
public class Address {
    private String street; // Название улицы
    private Location town; // Местоположение (город)

    /**
     * Конструктор по умолчанию.
     */
    public Address() {}

    /**
     * Конструктор с параметрами для инициализации улицы и местоположения.
     *
     * @param street название улицы.
     * @param town объект класса Location, представляющий местоположение (город).
     */
    public Address(String street, Location town) {
        this.street = street;
        this.town = town;
    }

    /**
     * Возвращает название улицы.
     *
     * @return название улицы.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Устанавливает название улицы.
     *
     * @param street название улицы.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Возвращает объект местоположения (город).
     *
     * @return объект класса Location.
     */
    public Location getTown() {
        return town;
    }

    /**
     * Устанавливает объект местоположения (город).
     *
     * @param town объект класса Location.
     */
    public void setTown(Location town) {
        this.town = town;
    }

    /**
     * Возвращает строковое представление объекта Address.
     *
     * @return строковое представление объекта в формате:
     * Address{street='название улицы', town=объект Location}.
     */
    @Override
    public String toString() {
        return "\nAddress{" +
                "street='" + street + '\'' +
                "\ntown=" + town +
                '}';
    }
}