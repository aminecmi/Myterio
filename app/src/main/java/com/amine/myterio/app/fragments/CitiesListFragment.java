package com.amine.myterio.app.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.amine.myterio.app.R;
import com.amine.myterio.app.SearchActivity;
import com.amine.myterio.app.adapters.CitiesAdapter;
import com.amine.myterio.app.db.CityDAO;
import com.amine.myterio.app.model.City;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

public class CitiesListFragment extends Fragment {

    RecyclerView mRecyclerView;
    FloatingActionButton fab;
    private Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.cities_list_fragment, null, false);
        mRecyclerView = (RecyclerView) fragView.findViewById(R.id.list);
        fab = (FloatingActionButton) fragView.findViewById(R.id.fab);
        handleView();
        return fragView;
    }

    @Override
    public void onResume() {
        super.onResume();
        handleView();
    }

    public void handleView() {
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(mActivity);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        ArrayList<City> cities;

        CityDAO dao = CityDAO.getInstance(mActivity);
        cities = dao.getAllCities();

        RecyclerView.Adapter mAdapter = new CitiesAdapter(cities, mActivity, this.getActivity());
        mRecyclerView.setAdapter(mAdapter);

        fab.attachToRecyclerView(mRecyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, SearchActivity.class);
                startActivity(intent);
            }
        });
    }
}
