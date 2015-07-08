package com.amine.myterio.app.model;

public class MainInfo {
    public float temp;
    public float temp_max;
    public float temp_min;

    public int getTemp() {
        return Math.round(temp);
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public int getTemp_max() {
        return Math.round(temp_max);
    }

    public void setTemp_max(float temp_max) {
        this.temp_max = temp_max;
    }

    public int getTemp_min() {
        return Math.round(temp_min);
    }

    public void setTemp_min(float temp_min) {
        this.temp_min = temp_min;
    }
}
