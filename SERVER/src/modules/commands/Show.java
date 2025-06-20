package modules.commands;

import modules.Command;
import modules.XmlParser;
import network.Response;
import java.io.*;

import static CollectionObjects.Collectionss.stringCollection;

/**
 * Класс Show реализует команду вывода всех элементов коллекции в строковом представлении.
 */
public class Show implements Command {
    public static String inputFileName;
    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды.
     */
    @Override
    public String getDescription() {
        return getName() + " вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    /**
     * Возвращает имя команды.
     *
     * @return строка с именем команды.
     */
    @Override
    public String getName() {
        return "show";
    }
    @Override
    public Response call(String strArg, Serializable objectArg) {
        return new Response(XmlParser.readFromXml(inputFileName).toString());
    }


}

