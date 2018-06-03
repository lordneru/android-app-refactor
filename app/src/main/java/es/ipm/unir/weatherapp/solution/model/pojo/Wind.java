package es.ipm.unir.weatherapp.solution.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Wind {

    @SerializedName("deg")
    @Expose
    private Integer deg;
    @SerializedName("gust")
    @Expose
    private Double gust;
    @SerializedName("speed")
    @Expose
    private Double speed;

    public Integer getDeg() {
        return deg;
    }

    public void setDeg(Integer deg) {
        this.deg = deg;
    }

    public Double getGust() {
        return gust;
    }

    public void setGust(Double gust) {
        this.gust = gust;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }
}
