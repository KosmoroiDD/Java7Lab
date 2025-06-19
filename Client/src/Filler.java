import CollectionObjects.*;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Класс Filler отвечает за заполнение объекта класса Product данными, введенными пользователем.
 */
public class Filler {

    public static Product createProduct() {
        try {
            // Ввод данных для Product
            System.out.println("Введите имя продукта (не может быть null и пустой строкой):");
            Scanner scanner = new Scanner(System.in);
            String name = scanner.nextLine().trim();
            while (name.isEmpty()) {
                System.out.println("Ошибка: Имя не может быть null или пустой строкой.");
                name = scanner.nextLine().trim();
            }

            Coordinates coordinates = createCoordinates(scanner);

            double price = getValidDouble(scanner, "Введите цену продукта (должна быть больше 0):", 0);
            if (price <= 0) {
                System.out.println("Ошибка: Цена должна быть больше 0.");
                return null;
            }

            Integer manufactureCost = getValidInteger(scanner, "Введите стоимость производства (может быть null и не больше 2147483647):", null);

            UnitOfMeasure unitOfMeasure = getUnitOfMeasure(scanner);

            Organization manufacturer = createOrganization(scanner);
            if (manufacturer == null) {
                return null;
            }

            // Создание объекта Product
            Product product = new Product();
            product.SetName(name);
            product.setCoordinates(coordinates);
            product.setCreationDate(LocalDate.now()); // Автоматическая генерация даты
            product.setPrice(price);
            product.setManufactureCost(manufactureCost);
            product.setUnitOfMeasure(unitOfMeasure);
            product.setManufacturer(manufacturer);
            return product;

        } catch (InputMismatchException e) {
            System.out.println("Ошибка: Неверный формат ввода.");
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
            return null;
        }
    }

    /**
     * Метод createCoordinates отвечает за заполнение объекта класса Coordinates.
     *
     * @param scanner объект Scanner для считывания ввода пользователя.
     * @return возвращает объект класса Coordinates, если данные введены корректно, иначе возвращает null.
     */
    private static Coordinates createCoordinates(Scanner scanner) {
        try {
            System.out.println("Введите координату X (максимальное значение 918):");
            float x = scanner.nextFloat();
            while (x > 918) {
                System.out.println("Ошибка: Координата X не может быть больше 918 или равняться null!");
            }

            System.out.println("Введите координату Y (не может быть null и больше 9223372036854775807):");
            Long y = scanner.nextLong();
            scanner.nextLine(); // Очистка буфера сканера

            return new Coordinates(x, y);
        } catch (InputMismatchException e) {
            System.out.println("Ошибка: Неверный формат ввода для координат.");
            scanner.nextLine(); // Очистка буфера сканера
            return createCoordinates(scanner);
        }
    }

    /**
     * Метод getUnitOfMeasure отвечает за выбор единицы измерения из перечисления UnitOfMeasure.
     *
     * @param scanner объект Scanner для считывания ввода пользователя.
     * @return возвращает объект UnitOfMeasure, если данные введены корректно, иначе возвращает null.
     */
    private static UnitOfMeasure getUnitOfMeasure(Scanner scanner) {
        System.out.println("Выберите единицу измерения (KILOGRAMS, METERS, SQUARE_METERS, MILLILITERS, MILLIGRAMS или введите null):");
        String unitStr = scanner.nextLine().trim().toUpperCase();
        if (unitStr.equals("NULL") || unitStr.isEmpty()) {
            return null;
        }
        try {
            return UnitOfMeasure.valueOf(unitStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: Неверная единица измерения.");
            scanner.nextLine();
            return getUnitOfMeasure(scanner);
        }
    }

    /**
     * Метод createOrganization отвечает за заполнение объекта класса Organization.
     *
     * @param scanner объект Scanner для считывания ввода пользователя.
     * @return возвращает объект класса Organization, если данные введены корректно, иначе возвращает null.
     */
    private static Organization createOrganization(Scanner scanner) {
        try {
            System.out.println("Введите название организации (не может быть null и пустой строкой):");
            String name = scanner.nextLine().trim();
            if (name == null || name.isEmpty()) {
                System.out.println("Ошибка: Название организации не может быть null или пустой строкой.");
                scanner.nextLine();
                return createOrganization(scanner);
            }

            float annualTurnover = getValidFloat(scanner, "Введите годовой оборот организации (должен быть больше 0 и до 3.4028235E38):", 0);
            if (annualTurnover <= 0) {
                System.out.println("Ошибка: Годовой оборот должен быть больше 0 и до 3.4028235E38.");
                return null;
            }

            OrganizationType type = getOrganizationType(scanner);

            Address officialAddress = createAddress(scanner);
            if (officialAddress == null) {
                return null;
            }

            Organization organization = new Organization();
            organization.setName(name);
            organization.setAnnualTurnover(annualTurnover);
            organization.setType(type);
            organization.setOfficialAddress(officialAddress);

            return organization;
        } catch (InputMismatchException e) {
            System.out.println("Ошибка: Неверный формат ввода для организации.");
            scanner.nextLine(); // Очистка буфера сканера
            return null;
        }
    }

    /**
     * Метод getOrganizationType отвечает за выбор типа организации из перечисления OrganizationType.
     *
     * @param scanner объект Scanner для считывания ввода пользователя.
     * @return возвращает объект OrganizationType, если данные введены корректно, иначе возвращает null.
     */
    private static OrganizationType getOrganizationType(Scanner scanner) {
        System.out.println("Выберите тип организации (COMMERCIAL, PUBLIC, GOVERNMENT, TRUST, PRIVATE_LIMITED_COMPANY или введите null):");
        String typeStr = scanner.nextLine().trim().toUpperCase();
        if (typeStr.equals("NULL") || typeStr.isEmpty()) {
            return null;
        }
        try {
            return OrganizationType.valueOf(typeStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: Неверный тип организации.");
            scanner.nextLine();
            return getOrganizationType(scanner);
        }
    }

    /**
     * Метод createAddress отвечает за заполнение объекта класса Address.
     *
     * @param scanner объект Scanner для считывания ввода пользователя.
     * @return возвращает объект класса Address, если данные введены корректно, иначе возвращает null.
     */
    private static Address createAddress(Scanner scanner) {
        try {
            System.out.println("Введите улицу (не может быть null и пустой строкой):");
            String street = scanner.nextLine().trim();
            while (street == null || street.isEmpty()) {
                System.out.println("Ошибка: Улица не может быть null или пустой строкой.");
                street = scanner.nextLine().trim();
            }

            Location town = createLocation(scanner);
            if (town == null) {
                return null;
            }

            return new Address(street, town);
        } catch (InputMismatchException e) {
            System.out.println("Ошибка: Неверный формат ввода для адреса.");
            scanner.nextLine(); // Очистка буфера сканера
            return null;
        }
    }

    /**
     * Метод createLocation отвечает за заполнение объекта класса Location.
     *
     * @param scanner объект Scanner для считывания ввода пользователя.
     * @return возвращает объект класса Location, если данные введены корректно, иначе возвращает null.
     */
    private static Location createLocation(Scanner scanner) {
        try {
            System.out.println("Введите координату X для города (до 9223372036854775807 )");
            long x = scanner.nextLong();

            System.out.println("Введите координату Y для города:");
            double y = scanner.nextDouble();

            System.out.println("Введите координату Z для города (не может быть null и больше 2147483647):");
            Integer z = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера сканера

            System.out.println("Введите название города (может быть null или пустой):");
            String name = scanner.nextLine();

            return new Location(x, y, z, name);
        } catch (InputMismatchException e) {
            System.out.println("Ошибка: Неверный формат ввода для координат города.");
            scanner.nextLine(); // Очистка буфера сканера
            return createLocation(scanner);
        }
    }

    /**
     * Метод getValidDouble проверяет, что введенное значение является числом с плавающей точкой и больше минимального значения.
     *
     * @param scanner объект Scanner для считывания ввода пользователя.
     * @param prompt сообщение, которое выводится пользователю для ввода данных.
     * @param minValue минимальное значение, которое должно быть введено.
     * @return возвращает введенное значение, если оно корректно.
     */
    private static double getValidDouble(Scanner scanner, String prompt, double minValue) {
        double value = 0;
        boolean isValid = false;
        while (!isValid) {
            try {
                System.out.println(prompt);
                value = scanner.nextDouble();
                if (value > minValue) {
                    isValid = true;
                } else {
                    System.out.println("Ошибка: Значение должно быть больше " + minValue + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: Неверный формат ввода. Введите число с плавающей точкой.");
                scanner.nextLine(); // Очистка буфера сканера
            } finally {
                scanner.nextLine(); // Очистка буфера сканера
            }
        }
        return value;
    }

    /**
     * Метод getValidInteger проверяет, что введенное значение является целым числом или null.
     *
     * @param scanner объект Scanner для считывания ввода пользователя.
     * @param prompt сообщение, которое выводится пользователю для ввода данных.
     * @param defaultValue значение по умолчанию, которое будет возвращено, если пользователь введет "null".
     * @return возвращает введенное значение, если оно корректно.
     */
    private static Integer getValidInteger(Scanner scanner, String prompt, Integer defaultValue) {
        Integer value = defaultValue;
        boolean isValid = false;
        while (!isValid) {
            try {
                System.out.println(prompt);
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("null") || input.isEmpty()) {
                    isValid = true;
                } else {
                    value = Integer.parseInt(input);
                    isValid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Неверный формат ввода. Введите целое число или null.");
            }
        }
        return value;
    }

    /**
     * Метод getValidFloat проверяет, что введенное значение является числом с плавающей точкой и больше минимального значения.
     *
     * @param scanner объект Scanner для считывания ввода пользователя.
     * @param prompt сообщение, которое выводится пользователю для ввода данных.
     * @param minValue минимальное значение, которое должно быть введено.
     * @return возвращает введенное значение, если оно корректно.
     */
    private static float getValidFloat(Scanner scanner, String prompt, float minValue) {
        float value = 0;
        boolean isValid = false;
        while (!isValid) {
            try {
                System.out.println(prompt);
                value = scanner.nextFloat();
                if (value > minValue) {
                    isValid = true;
                } else {
                    System.out.println("Ошибка: Значение должно быть больше " + minValue + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: Неверный формат ввода. Введите число с плавающей точкой.");
                scanner.nextLine(); // Очистка буфера сканера
            } finally {
                scanner.nextLine(); // Очистка буфера сканера
            }
        }
        return value;
    }
}