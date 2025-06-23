package modules;

import Exceptions.NoSuchCommandException;
import network.*;
import modules.commands.*;


import java.util.*;
import java.util.logging.Logger;

/**
 * Класс `CommandsProvider` предоставляет доступ к набору команд, доступных в приложении.
 * Он инициализирует и хранит команды в `HashMap`, обеспечивая возможность их получения по имени.
 */
public class CommandsProvider {
    static Logger log = Logger.getLogger(CommandsProvider.class.getName());
    /**
     * `HashMap`, содержащий доступные команды, где ключ - имя команды, а значение - объект `Command`.
     */
    public static Map<String, Command> commands = new HashMap<>();

    public static Response call(Request request) {
        try {
            Command command = CommandsProvider.getCommandByKey(request.getCommandName());
            if (command == null) {
                log.info("Command " + request.getCommandName() + " not found");
                return new Response("Command " + request.getCommandName() + " not found");
            }
            log.info("Command " + request.getCommandName() + " executed");
            return command.call(request.getCommandStrArg(), request.getCommandObjArg());
        } catch (NoSuchCommandException e){
            log.info(e.getMessage());
            return new Response("Не нашел команду: " + e.getMessage());
        }
    }

    /**
     * Конструктор класса `CommandsProvider`.
     * Инициализирует `HashMap` с доступными командами.
     */
    public CommandsProvider() {
        commands.put("help", new Help());
        commands.put("info", new Inform());
        commands.put("show", new Show());
        commands.put("add", new Add());
        commands.put("update_id", new Updater());
        commands.put("remove_key", new RemKey());
        commands.put("clear", new Clean());
        commands.put("replace_h", new Replace_Higher());
        commands.put("replace_l", new Replace_Lower());
        commands.put("remove_lower", new RemLow());
        commands.put("min_name", new MinName());
        commands.put("from_max_to_min", new FromMax());
        commands.put("from_min_to_max", new FromMin());
    }

    /**
     * Возвращает объект `Command` по заданному ключу (имени команды).
     *
     * @param key Имя команды.
     * @return Объект `Command` или `null`, если команда не найдена.
     */
    public static Command getCommandByKey(String key) {
        return commands.get(key);
    }

    /**
     * Возвращает `HashMap`, содержащий все доступные команды.
     *
     * @return `HashMap`, содержащий все команды.
     */
    public static Map<String, Command> getCommands() {
        return commands;
    }
}




