package es.ipm.unir.weatherapp.solution.di;

import javax.inject.Singleton;

import dagger.Component;
import es.ipm.unir.weatherapp.solution.model.Model;
import es.ipm.unir.weatherapp.solution.ui.MainActivityImpl;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(MainActivityImpl mainActivity);
    void inject(Model model);
}
