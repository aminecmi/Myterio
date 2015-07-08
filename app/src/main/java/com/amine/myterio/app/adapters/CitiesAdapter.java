package com.amine.myterio.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.amine.myterio.app.DetailsActivity;
import com.amine.myterio.app.R;
import com.amine.myterio.app.api.WeatherAdapters;
import com.amine.myterio.app.api.WeatherApis;
import com.amine.myterio.app.model.City;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {
    private static ArrayList<City> mDataset;
    private static Context c;

    public CitiesAdapter(ArrayList<City> cities, Context c) {
        mDataset = cities;
        this.c = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_card_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final City[] currentCity = {mDataset.get(position)};
        TextView name = (TextView) holder.mCardView.findViewById(R.id.name);
        name.setText(currentCity[0].getName());

        WeatherAdapters adapters = new WeatherAdapters();
        WeatherApis.WeatherCityApi s = adapters.getWeatherCityAdapter();
        s.cityWeather(currentCity[0].getCityIdentifier(), new Callback<City>() {
            @Override
            public void success(City city, Response response) {
                currentCity[0] = city;
                // Update with found data

                TextView temp = (TextView) holder.mCardView.findViewById(R.id.temp);
                temp.setText("" + city.getMain().getTemp());

                TextView maxTemp = (TextView) holder.mCardView.findViewById(R.id.max_temp);
                maxTemp.setText("" + city.getMain().getTemp_max());

                TextView minTemp = (TextView) holder.mCardView.findViewById(R.id.temp_min);
                minTemp.setText("" + city.getMain().getTemp_min());

                TextView wind = (TextView) holder.mCardView.findViewById(R.id.wind_speed);
                wind.setText("" + city.getWind().getSpeed());

                TextView weatherName = (TextView) holder.mCardView.findViewById(R.id.weather_text);
                weatherName.setText(city.getWeather().get(0).getDescription());

                ImageView image = (ImageView) holder.mCardView.findViewById(R.id.weather_image);
                image.setImageDrawable(city.getWeather().get(0).getIconDrawable(c, c.getPackageName()));
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(c, "Hello toast!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {
        public View mCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Go to details activity
            final int position = getLayoutPosition();
            Intent intent = new Intent(c, DetailsActivity.class);
            intent.putExtra("city", mDataset.get(position));
            c.startActivity(intent);
        }
    }
}
