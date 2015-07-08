package com.amine.myterio.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class City implements Parcelable {

    public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>() {
        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
    public String name;
    public int id;
    public MainInfo main;
    public List<Weather> weather;
    public Wind wind;

    public City(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public City() {


    }

    public City(String description) {
        this.name = description;
    }

    public City(Parcel source) {
        this.name = source.readString();
        this.id = source.readInt();
        this.main = source.readParcelable(MainInfo.class.getClassLoader());
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCityIdentifier() {
        return id;
    }

    public void setCityIdentifier(int cityIdentifier) {
        this.id = cityIdentifier;
    }

    public MainInfo getMain() {
        return main;
    }

    public void setMain(MainInfo main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.id);
        dest.writeParcelable(this.main, flags);
    }
}
