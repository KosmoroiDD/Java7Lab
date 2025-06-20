package modules;

import CollectionObjects.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class XmlParser {
    public static void saveToXml(HashMap<Integer, Product> map, String path) {
        try {
            Document doc = createDocument(map);
            writeDocumentToFile(doc, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Метод для чтения коллекции из XML файла
    public static HashMap<Integer, Product> readFromXml(String path) {
        HashMap<Integer, Product> result = new HashMap<>();

        try {
            Document doc = parseXmlFile(path);
            NodeList productNodes = doc.getElementsByTagName("product");

            for (int i = 0; i < productNodes.getLength(); i++) {
                Node node = productNodes.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element productElement = (Element) node;
                    int id = Integer.parseInt(productElement.getAttribute("id"));
                    Product product = parseProduct(productElement);
                    result.put(id, product);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // ========== Вспомогательные методы для записи ==========
    private static Document createDocument(HashMap<Integer, Product> map) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element root = doc.createElement("products");
        doc.appendChild(root);

        for (var entry : map.entrySet()) {
            Element productElement = createProductElement(doc, entry.getKey(), entry.getValue());
            root.appendChild(productElement);
        }

        return doc;
    }

    private static Element createProductElement(Document doc, Integer id, Product product) {
        Element productElement = doc.createElement("product");
        productElement.setAttribute("id", id.toString());

        addTextElement(doc, productElement, "name", product.getName());
        addTextElement(doc, productElement, "price", String.valueOf(product.getPrice()));

        if (product.getManufactureCost() != null) {
            addTextElement(doc, productElement, "manufactureCost",
                    product.getManufactureCost().toString());
        }

        if (product.getCoordinates() != null) {
            Element coordinates = doc.createElement("coordinates");
            addTextElement(doc, coordinates, "x",
                    String.valueOf(product.getCoordinates().getX()));
            addTextElement(doc, coordinates, "y",
                    String.valueOf(product.getCoordinates().getY()));
            productElement.appendChild(coordinates);
        }

        if (product.getCreationDate() != null) {
            addTextElement(doc, productElement, "creationDate",
                    product.getCreationDate().format(DateTimeFormatter.ISO_DATE));
        }

        if (product.getUnitOfMeasure() != null) {
            addTextElement(doc, productElement, "unitOfMeasure",
                    product.getUnitOfMeasure().name());
        }

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

        if (org.getOfficialAddress() != null) {
            Element address = doc.createElement("postalAddress");
            addTextElement(doc, address, "street", org.getOfficialAddress().getStreet());

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

    private static void writeDocumentToFile(Document doc, String path) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(path));
        transformer.transform(source, result);
    }

    // ========== Вспомогательные методы для чтения ==========
    private static Document parseXmlFile(String path) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new File(path));
    }

    private static Product parseProduct(Element productElement) {
        Product product = new Product();

        product.SetName(getElementText(productElement, "name"));
        product.setPrice(Double.parseDouble(getElementText(productElement, "price")));

        if (hasElement(productElement, "manufactureCost")) {
            product.setManufactureCost(Integer.parseInt(getElementText(productElement, "manufactureCost")));
        }

        if (hasElement(productElement, "coordinates")) {
            Element coordElement = (Element) productElement.getElementsByTagName("coordinates").item(0);
            Coordinates coordinates = new Coordinates(0.0F, (long) 0.0F);
            coordinates.setX((float) Double.parseDouble(getElementText(coordElement, "x")));
            coordinates.setY((long) Double.parseDouble(getElementText(coordElement, "y")));
            product.setCoordinates(coordinates);
        }

        if (hasElement(productElement, "creationDate")) {
            product.setCreationDate(LocalDate.parse(
                    getElementText(productElement, "creationDate"),
                    DateTimeFormatter.ISO_DATE
            ));
        }

        if (hasElement(productElement, "unitOfMeasure")) {
            product.setUnitOfMeasure(UnitOfMeasure.valueOf(
                    getElementText(productElement, "unitOfMeasure")
            ));
        }

        if (hasElement(productElement, "manufacturer")) {
            Element manufacturerElement = (Element) productElement.getElementsByTagName("manufacturer").item(0);
            Organization organization = parseOrganization(manufacturerElement);
            product.setManufacturer(organization);
        }

        return product;
    }

    private static Organization parseOrganization(Element orgElement) {
        Organization org = new Organization();

        org.setName(getElementText(orgElement, "name"));

        if (hasElement(orgElement, "annualTurnover")) {
            org.setAnnualTurnover((float) Double.parseDouble(getElementText(orgElement, "annualTurnover")));
        }

        if (hasElement(orgElement, "type")) {
            org.setType(OrganizationType.valueOf(getElementText(orgElement, "type")));
        }

        if (hasElement(orgElement, "postalAddress")) {
            Element addressElement = (Element) orgElement.getElementsByTagName("postalAddress").item(0);
            Address address = new Address();
            address.setStreet(getElementText(addressElement, "street"));

            if (hasElement(addressElement, "town")) {
                Element townElement = (Element) addressElement.getElementsByTagName("town").item(0);
                Location location = new Location();
                location.setX(Long.parseLong(getElementText(townElement, "x")));
                location.setY(Double.parseDouble(getElementText(townElement, "y")));
                location.setZ(Integer.parseInt(getElementText(townElement, "z")));
                location.setName(getElementText(townElement, "name"));
                address.setTown(location);
            }

            org.setOfficialAddress(address);
        }

        return org;
    }

    private static String getElementText(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent();
        }
        return null;
    }

    private static boolean hasElement(Element parent, String tagName) {
        return parent.getElementsByTagName(tagName).getLength() > 0;
    }
}