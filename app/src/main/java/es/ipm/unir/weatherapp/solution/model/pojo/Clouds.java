package es.ipm.unir.weatherapp.solution.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Clouds {
    @SerializedName("all")
    @Expose
    private Integer all;

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }
}
