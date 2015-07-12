package com.amine.myterio.app.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.amine.myterio.app.DetailsActivity;
import com.amine.myterio.app.R;
import com.amine.myterio.app.api.CitiesAdapters;
import com.amine.myterio.app.api.CitiesApi;
import com.amine.myterio.app.config.Config;
import com.amine.myterio.app.model.Countries;
import com.amine.myterio.app.model.Predictions;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private final List<String> citiesArray = new ArrayList<String>();
    private Activity mActivity;
    private Countries countries;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.search_fragment, null, false);
        final SearchView s = (SearchView) fragView.findViewById(R.id.searchView);
        ListView l = (ListView) fragView.findViewById(R.id.listView2);

        final ArrayAdapter adapter = new ArrayAdapter(mActivity, android.R.layout.simple_list_item_1, citiesArray);
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
                Intent intent = new Intent(mActivity, DetailsActivity.class);
                intent.putExtra("city_name", query);

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
                        Toast.makeText(mActivity, R.string.country_list_error, Toast.LENGTH_SHORT).show();
                    }
                });
                return found[0];
            }
        });
        return fragView;
    }
}
