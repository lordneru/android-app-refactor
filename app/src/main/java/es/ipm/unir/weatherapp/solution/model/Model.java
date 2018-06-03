package es.ipm.unir.weatherapp.solution.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;

import es.ipm.unir.weatherapp.solution.App;
import es.ipm.unir.weatherapp.utilities.APIEndpoints;
import es.ipm.unir.weatherapp.solution.model.pojo.City;
import es.ipm.unir.weatherapp.solution.model.pojo.MedidaTemperatura;
import es.ipm.unir.weatherapp.solution.model.pojo.WeatherAPIResponse;
import es.ipm.unir.weatherapp.solution.ui.WeatherAPIResponseMapperCity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Model implements Presenter2Model{

    @Inject
    String apiBaseUrl;

    @Inject
    String apiToken;

    private final static String TEMP_KEY = "tempkey";
    private final Context context;
    private final APIEndpoints api;


    public Model(Context context) {
        this.context = context;
        App.getAppComponent().inject(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiBaseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(APIEndpoints.class);
    }

    @Override
    public MedidaTemperatura getTempPref() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return MedidaTemperatura.valueOf(sp.getString(TEMP_KEY, MedidaTemperatura.CEL.name()));
    }

    @Override
    public void setPrefs(MedidaTemperatura temp) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(TEMP_KEY, temp.name()).apply();
    }

    @Override
    public Observable<City> update() {
        Observable<City> observable1 = api.getCityWeather("Madrid", "json", apiToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new WeatherAPIResponseMapperCity());

        return api.getCityWeather("Madrid", "json", apiToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .mergeWith(new Observable<WeatherAPIResponse>() {
                    @Override
                    protected void subscribeActual(Observer<? super WeatherAPIResponse> observer) {

                    }
                })
                .map(new WeatherAPIResponseMapperCity());
    }
}
