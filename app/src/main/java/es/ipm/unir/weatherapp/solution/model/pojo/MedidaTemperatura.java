package es.ipm.unir.weatherapp.solution.model.pojo;

/**
 * Created by juangomez on 18/05/2018.
 */

public enum MedidaTemperatura {

    CEL,
    KEL;

    public String getClaveForMedida() {
        switch (this) {
            case CEL: return "C";
            case KEL: return "K";
        }
        return null;
    }

    public boolean isCelsius(){
        return this.equals(CEL);
    }

    public double getTempValueByType(double tempValue, MedidaTemperatura temp){
        if (temp.isCelsius()){
            return tempValue - 273.15;
        }else{
            return tempValue;
        }
    }
}
