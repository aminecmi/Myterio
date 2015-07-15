package com.amine.myterio.app.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.amine.myterio.app.R;
import com.amine.myterio.app.adapters.ForecastAdapter;
import com.amine.myterio.app.api.WeatherAdapters;
import com.amine.myterio.app.api.WeatherApis;
import com.amine.myterio.app.config.Config;
import com.amine.myterio.app.db.CityDAO;
import com.amine.myterio.app.model.City;
import com.amine.myterio.app.model.Forecast;
import com.melnykov.fab.FloatingActionButton;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DetailsFragment extends Fragment {
    static boolean isFav = true;
    static FloatingActionButton fab;
    private Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragView = inflater.inflate(R.layout.details_fragment, null, false);
        String cityName = null;
        City city = null;
        if (this.getArguments() != null) {
            Bundle bundle = this.getArguments();
            city = bundle.getParcelable("city");
        } else {
            cityName = getActivity().getIntent().getExtras().getString("city_name");
            if (cityName == null) {
                city = getActivity().getIntent().getExtras().getParcelable("city");
            }
        }

        WeatherAdapters adapters = new WeatherAdapters();
        final Forecast[] f = {null};
        final RecyclerView list = (RecyclerView) fragView.findViewById(R.id.weekForecast);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        list.setLayoutManager(layoutManager);
        list.setHasFixedSize(true);

        CityDAO dao = CityDAO.getInstance(mActivity);
        if (cityName != null) {

            isFav = dao.getCity(cityName) != null;
            WeatherApis.WeatherDailyForecastLocationApi s = adapters.getWeatherForecastLocationAdapter();

            s.cityForecast(cityName, Config.country, new Callback<Forecast>() {
                @Override
                public void success(Forecast forecast, Response response) {
                    f[0] = forecast;
                    list.setAdapter(new ForecastAdapter(mActivity, f[0]));

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(mActivity, getString(R.string.weather_not_available), Toast.LENGTH_SHORT).show();
                }
            });

            WeatherApis.WeatherLocationApi sWeather = adapters.getWeatherLocationAdapter();
            sWeather.locationWeather(cityName, Config.country, new Callback<City>() {
                @Override
                public void success(City c, Response response) {
                    ImageView image = (ImageView) fragView.findViewById(R.id.weatherImage);
                    image.setImageDrawable(c.getWeather().get(0).getIconDrawable(mActivity, mActivity.getPackageName()));
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(mActivity, getString(R.string.weather_get_error), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            cityName = city.getName();
            isFav = dao.getCity(city.getCityIdentifier()) != null;
            WeatherApis.WeatherDailyForecastApi s = adapters.getWeatherForecastAdapter();

            s.cityForecast(city.getCityIdentifier(), Config.country, new Callback<Forecast>() {
                @Override
                public void success(Forecast forecast, Response response) {
                    f[0] = forecast;
                    list.setAdapter(new ForecastAdapter(mActivity, f[0]));
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(mActivity, getString(R.string.weather_not_available), Toast.LENGTH_SHORT).show();
                }
            });

            WeatherApis.WeatherCityApi sWeather = adapters.getWeatherCityAdapter();
            sWeather.cityWeather(city.getCityIdentifier(), Config.country, new Callback<City>() {
                @Override
                public void success(City c, Response response) {
                    ImageView image = (ImageView) fragView.findViewById(R.id.weatherImage);
                    image.setImageDrawable(c.getWeather().get(0).getIconDrawable(mActivity, mActivity.getPackageName()));
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(mActivity, getString(R.string.weather_get_error), Toast.LENGTH_SHORT).show();
                }
            });
        }

        TextView name = (TextView) fragView.findViewById(R.id.cityName);
        name.setText(cityName);

        fab = (FloatingActionButton) fragView.findViewById(R.id.fab);
        if (isFav) {
            fab.setImageResource(R.mipmap.ic_favorite_border_black_24dp);
        } else {
            fab.setImageResource(R.mipmap.ic_favorite_black_24dp);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityDAO dao = CityDAO.getInstance(mActivity);
                if (isFav) {
                    dao.deleteCity(f[0].getCity());
                    fab.setImageResource(R.mipmap.ic_favorite_black_24dp);
                } else {
                    dao.insertCity(f[0].getCity());
                    fab.setImageResource(R.mipmap.ic_favorite_border_black_24dp);
                }
            }
        });
        return fragView;
    }
}
