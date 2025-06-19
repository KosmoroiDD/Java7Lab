package modules.commands;

import modules.Command;
import network.Response;
import java.io.Serializable;

/**
 * Класс RemKey реализует команду удаления элемента из коллекции по его ключу.
 */
public class RemKey implements Command {

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

        return null;
    }
}