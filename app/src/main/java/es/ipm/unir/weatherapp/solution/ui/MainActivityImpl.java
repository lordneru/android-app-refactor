package es.ipm.unir.weatherapp.solution.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.ipm.unir.weatherapp.R;
import es.ipm.unir.weatherapp.solution.App;
import es.ipm.unir.weatherapp.solution.model.pojo.City;
import es.ipm.unir.weatherapp.solution.presenter.MainActivityPresenter;

public class MainActivityImpl extends AppCompatActivity implements MainActivity, CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    @Inject
    MainActivityPresenter presenter;

    @BindView(R.id.loading_layout) View loadingLayout;
    @BindView(R.id.content_layout) View contentLayout;
    @BindView(R.id.error_layout) View errorLayout;
    @BindView(R.id.updatetime_tv) TextView updateTimeTV;
    @BindView(R.id.temperature_tv) TextView temperatureTV;
    @BindView(R.id.weatherdescr_tv) TextView weatherTV;
    @BindView(R.id.cityname_tv) TextView cityNameTV;
    @BindView(R.id.asteroid_tv) TextView asteroidTV;
    @BindView(R.id.temperature_switch) Switch temperatureSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent().inject(this);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        presenter.setView(this);
        presenter.create();
    }

    @OnClick(R.id.error_layout)
    void onErrorLayout(){
        presenter.onErrorLayoutClick();
    }

    @OnClick(R.id.temperature_switch)
    void onTemperatureSwitch(){
        temperatureSwitch.setOnCheckedChangeListener(this);
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
        contentLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.INVISIBLE);
        errorLayout.setVisibility(View.GONE);
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
