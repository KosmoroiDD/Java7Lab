package modules.commands;

import CollectionObjects.Product;
import modules.Command;
import network.Response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static CollectionObjects.Collectionss.stringCollection;

/**
 * Класс FromMax реализует команду вывода элементов коллекции в порядке убывания цены.
 */
public class FromMax implements Command {
    static Logger log = Logger.getLogger(FromMax.class.getName());
    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды.
     */
    @Override
    public String getDescription() {
        return getName() + " вывести элементы коллекции в порядке убывания цены";
    }

    /**
     * Возвращает имя команды.
     *
     * @return строка с именем команды.
     */
    @Override
    public String getName() {
        return "from_max_to_min";
    }

    @Override
    public Response call(String strArg, Serializable objectArg) {
        // Проверка на null и пустую коллекцию
        if (stringCollection == null || stringCollection.isEmpty()) {
            log.warning("Collection is empty");
            return new Response("Collection is empty");
        }
        // Создаем список из элементов коллекции
        List<Map.Entry<Integer, Product>> list = new ArrayList<>(stringCollection.entrySet());
        // Сортируем по убыванию цены
        list.sort((entry1, entry2) -> {
            Double price1 = entry1.getValue().getPrice();
            Double price2 = entry2.getValue().getPrice();
            // Обработка null (если цена может быть null)
            if (price1 == null && price2 == null) return 0;
            if (price1 == null) return 1; // null считается больше, чтобы уйти в конец списка
            if (price2 == null) return -1;
            return Double.compare(price2, price1); // Сортировка по убыванию
        });
        // Выводим отсортированные элементы
        for (Map.Entry<Integer, Product> entry : list) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        log.info("From Max to Min completed");
        return new Response(list.toString());
    }
}