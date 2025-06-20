package modules.commands;

import CollectionObjects.Product;
import modules.Command;
import modules.XmlParser;
import network.Response;
import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;
import static CollectionObjects.Collectionss.stringCollection;

/**
 * Класс Add реализует команду добавления нового элемента в коллекцию.
 */
public class Add implements Command {
    static Scanner scanner = new Scanner(System.in);
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

    @Override
    public Response call(String strArg, Serializable objArg) {
        Random random = new Random();
        int id;
        id = random.nextInt(10000) + 1;
        System.out.println(objArg.toString());
        Product product = (Product) objArg;
        stringCollection.put(id, product);
        XmlParser.saveToXml(stringCollection, filename);
        return new Response("item successfully added to collection!");
    }
}