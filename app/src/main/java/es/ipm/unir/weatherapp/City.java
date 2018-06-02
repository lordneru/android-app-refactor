package es.ipm.unir.weatherapp;

public class City {

    private String updateTime;
    private double temperature;
    private String temperatureUnit;
    private String weatherDescription;
    private String name;
    private boolean isDeathByAsteroidPossible;

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public boolean isDeathByAsteroidPossible() {
        return isDeathByAsteroidPossible;
    }

    public void setDeathByAsteroidPossible(boolean deathByAsteroidPossible) {
        isDeathByAsteroidPossible = deathByAsteroidPossible;
    }
}