package CollectionObjects;

import java.io.Serializable;
import java.time.LocalDate;
import CollectionObjects.*;

/**
 * Класс Product представляет продукт с различными атрибутами, такими как название, координаты, дата создания,
 * цена, стоимость производства, единица измерения и производитель.
 */
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name; // Название продукта
    private Coordinates coordinates; // Координаты продукта
    private LocalDate creationDate; // Дата создания продукта
    private double price; // Цена продукта
    private Integer manufactureCost; // Стоимость производства продукта (может быть null)
    private UnitOfMeasure unitOfMeasure; // Единица измерения продукта
    private Organization manufacturer; // Производитель продукта

    /**
     * Конструктор по умолчанию.
     */
    public Product() {
    }

    /**
     * Устанавливает название продукта.
     *
     * @param name название продукта (не может быть null или пустой строкой).
     */
    public void SetName(String name) {
        this.name = name;
    }

    /**
     * Возвращает название продукта.
     *
     * @return название продукта (тип String).
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает координаты продукта.
     *
     * @param coordinates объект класса Coordinates.
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Устанавливает дату создания продукта.
     *
     * @param creationDate дата создания продукта (тип LocalDate).
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Устанавливает цену продукта.
     *
     * @param price цена продукта (должна быть больше 0).
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Возвращает цену продукта.
     *
     * @return цена продукта (тип Double).
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Устанавливает стоимость производства продукта.
     *
     * @param manufactureCost стоимость производства продукта (тип Integer, может быть null).
     */
    public void setManufactureCost(Integer manufactureCost) {
        this.manufactureCost = manufactureCost;
    }

    /**
     * Устанавливает единицу измерения продукта.
     *
     * @param unitOfMeasure единица измерения продукта (тип UnitOfMeasure).
     */
    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    /**
     * Устанавливает производителя продукта.
     *
     * @param manufacturer объект класса Organization, представляющий производителя.
     */
    public void setManufacturer(Organization manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * Возвращает строковое представление объекта Product.
     *
     * @return строковое представление объекта в формате:
     * name='название', coordinates=координаты, creationDate=дата, price=цена,
     * manufactureCost=стоимость, unitOfMeasure=единица измерения, manufacturer=производитель.
     */
    @Override
    public String toString() {
        return "name='" + name + '\'' +
                "\ncoordinates=" + coordinates +
                "\ncreationDate=" + creationDate +
                "\nprice=" + price +
                "\nmanufactureCost=" + manufactureCost +
                "\nunitOfMeasure=" + unitOfMeasure +
                "\n" + manufacturer;
    }
}