package CollectionObjects;

import CollectionObjects.Address;
/**
 * Класс Organization представляет организацию с названием, годовым оборотом, типом и официальным адресом.
 */
public class Organization {
    private Integer id; // Уникальный идентификатор организации (может быть null)
    private String name; // Название организации
    private float annualTurnover; // Годовой оборот организации
    private OrganizationType type; // Тип организации
    private Address officialAddress; // Официальный адрес организации

    /**
     * Конструктор по умолчанию.
     */
    public Organization() {}

    /**
     * Конструктор с параметрами для инициализации организации.
     *
     * @param name название организации (не может быть null или пустой строкой).
     * @param annualTurnover годовой оборот организации (должен быть больше 0).
     * @param type тип организации (может быть null).
     * @param officialAddress официальный адрес организации (может быть null).
     */
    public Organization(String name, float annualTurnover, OrganizationType type, Address officialAddress) {
        this.name = name;
        this.annualTurnover = annualTurnover;
        this.type = type;
        this.officialAddress = officialAddress;
    }

    /**
     * Возвращает название организации.
     *
     * @return название организации (тип String).
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает название организации.
     *
     * @param name название организации (тип String, не может быть null или пустой строкой).
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает годовой оборот организации.
     *
     * @return годовой оборот организации (тип float).
     */
    public float getAnnualTurnover() {
        return annualTurnover;
    }

    /**
     * Устанавливает годовой оборот организации.
     *
     * @param annualTurnover годовой оборот организации (тип float, должен быть больше 0).
     */
    public void setAnnualTurnover(float annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    /**
     * Возвращает тип организации.
     *
     * @return тип организации (тип OrganizationType, может быть null).
     */
    public OrganizationType getType() {
        return type;
    }

    /**
     * Устанавливает тип организации.
     *
     * @param type тип организации (тип OrganizationType, может быть null).
     */
    public void setType(OrganizationType type) {
        this.type = type;
    }

    /**
     * Возвращает официальный адрес организации.
     *
     * @return официальный адрес организации (тип Address, может быть null).
     */
    public Address getOfficialAddress() {
        return officialAddress;
    }

    /**
     * Устанавливает официальный адрес организации.
     *
     * @param officialAddress официальный адрес организации (тип Address, может быть null).
     */
    public void setOfficialAddress(Address officialAddress) {
        this.officialAddress = officialAddress;
    }

    /**
     * Возвращает строковое представление объекта Organization.
     *
     * @return строковое представление объекта в формате:
     * Organization{name='название', annualTurnover=значение, type=тип, officialAddress=адрес}.
     */
    @Override
    public String toString() {
        return "Organization{" +
                "\nname='" + name + '\'' +
                "\nannualTurnover=" + annualTurnover +
                "\ntype=" + type +
                officialAddress +
                '}';
    }
}