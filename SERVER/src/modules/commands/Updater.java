package modules.commands;

import modules.Command;
import java.io.Serializable;
import network.Response;
/**
 * Класс Updater реализует команду обновления значения элемента коллекции по его ID.
 */
public class Updater implements Command {

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды.
     */
    @Override
    public String getDescription() {
        return getName() + " обновить значение элемента коллекции, id которого равен заданному";
    }

    /**
     * Возвращает имя команды.
     *
     * @return строка с именем команды.
     */
    @Override
    public String getName() {
        return "update_id";
    }

    @Override
    public Response call(String strArg, Serializable objectArg) {

        return null;
    }
}