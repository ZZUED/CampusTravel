package com.zzued.campustravel.modelclass;

public class ThermalData {
    private double lat;
    private double lng;
    private int views;

    public ThermalData(double lat, double lng, int views){
        this.views = views;
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
