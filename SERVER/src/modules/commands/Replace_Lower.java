package modules.commands;
import CollectionObjects.Product;
import network.Response;
import modules.Command;

import java.io.Serializable;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

/**
 * Класс Replace_Lower реализует команду замены значения по ключу, если новое значение меньше старого.
 */
public class Replace_Lower implements Command {
    static Logger log = Logger.getLogger(Replace_Lower.class.getName());
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
        try {
            Product product = (Product) objectArg;
            if (product.getPrice() < parseInt(strArg)) {
                product.setPrice(product.getPrice() * product.getPrice());
                log.info("Price updated to " + product.getPrice());
                return new Response("Price updated successfully");
            } else {
                log.info("Price higher than old price");
                return new Response("Price higher than old");
            }
        }
        catch (Exception e) {
            log.severe("Before create product!");
            return new Response("Before create product!");
        }
    }
}