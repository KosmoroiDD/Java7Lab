package modules.commands;

import modules.Command;
import network.Response;
import java.io.Serializable;


/**
 * Класс MinName реализует команду вывода объекта из коллекции с минимальным значением поля name.
 */
public class MinName implements Command {

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды.
     */
    @Override
    public String getDescription() {
        return getName() + " вывести любой объект из коллекции, значение поля name которого является минимальным";
    }

    /**
     * Возвращает имя команды.
     *
     * @return строка с именем команды.
     */
    @Override
    public String getName() {
        return "min_name";
    }

    @Override
    public Response call(String strArg, Serializable objectArg) {

        return null;
    }
}