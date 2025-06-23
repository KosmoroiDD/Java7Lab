package modules.commands;

import modules.Command;
import network.Response;
import java.io.Serializable;
import java.util.logging.Logger;

import static CollectionObjects.Collectionss.stringCollection;

/**
 * Класс Clean реализует команду очистки коллекции.
 */
public class Clean implements Command {
    static Logger log = Logger.getLogger(Clean.class.getName());
    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды.
     */
    @Override
    public String getDescription() {
        return getName() + " очистить коллекцию";
    }

    /**
     * Возвращает имя команды.
     *
     * @return строка с именем команды.
     */
    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public Response call(String strArg, Serializable objArg) {
        stringCollection.clear();
        log.info("Collection cleared");
        return new Response("collection had been successfully cleared");
    }
}