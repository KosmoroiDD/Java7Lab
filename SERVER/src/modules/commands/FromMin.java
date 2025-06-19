package modules.commands;

import modules.Command;
import network.Response;

import java.io.Serializable;


/**
 * Класс FromMin реализует команду вывода элементов коллекции в порядке возрастания цены.
 */
public class FromMin implements Command {

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

        return null;
    }
}