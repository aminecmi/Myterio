package com.amine.myterio.app.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class Weather {
    public String description;
    public String icon;
    public int id;
    public String main;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public Drawable getIconDrawable(Context c, String packageName) {
        String uri = "@mipmap/";

        if (this.getIcon().equals("01d")) {
            // 01d => ic_sun
            uri += "ic_sun";
        }
        else if(this.getIcon().equals("01n")) {
            // 01n => ic_moon
            uri += "ic_moon";
        }
        else if(this.getIcon().equals("02d")) {
            // 02d => ic_sunny_cloud
            uri += "ic_sunny_cloud";
        }
        else if(this.getIcon().equals("02n")) {
            // 02n => ic_moony_cloud
            uri += "ic_moony_cloud";
        }
        else if(this.getIcon().equals("03d") || this.getIcon().equals("03n")) {
            // 03d/03n => ic_cloud
            uri += "ic_cloud";
        }
        else if(this.getIcon().equals("04d") || this.getIcon().equals("04n")) {
            // 04d/04n => ic_coud_grey
            uri += "ic_coud_grey";
        }
        else if(this.getIcon().equals("09d") || this.getIcon().equals("09n")) {
            // 09d/09n => ic_rainy_rain
            uri += "ic_rainy";
        }
        else if(this.getIcon().equals("10d")) {
            // 10d => ic_sunny_rain
            uri += "ic_sunny_rain";
        }
        else if(this.getIcon().equals("10n")) {
            // 10n => ic_moony_rain
            uri += "ic_moony_rain";
        }
        else if(this.getIcon().equals("11d") || this.getIcon().equals("11n")) {
            // 11d/11n => ic_thunder
            uri += "ic_thunder";
        }
        else if(this.getIcon().equals("13d") || this.getIcon().equals("13n")) {
            // 13d/13n => ic_snow
            uri += "ic_snow";
        }
        else if(this.getIcon().equals("50d") || this.getIcon().equals("50n")) {
            // 50d/50n => ic_mist
            uri += "ic_mist";
        }

        int imageResource = c.getResources().getIdentifier(uri, null, packageName);
        return c.getResources().getDrawable(imageResource);
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
