package es.ipm.unir.weatherapp.solution.presenter;

import android.content.Context;

import es.ipm.unir.weatherapp.solution.model.Model;
import es.ipm.unir.weatherapp.solution.ui.MainActivityImpl;

public interface MainActivityPresenter {
    void create();
    void destroy();
    void onErrorLayoutClick();
    void checkChanged(boolean isChecked);
    void setView(MainActivityImpl mainActivity);
}
