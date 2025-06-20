package modules.commands;
import CollectionObjects.Product;
import network.Response;
import modules.Command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static CollectionObjects.Collectionss.stringCollection;
import static java.lang.Integer.parseInt;

/**
 * Класс RemLow реализует команду удаления из коллекции всех элементов, ключи которых меньше заданного.
 */
public class RemLow implements Command {
    static Logger log = Logger.getLogger(RemLow.class.getName());
    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды.
     */
    @Override
    public String getDescription() {
        return getName() + " удалить из коллекции все элементы, меньшие, чем заданный";
    }

    /**
     * Возвращает имя команды.
     *
     * @return строка с именем команды.
     */
    @Override
    public String getName() {
        return "remove_lower";
    }


    @Override
    public Response call(String strArg, Serializable objArg) {
        try {
            Integer id2 = parseInt(strArg);
            // Список для хранения ключей элементов, которые нужно удалить
            List<Integer> IdToRemove = new ArrayList<>();
            // Поиск элементов, ключи которых меньше заданного
            for (Map.Entry<Integer, Product> entry : stringCollection.entrySet()) {
                Integer id = entry.getKey();
                if (id.compareTo(id2) < 0) {
                    IdToRemove.add(id);
                }
            }
            // Удаление найденных элементов
            for (Integer id : IdToRemove) {
                stringCollection.remove(id);
            }
            log.info("Collections with lower case deleted");
            return new Response("Collections with lower case deleted");
        }
        catch (Exception e) {
            log.severe(e.getMessage());
            return new Response("Error: " + e.getMessage());
        }
    }
}