package es.ipm.unir.weatherapp.solution;

import android.app.Application;

import es.ipm.unir.weatherapp.solution.di.AppComponent;
import es.ipm.unir.weatherapp.solution.di.DaggerAppComponent;

public class App extends Application {

    private static App appInstance;
    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appInstance = this;
        appComponent  = DaggerAppComponent.builder().build();
    }

    public static App getAppInstance(){
        return appInstance;
    }
    public static AppComponent getAppComponent(){
        return appInstance.appComponent;
    }

}
