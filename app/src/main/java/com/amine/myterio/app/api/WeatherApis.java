package com.amine.myterio.app.api;

import com.amine.myterio.app.model.City;
import com.amine.myterio.app.model.Forecast;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public class WeatherApis {
    public interface WeatherLocationApi {
        @GET("/data/2.5/weather")
        void locationWeather(@Query("q") String location, Callback<City> cb);
    }
    public interface WeatherCityApi {
        @GET("/data/2.5/weather")
        void cityWeather(@Query("id") int city, Callback<City> cb);
    }
    public interface WeatherDailyForecastApi {
        @GET("/data/2.5/forecast/daily?units=metric&cnt=7")
        void cityForecast(@Query("id") int city, Callback<Forecast> cb);
    }

    public interface WeatherDailyForecastLocationApi {
        @GET("/data/2.5/forecast/daily?units=metric&cnt=7")
        void cityForecast(@Query("q") String location, Callback<Forecast> cb);
    }
}
