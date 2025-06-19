package modules.commands;
import network.Response;
import modules.Command;

import java.io.Serializable;

import static java.lang.Integer.parseInt;

/**
 * Класс RemLow реализует команду удаления из коллекции всех элементов, ключи которых меньше заданного.
 */
public class RemLow implements Command {

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

        return null;
    }
}