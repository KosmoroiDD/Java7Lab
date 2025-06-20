package modules;

import CollectionObjects.Location;
import CollectionObjects.Organization;
import CollectionObjects.Product;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class XmlParser {
    public static void saveToXml(HashMap<Integer, Product> map, String path) {
        try {
            // Создаем документ
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Создаем корневой элемент
            Element rootElement = doc.createElement("products");
            doc.appendChild(rootElement);

            // Добавляем все продукты
            for (HashMap.Entry<Integer, Product> entry : map.entrySet()) {
                Product product = entry.getValue();
                Element productElement = createProductElement(doc, entry.getKey(), product);
                rootElement.appendChild(productElement);
            }

            // Записываем в файл с форматированием
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Element createProductElement(Document doc, Integer id, Product product) {
        Element productElement = doc.createElement("product");
        productElement.setAttribute("id", id.toString());

        // Добавляем простые поля
        addTextElement(doc, productElement, "name", product.getName());
        addTextElement(doc, productElement, "price", String.valueOf(product.getPrice()));

        if (product.getManufactureCost() != null) {
            addTextElement(doc, productElement, "manufactureCost",
                    product.getManufactureCost().toString());
        }

        // Добавляем координаты
        if (product.getCoordinates() != null) {
            Element coordinates = doc.createElement("coordinates");
            addTextElement(doc, coordinates, "x",
                    String.valueOf(product.getCoordinates().getX()));
            addTextElement(doc, coordinates, "y",
                    String.valueOf(product.getCoordinates().getY()));
            productElement.appendChild(coordinates);
        }

        // Добавляем дату создания
        if (product.getCreationDate() != null) {
            addTextElement(doc, productElement, "creationDate",
                    product.getCreationDate().format(DateTimeFormatter.ISO_DATE));
        }

        // Добавляем единицу измерения
        if (product.getUnitOfMeasure() != null) {
            addTextElement(doc, productElement, "unitOfMeasure",
                    product.getUnitOfMeasure().name());
        }

        // Добавляем производителя
        if (product.getManufacturer() != null) {
            Element manufacturer = createOrganizationElement(doc, product.getManufacturer());
            productElement.appendChild(manufacturer);
        }

        return productElement;
    }

    private static Element createOrganizationElement(Document doc, Organization org) {
        Element orgElement = doc.createElement("manufacturer");

        addTextElement(doc, orgElement, "name", org.getName());

        if (org.getAnnualTurnover() > 0) {
            addTextElement(doc, orgElement, "annualTurnover",
                    String.valueOf(org.getAnnualTurnover()));
        }

        if (org.getType() != null) {
            addTextElement(doc, orgElement, "type", org.getType().name());
        }

        // Добавляем адрес
        if (org.getOfficialAddress() != null) {
            Element address = doc.createElement("postalAddress");
            addTextElement(doc, address, "street", org.getOfficialAddress().getStreet());

            // Добавляем местоположение
            if (org.getOfficialAddress().getTown() != null) {
                Element town = createLocationElement(doc, org.getOfficialAddress().getTown());
                address.appendChild(town);
            }

            orgElement.appendChild(address);
        }

        return orgElement;
    }

    private static Element createLocationElement(Document doc, Location location) {
        Element locationElement = doc.createElement("town");

        addTextElement(doc, locationElement, "x", String.valueOf(location.getX()));
        addTextElement(doc, locationElement, "y", String.valueOf(location.getY()));
        addTextElement(doc, locationElement, "z", String.valueOf(location.getZ()));
        addTextElement(doc, locationElement, "name", location.getName());

        return locationElement;
    }

    private static void addTextElement(Document doc, Element parent,
                                       String tagName, String value) {
        if (value != null) {
            Element element = doc.createElement(tagName);
            element.appendChild(doc.createTextNode(value));
            parent.appendChild(element);
        }
    }
}