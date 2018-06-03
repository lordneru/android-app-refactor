package es.ipm.unir.weatherapp.solution.model;

import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import es.ipm.unir.weatherapp.solution.model.pojo.City;
import es.ipm.unir.weatherapp.solution.api.APIBaseRunnable;
import es.ipm.unir.weatherapp.solution.utilities.Utils;

public class CityInfoRequest extends APIBaseRunnable<City> {

    private final static String API_TOKEN = "ec4e98347c55d9bbea652c638940c0fc";
    private final static String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q=Madrid&mode=xml&appid=" + API_TOKEN;

    public CityInfoRequest() {
    }

    @Override
    public City run() {
        City response = new City();
        try {
            String xmlResponse = IOUtils.toString(new URL(WEATHER_URL));

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlResponse));
            Document doc = db.parse(is);

            Node cityNameNode = Utils.getFirstElementByTagName(doc, "city");
            response.setName(cityNameNode.getAttributes().getNamedItem("name").getTextContent());

            Node lastUpdateNode = Utils.getFirstElementByTagName(doc, "lastupdate");
            response.setUpdateTime(lastUpdateNode.getAttributes().getNamedItem("value").getTextContent());

            Node temperatureNode = Utils.getFirstElementByTagName(doc, "temperature");
            double tempKelvin = Float.valueOf(temperatureNode.getAttributes().getNamedItem("value").getTextContent());
            response.setTemperature(tempKelvin);

            Node weatherDescNode = Utils.getFirstElementByTagName(doc, "weather");
            response.setWeatherDescription(weatherDescNode.getAttributes().getNamedItem("value").getTextContent());

        } catch (IOException | ParserConfigurationException | SAXException e) {
            Log.d("CityInfoRequest", e.getMessage());
            return null;
        }
        return response;
    }
}
