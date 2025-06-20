package modules.commands;

import modules.Command;
import java.io.Serializable;
import java.util.Random;
import java.util.logging.Logger;

import network.Response;
import static CollectionObjects.Collectionss.stringCollection;
import static java.lang.Integer.parseInt;

/**
 * Класс Updater реализует команду обновления значения элемента коллекции по его ID.
 */
public class Updater implements Command {
    static Logger log = Logger.getLogger(Updater.class.getName());
    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды.
     */
    @Override
    public String getDescription() {
        return getName() + " обновить значение элемента коллекции, id которого равен заданному";
    }
    public int generateId(){
        Random random = new Random();
        int id;
        id = random.nextInt(10000) + 1;
        return id;
    }
    /**
     * Возвращает имя команды.
     *
     * @return строка с именем команды.
     */
    @Override
    public String getName() {
        return "update_id";
    }

    @Override
    public Response call(String strArg, Serializable objectArg) {
        try {
            if (stringCollection.containsKey(parseInt(strArg))) {
                stringCollection.keySet().add(generateId());
                log.info("Id updated");
                return new Response("Id updated");
            } else {
                log.info("Id not found");
                return new Response("Id not found");
            }
        }
        catch (NumberFormatException e) {
            log.warning("Number format exception");
            return new Response("Not a number");
        }
    }
}