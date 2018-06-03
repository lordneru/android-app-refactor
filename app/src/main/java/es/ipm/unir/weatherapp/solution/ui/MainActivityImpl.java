package es.ipm.unir.weatherapp.solution.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import javax.inject.Inject;

import es.ipm.unir.weatherapp.R;
import es.ipm.unir.weatherapp.solution.App;
import es.ipm.unir.weatherapp.solution.model.pojo.City;
import es.ipm.unir.weatherapp.solution.presenter.MainActivityPresenter;

public class MainActivityImpl extends AppCompatActivity implements MainActivity, CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    @Inject
    MainActivityPresenter presenter;

    View loadingLayout;
    View contentLayout;
    View errorLayout;
    TextView updateTimeTV;
    TextView temperatureTV;
    TextView weatherTV;
    TextView cityNameTV;
    TextView asteroidTV;
    Switch temperatureSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent().inject(this);

        loadingLayout = findViewById(R.id.loading_layout);
        contentLayout = findViewById(R.id.content_layout);
        errorLayout = findViewById(R.id.error_layout);
        cityNameTV = (TextView) findViewById(R.id.cityname_tv);
        updateTimeTV = (TextView) findViewById(R.id.updatetime_tv);
        temperatureTV = (TextView) findViewById(R.id.temperature_tv);
        weatherTV = (TextView) findViewById(R.id.weatherdescr_tv);
        asteroidTV = (TextView) findViewById(R.id.asteroid_tv);
        temperatureSwitch = (Switch) findViewById(R.id.temperature_switch);

        errorLayout.setOnClickListener(this);
        temperatureSwitch.setOnCheckedChangeListener(this);

        presenter.setView(this);
        presenter.create();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        presenter.checkChanged(isChecked);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.error_layout) {
            presenter.onErrorLayoutClick();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void setSwitchCheck(boolean checked) {
        temperatureSwitch.setChecked(checked);
    }

    @Override
    public void showError() {
        contentLayout.setVisibility(View.INVISIBLE);
        loadingLayout.setVisibility(View.INVISIBLE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showContent() {

    }

    @Override
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        contentLayout.setVisibility(View.INVISIBLE);
        errorLayout.setVisibility(View.GONE);
    }

    @Override
    public void updateView(City city, String tempUnits) {
        cityNameTV.setText(city.getName());
        updateTimeTV.setText(getString(R.string.updatetime_info, city.getUpdateTime()));
        weatherTV.setText(getString(R.string.weatherdesc_info, city.getWeatherDescription()));
        temperatureTV.setText(getString(R.string.temperature_info, city.getTemperature(), tempUnits));
    }
}
