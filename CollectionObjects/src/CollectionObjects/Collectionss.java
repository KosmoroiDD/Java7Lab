package CollectionObjects;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;


/**
 * Класс Collectionss представляет коллекцию продуктов, хранящуюся в виде HashMap.
 * Ключом является целое число (ID продукта), а значением — объект класса Product.
 */
public class Collectionss {

    /**
     * Статическое поле, представляющее коллекцию продуктов.
     * Ключом является целое число (ID продукта), а значением — объект класса Product.
     */
    public static HashMap<Integer, Product> stringCollection = new HashMap<>();
    private static Collection<Product> collection = new HashSet<>();
    Integer g = 4;
    int f = g;
    public static HashMap<Integer,Product> getCollection() {
        return stringCollection;
    }
    public static Collection<Product> getCollections() {
        return collection;
    }
    public static void setCollection(HashMap<Integer, Product> collection) {
        stringCollection = stringCollection;
    }


}