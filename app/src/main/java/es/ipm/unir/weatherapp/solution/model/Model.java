package es.ipm.unir.weatherapp.solution.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;
import javax.inject.Named;

import es.ipm.unir.weatherapp.solution.App;
import es.ipm.unir.weatherapp.solution.api.APIEndpoints;
import es.ipm.unir.weatherapp.solution.api.WeatherAPIResponseMapperCity;
import es.ipm.unir.weatherapp.solution.model.pojo.City;
import es.ipm.unir.weatherapp.solution.model.pojo.MedidaTemperatura;
import es.ipm.unir.weatherapp.solution.model.pojo.WeatherAPIResponse;
import es.ipm.unir.weatherapp.solution.presenter.Presenter2Model;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Model implements Presenter2Model {

    @Inject
    @Named("apiBaseUrl")
    String apiBaseUrl;

    @Inject
    String apiToken;

    @Inject
    Retrofit retrofit;

    private final static String TEMP_KEY = "tempkey";
    private final Context context;
    private final APIEndpoints api;


    public Model(Context context) {
        this.context = context;
        App.getAppComponent().inject(this);

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
        return api.getCityWeather("Madrid", "json", apiToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new WeatherAPIResponseMapperCity());

//        return api.getCityWeather("Madrid", "json", apiToken)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .mergeWith(new Observable<WeatherAPIResponse>() {
//                    @Override
//                    protected void subscribeActual(Observer<? super WeatherAPIResponse> observer) {
//
//                    }
//                })
//                .map(new WeatherAPIResponseMapperCity());
    }
}
