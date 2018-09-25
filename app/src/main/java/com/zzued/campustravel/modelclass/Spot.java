package com.zzued.campustravel.modelclass;

public class Spot {
    private String SpotId;
    private String SpotName;
    private String SpotPictureUrl;

    public Spot(String SpotId,String SpotName,String SpotPictureUrl){
        this.SpotId = SpotId;
        this.SpotName = SpotName;
        this.SpotPictureUrl = SpotPictureUrl;
    }

    public String getSpotId() {
        return SpotId;
    }

    public void setSpotId(String spotId) {
        SpotId = spotId;
    }

    public String getSpotName() {
        return SpotName;
    }

    public void setSpotName(String spotName) {
        SpotName = spotName;
    }

    public String getSpotPictureUrl() {
        return SpotPictureUrl;
    }

    public void setSpotPictureUrl(String spotPictureUrl) {
        SpotPictureUrl = spotPictureUrl;
    }
}
