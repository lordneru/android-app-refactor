package es.ipm.unir.weatherapp.solution.ui;

import es.ipm.unir.weatherapp.solution.model.pojo.City;
import es.ipm.unir.weatherapp.solution.model.pojo.WeatherAPIResponse;
import io.reactivex.functions.Function;

public class WeatherAPIResponseMapperCity implements Function<WeatherAPIResponse, City> {
    @Override
    public City apply(WeatherAPIResponse responseBody) throws Exception {
        City response = new City();
        response.setName(responseBody.getName());
        response.setUpdateTime(String.valueOf(responseBody.getDt()));
        response.setWeatherDescription(responseBody.getWeather().get(0).getDescription());
        response.setTemperature(responseBody.getMain().getTemp());
        return response;
    }
}
