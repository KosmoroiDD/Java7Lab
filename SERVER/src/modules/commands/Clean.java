package modules.commands;

import modules.Command;
import network.Response;
import CollectionObjects.Collectionss;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Класс Clean реализует команду очистки коллекции.
 */
public class Clean implements Command {

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
        Collectionss.setCollection(new HashMap<>());
        return new Response("collection had been successfully cleared");
    }
}