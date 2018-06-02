package es.ipm.unir.weatherapp;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

@SuppressWarnings("ALL")
public class Utils {

    public static Node getFirstElementByTagName(Document doc, String tagName) {
        return doc.getElementsByTagName(tagName).item(0);
    }

}
