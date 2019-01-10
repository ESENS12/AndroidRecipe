package com.example.esens.cardviewtest;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

public class RouteCardData {
    String type;
    int color;
    Drawable icon;
    Boolean showCharge;
    String charge;
    String distance;
    String time;

    public RouteCardData(String type, int color, Drawable icon, Boolean showCharge, String charge, String distance, String time) {
        this.type = type;
        this.color = color;
        this.icon = icon;
        this.showCharge = showCharge;
        this.charge = charge;
        this.distance = distance;
        this.time = time;
    }

    public RouteCardData() {
        this.color = Color.BLUE;
        this.showCharge = true;
        this.charge = "4";
        this.distance = "100401km";
        this.time = "4시간5분";
        this.type = "TEST";
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    public Drawable getIcon() {
        return icon;
    }

    public Boolean getShowCharge() {
        return showCharge;
    }

    public String getCharge() {
        return charge;
    }

    public String getDistance() {
        return distance;
    }

    public String getTime() {
        return time;
    }
}
