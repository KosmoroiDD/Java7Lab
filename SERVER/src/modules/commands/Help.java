package modules.commands;

import modules.Command;
import modules.CommandsProvider;
import network.Response;

import java.io.Serializable;
import java.util.Map;

/**
 * Класс Help реализует команду вывода справки по доступным командам.
 */
public class Help implements Command {

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды.
     */
    @Override
    public String getDescription() {
        return getName() + " вывести справку по доступным командам";
    }

    /**
     * Возвращает имя команды.
     *
     * @return строка с именем команды.
     */
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public Response call(String strArg, Serializable objectArg) {
        Map<String, Command> commands = CommandsProvider.getCommands();
        StringBuilder ret = new StringBuilder();
        for (Map.Entry<String, Command> entry : commands.entrySet()){
            ret.append(entry.getKey()).append(" - ").append(entry.getValue().getDescription()).append("\n");
        }
        return new Response(ret.toString());
    }
}