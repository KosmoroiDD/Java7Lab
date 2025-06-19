package modules.commands;

import network.Response;
import modules.Command;

import java.io.Serializable;


import static java.lang.Integer.parseInt;

/**
 * Класс Replace_Higher реализует команду замены значения по ключу, если новое значение больше старого.
 */
public class Replace_Higher implements Command {

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды.
     */
    @Override
    public String getDescription() {
        return getName() + " заменить значение по ключу, если новое значение больше старого";
    }

    /**
     * Возвращает имя команды.
     *
     * @return строка с именем команды.
     */
    @Override
    public String getName() {
        return "replace_h";
    }


    @Override
    public Response call(String strArg, Serializable objArg) {

        return null;
    }
}