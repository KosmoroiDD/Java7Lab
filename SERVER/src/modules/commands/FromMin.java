package modules.commands;

import CollectionObjects.Product;
import modules.Command;
import network.Response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static CollectionObjects.Collectionss.stringCollection;


/**
 * Класс FromMin реализует команду вывода элементов коллекции в порядке возрастания цены.
 */
public class FromMin implements Command {
    static Logger log = Logger.getLogger(FromMin.class.getName());
    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды.
     */
    @Override
    public String getDescription() {
        return getName() + " вывести элементы коллекции в порядке возрастания цены";
    }

    /**
     * Возвращает имя команды.
     *
     * @return строка с именем команды.
     */
    @Override
    public String getName() {
        return "from_min_to_max";
    }

    @Override
    public Response call(String strArg, Serializable objectArg) {
        try {
            if (stringCollection == null || stringCollection.isEmpty()) {
                log.info("Collection is empty");
                return new Response("Collection is empty");
            }
            // Создаем список из элементов коллекции
            List<Map.Entry<Integer, Product>> list = new ArrayList<>(stringCollection.entrySet());

            // Сортируем по возрастанию цены
            list.sort(Comparator.comparingDouble(entry -> entry.getValue().getPrice()));

            // Выводим отсортированные элементы
            for (Map.Entry<Integer, Product> entry : list) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }
            log.info("From Min completed");
            return new Response(list.toString());
        }
        catch (Exception e) {
            log.severe(e.getMessage());
            return new Response(e.getMessage());
        }
    }
}