package es.ipm.unir.weatherapp.solution.ui;

import es.ipm.unir.weatherapp.solution.model.pojo.City;

public interface MainActivity {

    void setSwitchCheck(boolean checked);
    void showError();
    void showContent();
    void showLoading();
    void updateView(City city, String tempUnits);

}
