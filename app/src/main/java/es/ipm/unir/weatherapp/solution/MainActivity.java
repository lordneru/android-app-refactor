package es.ipm.unir.weatherapp.solution;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import es.ipm.unir.weatherapp.City;
import es.ipm.unir.weatherapp.R;
import es.ipm.unir.weatherapp.Utils;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private final static String WEATHER_API_TOKEN = "ec4e98347c55d9bbea652c638940c0fc";
    private final static String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q=Madrid&mode=xml&appid=" + WEATHER_API_TOKEN;
    private final static String NASA_API_TOKEN = "DEMO_KEY";
    private final static String NASA_URL = "https://api.nasa.gov/neo/rest/v1/feed?start_date=#STARTDATE#&end_date=#ENDDATE#&detailed=false&api_key=" + NASA_API_TOKEN;
    private final static String TEMP_KEY = "tempkey";
    private final static String CELSIUS_KEY = "CELSIUS";
    private final static String KELVIN_KEY = "KELVIN";

    View loadingLayout;
    View contentLayout;
    View errorLayout;
    TextView updateTimeTV;
    TextView temperatureTV;
    TextView weatherTV;
    TextView cityNameTV;
    TextView asteroidTV;
    Switch temperatureSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingLayout = findViewById(R.id.loading_layout);
        contentLayout = findViewById(R.id.content_layout);
        errorLayout = findViewById(R.id.error_layout);
        cityNameTV = (TextView) findViewById(R.id.cityname_tv);
        updateTimeTV = (TextView) findViewById(R.id.updatetime_tv);
        temperatureTV = (TextView) findViewById(R.id.temperature_tv);
        weatherTV = (TextView) findViewById(R.id.weatherdescr_tv);
        asteroidTV = (TextView) findViewById(R.id.asteroid_tv);
        temperatureSwitch = (Switch) findViewById(R.id.temperature_switch);

        errorLayout.setOnClickListener(this);
        temperatureSwitch.setOnCheckedChangeListener(this);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isCelsius = CELSIUS_KEY.equals(sp.getString(TEMP_KEY, CELSIUS_KEY));
        temperatureSwitch.setChecked(!isCelsius);

        new UpdateData().execute(WEATHER_URL, NASA_URL);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().putString(TEMP_KEY, isChecked ? KELVIN_KEY : CELSIUS_KEY).apply();

        new UpdateData().execute(WEATHER_URL, NASA_URL);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.error_layout) {
            new UpdateData().execute(WEATHER_URL, NASA_URL);
        }
    }

    private class UpdateData extends AsyncTask<String, City, City> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingLayout.setVisibility(View.VISIBLE);
            contentLayout.setVisibility(View.INVISIBLE);
            errorLayout.setVisibility(View.GONE);
        }

        protected City doInBackground(String... urls) {
            City response = new City();

            try {
                String xmlResponse = IOUtils.toString(new URL(urls[0]));

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

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String dayString = sdf.format(new Date());
                String finalNASAURL = urls[1].replace("#STARTDATE#", dayString).replace("#ENDDATE#", dayString);
                JSONObject jsonResponse = new JSONObject(IOUtils.toString(new URL(finalNASAURL)));

                boolean hazardous = false;
                JSONArray nearObjects = jsonResponse.getJSONObject("near_earth_objects").getJSONArray(dayString);
                for (int i=0; i<nearObjects.length(); i++) {
                    JSONObject nearObject = nearObjects.getJSONObject(i);
                    hazardous = hazardous || nearObject.getBoolean("is_potentially_hazardous_asteroid");
                }
                response.setDeathByAsteroidPossible(hazardous);
            } catch (IOException | ParserConfigurationException | SAXException | JSONException e) {
                e.printStackTrace();
                return null;
            }

            return response;
        }

        @Override
        protected void onPostExecute(City response) {
            super.onPostExecute(response);

            if (response == null) {
                contentLayout.setVisibility(View.INVISIBLE);
                loadingLayout.setVisibility(View.INVISIBLE);
                errorLayout.setVisibility(View.VISIBLE);

            } else {
                cityNameTV.setText(response.getName());
                updateTimeTV.setText(getString(R.string.updatetime_info, response.getUpdateTime()));
                weatherTV.setText(getString(R.string.weatherdesc_info, response.getWeatherDescription()));
                asteroidTV.setText(getString(R.string.asteroid_info, response.isDeathByAsteroidPossible() ? "Si" : "No"));

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                switch (sp.getString(TEMP_KEY, CELSIUS_KEY)) {
                    case CELSIUS_KEY:
                        temperatureTV.setText(getString(R.string.temperature_info, response.getTemperature() - 273.15, "ºC"));
                        break;
                    case KELVIN_KEY:
                        temperatureTV.setText(getString(R.string.temperature_info, response.getTemperature(), "K"));
                        break;
                }

                contentLayout.setVisibility(View.VISIBLE);
                loadingLayout.setVisibility(View.INVISIBLE);
                errorLayout.setVisibility(View.GONE);
            }
        }
    }

}