package es.ipm.unir.weatherapp.solution.model.pojo;

/**
 * Created by juangomez on 18/05/2018.
 */

public enum MedidaTemperatura {

    CEL,
    FAR,
    KEL;

    public String getClaveForMedida() {
        switch (this) {
            case CEL: return "C";
            case FAR: return "F";
            case KEL: return "K";
        }
        return null;
    }

}
