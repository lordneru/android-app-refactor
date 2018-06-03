package es.ipm.unir.weatherapp.solution.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserPreferenceManager {
    private final static String TEMP_KEY = "tempkey";
    private final static String CELSIUS_KEY = "CELSIUS";
    private final static String KELVIN_KEY = "KELVIN";
    private static UserPreferenceManager instance = new UserPreferenceManager( );

    private UserPreferenceManager() { }

    public static UserPreferenceManager getInstance( ) {
        return instance;
    }

    public void setUserPrefersCelsius(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(TEMP_KEY, CELSIUS_KEY).apply();
    }

    public void setUserPrefersKelvin(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(TEMP_KEY, KELVIN_KEY).apply();
    }

    public boolean userPrefersCelsius(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return CELSIUS_KEY.equals(sp.getString(TEMP_KEY, CELSIUS_KEY));
    }
}
