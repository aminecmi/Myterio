package com.amine.myterio.app.api;

import retrofit.RestAdapter;

public class WeatherAdapters {
    private final RestAdapter restAdapter;


    public WeatherAdapters() {
        this.restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.openweathermap.org")
                .build();
    }

    public WeatherApis.WeatherLocationApi getWeatherLocationAdapter() {
        return restAdapter.create(WeatherApis.WeatherLocationApi.class);
    }

    public WeatherApis.WeatherCityApi getWeatherCityAdapter() {
        return restAdapter.create(WeatherApis.WeatherCityApi.class);
    }

    public WeatherApis.WeatherDailyForecastApi getWeatherForecastAdapter() {
        return restAdapter.create(WeatherApis.WeatherDailyForecastApi.class);
    }


    public WeatherApis.WeatherDailyForecastLocationApi getWeatherForecastLocationAdapter() {
        return restAdapter.create(WeatherApis.WeatherDailyForecastLocationApi.class);
    }
}
