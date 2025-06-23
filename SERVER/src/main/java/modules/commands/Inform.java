package modules.commands;

import modules.Command;
import CollectionObjects.Collectionss;
import CollectionObjects.Product;
import network.Response;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Класс Inform реализует команду вывода информации о коллекции.
 */
public class Inform implements Command {
    static Logger log = Logger.getLogger(Inform.class.getName());
    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды.
     */
    @Override
    public String getDescription() {
        return getName() + " вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }

    /**
     * Возвращает имя команды.
     *
     * @return строка с именем команды.
     */
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public Response call(String strArg, Serializable objectArg) {
        String ret = "collection type: " + Collectionss.getCollection().getClass().getSimpleName() + "\n" +
                "element type: " + Product.class.getSimpleName() + "\n" +
                "element amount: " + Collectionss.getCollection().size() + "\n";
        log.info(ret);
        return new Response(ret);
    }
}