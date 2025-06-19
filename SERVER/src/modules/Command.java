package modules;

import java.io.Serializable;
import network.Response;

/**
 * Интерфейс Command определяет контракт для всех команд, которые могут быть выполнены в приложении.
 * Каждая команда должна реализовывать методы для получения описания, имени и выполнения команды.
 */
public interface Command {

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды.
     */
    public abstract String getDescription();

    /**
     * Возвращает имя команды.
     *
     * @return строка с именем команды.
     */
    public abstract String getName();

    /**
     * Выполняет команду с указанным аргументом.

     */
    public abstract Response call(String strArg, Serializable objArg);
}