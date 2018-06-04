package es.ipm.unir.weatherapp.solution.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import es.ipm.unir.weatherapp.solution.api.APIManagerResponse;
import es.ipm.unir.weatherapp.solution.model.Model;
import es.ipm.unir.weatherapp.solution.model.pojo.City;
import es.ipm.unir.weatherapp.solution.model.pojo.MedidaTemperatura;
import es.ipm.unir.weatherapp.solution.ui.MainActivity;
import io.reactivex.observers.DisposableObserver;

public class MainActivityPresenterImpl implements MainActivityPresenter, APIManagerResponse {

    private MainActivity view;
    private Model model;

    public MainActivityPresenterImpl(Context context, Model model){
        this.model = model;
    }

    @Override
    public void create() {
        if (view != null) {
            view.setSwitchCheck(model.getTempPref().isCelsius());
        }

        updateData();
    }

    private void updateData() {
        view.showLoading();

        model.update()
                .subscribe(new DisposableObserver<City>() {
                    @Override
                    public void onNext(City body) {
                        onAPIResponse(body);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onDataLoadError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void destroy() {
        this.view = null;
    }

    @Override
    public void onErrorLayoutClick() {
        updateData();
    }

    @Override
    public void checkChanged(boolean isChecked) {
        if (isChecked) {
            model.setPrefs(MedidaTemperatura.KEL);
        } else {
            model.setPrefs(MedidaTemperatura.CEL);
        }
        updateData();
    }

    private void onDataLoadError() {
        view.showError();
    }

    @Override
    public void setView(MainActivity mainActivity) {
        view = mainActivity;
    }

    @Override
    public void onAPIResponse(Object data) {
        final City city = (City) data;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (city == null) {
                    view.showError();
                } else {
                    MedidaTemperatura temp = model.getTempPref();

                    city.setTemperature(temp.getTempValueByType(city.getTemperature(), temp));
                    view.updateView(city, temp.getClaveForMedida());
                    view.showContent();
                }
            }
        });
    }
}
