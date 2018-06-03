package es.ipm.unir.weatherapp.utilities;

import es.ipm.unir.weatherapp.solution.model.pojo.WeatherAPIResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIEndpoints {
    @GET("weather")
    Observable<WeatherAPIResponse> getCityWeather(@Query("q") String city, @Query("mode") String mode, @Query("appid") String appid);
}
