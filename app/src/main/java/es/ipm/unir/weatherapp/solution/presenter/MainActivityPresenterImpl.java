package es.ipm.unir.weatherapp.solution.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import es.ipm.unir.weatherapp.solution.model.APIManagerResponse;
import es.ipm.unir.weatherapp.solution.model.CityInfoRequest;
import es.ipm.unir.weatherapp.solution.model.Model;
import es.ipm.unir.weatherapp.solution.model.UserPreferenceManager;
import es.ipm.unir.weatherapp.solution.model.pojo.City;
import es.ipm.unir.weatherapp.solution.ui.MainActivity;
import es.ipm.unir.weatherapp.solution.ui.MainActivityImpl;
import es.ipm.unir.weatherapp.utilities.APIManager;

public class MainActivityPresenterImpl implements MainActivityPresenter, APIManagerResponse {

    private MainActivity view;
    private Model model;
    private Context context;
    private APIManager apiManager;

    public MainActivityPresenterImpl(Context context, Model model){
        this.context = context;
        this.model = model;
    }

//    public MainActivityPresenterImpl(Context context, Model model, MainActivity view){
//        this.context = context;
//        this.model = model;
//        this.view = view;
//    }

    @Override
    public void create() {
//        if (view != null) {
//            boolean isCelsius = UserPreferenceManager_3.getInstance().userPrefersCelsius(context);
//            view.setSwitchCheck(!isCelsius);
//        }

        updateData();
    }

    private void updateData() {
        view.showLoading();
        apiManager.executeAPIRequest(new CityInfoRequest());
    }

    @Override
    public void destroy() {
        this.view = null;
        this.context = null;
    }

    @Override
    public void onErrorLayoutClick() {
        updateData();
    }

    @Override
    public void checkChanged(boolean isChecked) {
        if (isChecked) {
            UserPreferenceManager.getInstance().setUserPrefersKelvin(context);
        } else {
            UserPreferenceManager.getInstance().setUserPrefersCelsius(context);
        }
        updateData();
    }

    @Override
    public void setView(MainActivityImpl mainActivity) {

    }

    @Override
    public void onAPIResponse(Object data) {
        final City city = (City) data;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {if (city == null) {
                view.showError();
            } else {
                boolean isCelsius = UserPreferenceManager.getInstance().userPrefersCelsius(context);

                city.setTemperature(isCelsius ? city.getTemperature() - 273.15 : city.getTemperature());
                view.updateView(city, isCelsius ? "ºC" : "K");

                view.showContent();
            }
            }
        });
    }
}
