package modules.commands;

import modules.Command;
import CollectionObjects.Collectionss;
import CollectionObjects.Product;
import network.Response;
import java.io.*;
import java.util.Collection;

/**
 * Класс Show реализует команду вывода всех элементов коллекции в строковом представлении.
 */
public class Show implements Command {
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
        Collection<Product> products = Collectionss.getCollections();
        StringBuilder ret = new StringBuilder();

        for(Product flat : products){
            ret.append(flat.toString()).append("\n");
        }
        return new Response(ret.toString());
    }


}

