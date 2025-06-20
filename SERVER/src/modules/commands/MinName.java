package modules.commands;

import CollectionObjects.Product;
import modules.Command;
import network.Response;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Optional;
import java.util.logging.Logger;

import static CollectionObjects.Collectionss.stringCollection;


/**
 * Класс MinName реализует команду вывода объекта из коллекции с минимальным значением поля name.
 */
public class MinName implements Command {
    static Logger log = Logger.getLogger(MinName.class.getName());
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
        try {
            Optional<Product> minProduct = stringCollection.values().stream()
                    .min(Comparator.comparing(Product::getName));

            log.info("Min product name completed");
            return minProduct.map(product -> new Response(product.toString())).orElseGet(()
                    -> new Response("Collection is empty"));
        }
        catch (Exception e) {
            log.warning("Min product name failed");
            return new Response(e.getMessage());
        }
    }
}