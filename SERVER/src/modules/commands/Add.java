package modules.commands;

import CollectionObjects.Product;
import modules.Command;
import modules.XmlParser;
import network.Response;
import java.io.Serializable;
import java.util.Random;
import java.util.logging.Logger;

import static CollectionObjects.Collectionss.stringCollection;

/**
 * Класс Add реализует команду добавления нового элемента в коллекцию.
 */
public class Add implements Command {
    static Logger log = Logger.getLogger(Add.class.getName());
    public static String filename;

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды.
     */
    @Override
    public String getDescription() {
        return getName() + " добавить новый элемент с заданным ключом";
    }

    /**
     * Возвращает имя команды.
     *
     * @return строка с именем команды.
     */
    @Override
    public String getName() {
        return "add";
    }
    public int generateId(){
        Random random = new Random();
        int id;
        id = random.nextInt(10000) + 1;
        return id;
    }

    @Override
    public Response call(String strArg, Serializable objArg) {
        Product product = (Product) objArg;
        stringCollection.put(generateId(), product);
        XmlParser.saveToXml(stringCollection, filename);
        log.info("Collection successfully added product");
        return new Response("item successfully added to collection!");
    }
}