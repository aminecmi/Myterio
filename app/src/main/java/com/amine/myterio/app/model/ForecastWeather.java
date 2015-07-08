package com.amine.myterio.app.model;

import java.util.List;

public class ForecastWeather {
    public TempInfo temp;
    public List<Weather> weather;
    public float speed;
    public int dt;

    public TempInfo getTemp() {
        return temp;
    }

    public void setTemp(TempInfo temp) {
        this.temp = temp;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }
}
