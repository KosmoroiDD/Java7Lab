package modules;

import jakarta.xml.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

import CollectionObjects.Product;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductMap {
    @XmlElement
    private Map<Integer, Product> products = new HashMap<>();

    public Map<Integer, Product> getProducts() {
        return products;
    }

    public void setProducts(Map<Integer, Product> products) {
        this.products = products;
    }
}