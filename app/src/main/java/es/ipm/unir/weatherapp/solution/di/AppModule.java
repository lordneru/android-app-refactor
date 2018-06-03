package es.ipm.unir.weatherapp.solution.di;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import es.ipm.unir.weatherapp.solution.App;
import es.ipm.unir.weatherapp.solution.model.Model;
import es.ipm.unir.weatherapp.solution.presenter.MainActivityPresenter;
import es.ipm.unir.weatherapp.solution.presenter.MainActivityPresenterImpl;

@Module
public class AppModule {

    private final Context context;

    public AppModule(){
        this.context = App.getAppInstance().getApplicationContext();
    }

//    public Retrofit provideRetrofit(@Named("apiBaseUrl") String url){
//
//    }

    @Provides
    public MainActivityPresenter providePresenter(){
        return new MainActivityPresenterImpl(context, new Model(context));
    }

    @Provides
    public String apiBaseUrl(){
        return "http://api.openweathermap.org/data/2.5/";
    }

    @Provides
    public String apiToken(){
        return "ec4e98347c55d9bbea652c638940c0fc";
    }
}
