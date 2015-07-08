package com.amine.myterio.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.amine.myterio.app.api.CitiesAdapters;
import com.amine.myterio.app.api.CitiesApi;
import com.amine.myterio.app.api.Predictions;
import com.amine.myterio.app.config.Config;
import com.amine.myterio.app.model.Countries;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity {
    private final List<String> citiesArray = new ArrayList<String>();
    private Countries countries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final SearchView s = (SearchView) findViewById(R.id.searchView);
        ListView l = (ListView) findViewById(R.id.listView2);

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, citiesArray);
        l.setAdapter(adapter);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView t = (TextView) view;
                s.setQuery(t.getText(), true);
            }
        });

        s.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final boolean[] found = {false};

                CitiesAdapters adapters = new CitiesAdapters();
                CitiesApi.AutocompleteApi s = adapters.getCitiesForAutocomplete();
                s.autocompletePlace(newText, Config.placesApiKey, new Callback<Predictions>() {
                    @Override
                    public void success(Predictions predictions, Response response) {
                        List<String> cities = predictions.getCities();
                        citiesArray.clear();
                        found[0] = !cities.isEmpty();
                        citiesArray.addAll(cities);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(), "Erreur lors de la récupération des pays", Toast.LENGTH_SHORT).show();
                    }
                });
                return found[0];
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
