package es.ipm.unir.weatherapp.solution.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Coord {
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lon")
    @Expose
    private Double lon;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
