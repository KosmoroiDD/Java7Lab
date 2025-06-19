package modules.commands;
import network.Response;
import modules.Command;

import java.io.Serializable;

import static java.lang.Integer.parseInt;

/**
 * Класс Replace_Lower реализует команду замены значения по ключу, если новое значение меньше старого.
 */
public class Replace_Lower implements Command {

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды.
     */
    @Override
    public String getDescription() {
        return getName() + " заменить значение по ключу, если новое значение меньше старого";
    }

    /**
     * Возвращает имя команды.
     *
     * @return строка с именем команды.
     */
    @Override
    public String getName() {
        return "replace_l";
    }

    @Override
    public Response call(String strArg, Serializable objectArg) {

        return null;
    }
}