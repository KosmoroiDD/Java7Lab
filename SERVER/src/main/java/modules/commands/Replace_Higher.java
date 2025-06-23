package modules.commands;

import CollectionObjects.Product;
import network.Response;
import modules.Command;

import java.io.Serializable;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

/**
 * Класс Replace_Higher реализует команду замены значения по ключу, если новое значение больше старого.
 */
public class Replace_Higher implements Command {
    static Logger log = Logger.getLogger(Replace_Higher.class.getName());
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
        try {
            Product product = (Product) objArg;
            if (product.getPrice() > parseInt(strArg)) {
                product.setPrice(product.getPrice() * product.getPrice());
                log.info("Price updated to " + product.getPrice());
                return new Response("Price updated successfully");
            } else {
                log.info("Price lower than old price");
                return new Response("Price lower than old");
            }
        }
        catch (Exception e) {
            log.severe("Before create product!");
            return new Response("Before create product!");
        }

    }
}