package es.ipm.unir.weatherapp.solution.model;

import es.ipm.unir.weatherapp.solution.model.pojo.City;
import es.ipm.unir.weatherapp.solution.model.pojo.MedidaTemperatura;
import io.reactivex.Observable;

public interface Presenter2Model {
    MedidaTemperatura getTempPref();
    void setPrefs(MedidaTemperatura temp);

    Observable<City> update();
}
