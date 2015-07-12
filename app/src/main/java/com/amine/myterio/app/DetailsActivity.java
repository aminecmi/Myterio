package com.amine.myterio.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.amine.myterio.app.adapters.ForecastAdapter;
import com.amine.myterio.app.api.WeatherAdapters;
import com.amine.myterio.app.api.WeatherApis;
import com.amine.myterio.app.config.Config;
import com.amine.myterio.app.model.City;
import com.amine.myterio.app.model.Forecast;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String cityName = getIntent().getExtras().getString("city_name");

        WeatherAdapters adapters = new WeatherAdapters();
        final Forecast[] f = {null};
        final RecyclerView list = (RecyclerView) findViewById(R.id.weekForecast);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        list.setHasFixedSize(true);


        if (cityName != null) {
            WeatherApis.WeatherDailyForecastLocationApi s = adapters.getWeatherForecastLocationAdapter();

            s.cityForecast(cityName, Config.country, new Callback<Forecast>() {
                @Override
                public void success(Forecast forecast, Response response) {
                    f[0] = forecast;
                    list.setAdapter(new ForecastAdapter(DetailsActivity.this, f[0]));

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getApplicationContext(), getString(R.string.weather_not_available), Toast.LENGTH_SHORT).show();
                }
            });

            WeatherApis.WeatherLocationApi sWeather = adapters.getWeatherLocationAdapter();
            sWeather.locationWeather(cityName, Config.country, new Callback<City>() {
                @Override
                public void success(City c, Response response) {
                    ImageView image = (ImageView) findViewById(R.id.weatherImage);
                    image.setImageDrawable(c.getWeather().get(0).getIconDrawable(DetailsActivity.this, DetailsActivity.this.getPackageName()));
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(DetailsActivity.this, getString(R.string.weather_get_error), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            City city = getIntent().getExtras().getParcelable("city");
            cityName = city.getName();
            WeatherApis.WeatherDailyForecastApi s = adapters.getWeatherForecastAdapter();

            s.cityForecast(city.getCityIdentifier(), Config.country, new Callback<Forecast>() {
                @Override
                public void success(Forecast forecast, Response response) {
                    f[0] = forecast;
                    list.setAdapter(new ForecastAdapter(DetailsActivity.this, f[0]));
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getApplicationContext(), getString(R.string.weather_not_available), Toast.LENGTH_SHORT).show();
                }
            });

            WeatherApis.WeatherCityApi sWeather = adapters.getWeatherCityAdapter();
            sWeather.cityWeather(city.getCityIdentifier(), new Callback<City>() {
                @Override
                public void success(City c, Response response) {
                    ImageView image = (ImageView) findViewById(R.id.weatherImage);
                    image.setImageDrawable(c.getWeather().get(0).getIconDrawable(DetailsActivity.this, DetailsActivity.this.getPackageName()));
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(DetailsActivity.this, getString(R.string.weather_get_error), Toast.LENGTH_SHORT).show();
                }
            });
        }

        TextView name = (TextView) findViewById(R.id.cityName);
        name.setText(cityName);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
