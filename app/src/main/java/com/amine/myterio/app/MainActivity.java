package com.amine.myterio.app;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.amine.myterio.app.adapters.CitiesAdapter;
import com.amine.myterio.app.db.CityDAO;
import com.amine.myterio.app.model.City;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleView();

       /* WeatherAdapters adapters = new WeatherAdapters();
        WeatherApis.WeatherDailyForecastApi s = adapters.getWeatherForecastAdapter();
        s.cityForecast(1851632, new Callback<Forecast>() {
            @Override
            public void success(Forecast forecast, Response response) {
                Toast.makeText(getApplicationContext(), "Hello toast!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Hello toast!", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void handleView() {
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.list);


        // Google way to test is tablet
        boolean isTablet = ((this.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE);

        final RecyclerView.LayoutManager layoutManager;
        if (isTablet) {
            layoutManager = new GridLayoutManager(this, 2);
        }
        else {
            layoutManager = new LinearLayoutManager(this);
        }

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        ArrayList<City> cities = new ArrayList<City>();

        CityDAO dao = CityDAO.getInstance(this);

        City c = new City("Cairns", 2172797);
        City c1 = new City("Moscow", 524901);

        dao.insertCity(c);
        dao.insertCity(c1);
        cities = dao.getAllCities();

        mAdapter = new CitiesAdapter(cities, this);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToRecyclerView(mRecyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
