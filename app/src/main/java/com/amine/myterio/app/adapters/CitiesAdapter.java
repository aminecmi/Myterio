package com.amine.myterio.app.adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.amine.myterio.app.config.Config;
import com.amine.myterio.app.db.CityDAO;
import com.amine.myterio.app.fragments.DetailsFragment;
import com.amine.myterio.app.model.City;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {
    private ArrayList<City> mDataset;
    private Context c;
    private Activity activity;

    public CitiesAdapter(ArrayList<City> cities, Context c, Activity a) {
        this.mDataset = cities;
        this.c = c;
        this.activity = a;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_card_view, parent, false);
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final City[] currentCity = {mDataset.get(position)};
        TextView name = (TextView) holder.mCardView.findViewById(R.id.name);
        name.setText(currentCity[0].getName());

        WeatherAdapters adapters = new WeatherAdapters();
        WeatherApis.WeatherCityApi s = adapters.getWeatherCityAdapter();
        s.cityWeather(currentCity[0].getCityIdentifier(), Config.country, new Callback<City>() {
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
                Toast.makeText(c, c.getString(R.string.weather_get_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener, RecyclerView.OnLongClickListener {
        public final View mCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int position = getLayoutPosition();
            int a = (c.getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK);

            if (((c.getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_LARGE) || ((c.getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_XLARGE) || ((c.getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    4)) {
                FragmentManager fragmentManager = activity.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DetailsFragment fragment = new DetailsFragment();
                fragmentTransaction.replace(R.id.details, fragment);

                fragmentTransaction.commit();

                Bundle bundle = new Bundle();
                bundle.putParcelable("city", mDataset.get(position));
                fragment.setArguments(bundle);
            } else {
                Intent intent = new Intent(c, DetailsActivity.class);
                intent.putExtra("city", mDataset.get(position));
                c.startActivity(intent);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            final int position = getLayoutPosition();
            AlertDialog.Builder builder = new AlertDialog.Builder(c);
            builder.setMessage(R.string.message_remove_from_favs)
                    .setTitle(R.string.title_remove_from_favs);
            builder.setPositiveButton(c.getString(R.string.delete), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    CityDAO dao = CityDAO.getInstance(c);
                    dao.deleteCity(mDataset.get(position));
                    mDataset.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mDataset.size());
                }
            });
            builder.setNegativeButton(c.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }
    }
}
