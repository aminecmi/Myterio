package com.amine.myterio.app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MainInfo implements Parcelable {
    public static final Parcelable.Creator<MainInfo> CREATOR = new Parcelable.Creator<MainInfo>() {
        @Override
        public MainInfo createFromParcel(Parcel source) {
            return new MainInfo(source);
        }

        @Override
        public MainInfo[] newArray(int size) {
            return new MainInfo[size];
        }
    };
    private float temp;
    private float temp_max;
    private float temp_min;

    public MainInfo(Parcel source) {
        this.temp = source.readFloat();
        this.temp_max = source.readFloat();
        this.temp_min = source.readFloat();
    }

    public int getTemp() {
        return Math.round(temp);
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public int getTemp_max() {
        return Math.round(temp_max);
    }

    public void setTemp_max(float temp_max) {
        this.temp_max = temp_max;
    }

    public int getTemp_min() {
        return Math.round(temp_min);
    }

    public void setTemp_min(float temp_min) {
        this.temp_min = temp_min;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.temp);
        dest.writeFloat(this.temp_max);
        dest.writeFloat(this.temp_min);
    }
}
