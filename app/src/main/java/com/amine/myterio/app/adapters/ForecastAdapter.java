package com.amine.myterio.app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.amine.myterio.app.R;
import com.amine.myterio.app.model.Forecast;
import com.amine.myterio.app.model.ForecastWeather;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    private static Forecast forecast;
    private static Context c;

    public ForecastAdapter(Context c, Forecast forecast) {
        this.forecast = forecast;
        this.c = c;
    }


    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ForecastAdapter.ViewHolder holder, int position) {
        ForecastWeather fw = this.forecast.getList().get(position);
        // MARCHE PAS
        Date date = new Date(fw.getDt());
        DateFormat format = new SimpleDateFormat("EEEE");
        String finalDay = format.format(date);

        TextView dateView = (TextView) holder.itemView.findViewById(R.id.dateText);
        TextView temp = (TextView) holder.itemView.findViewById(R.id.temp);

        dateView.setText(finalDay);
        temp.setText(fw.getTemp().getDay() + "");
    }

    @Override
    public int getItemCount() {
        return forecast.getList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}
