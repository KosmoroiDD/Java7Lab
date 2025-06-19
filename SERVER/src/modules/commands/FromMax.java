package modules.commands;

import modules.Command;
import network.Response;

import java.io.Serializable;


/**
 * Класс FromMax реализует команду вывода элементов коллекции в порядке убывания цены.
 */
public class FromMax implements Command {

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


        return null;
    }
}