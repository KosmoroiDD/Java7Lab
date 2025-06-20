package modules.commands;

import modules.Command;
import network.Response;
import java.io.Serializable;
import java.util.logging.Logger;

import static CollectionObjects.Collectionss.stringCollection;
import static java.lang.Integer.parseInt;

/**
 * Класс RemKey реализует команду удаления элемента из коллекции по его ключу.
 */
public class RemKey implements Command {
    static Logger log = Logger.getLogger(RemKey.class.getName());
    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды.
     */
    @Override
    public String getDescription() {
        return getName() + " удалить элемент из коллекции по его ключу";
    }

    /**
     * Возвращает имя команды.
     *
     * @return строка с именем команды.
     */
    @Override
    public String getName() {
        return "remove_key";
    }

    @Override
    public Response call(String strArg, Serializable objectArg) {
        try {
            if (stringCollection.containsKey(parseInt(strArg))) {
                stringCollection.remove(parseInt(strArg));
                log.info("Collection with" + parseInt(strArg) + "deleted");
                return new Response("Collection with" + parseInt(strArg) + " deleted");
            } else {
                log.info("Id not found");
                return new Response("Id not found");
            }
        }
        catch (Exception e) {
            log.severe(e.getMessage());
            return new Response("Error: " + e.getMessage());
        }
    }
}