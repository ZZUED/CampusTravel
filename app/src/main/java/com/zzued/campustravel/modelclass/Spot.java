package com.zzued.campustravel.modelclass;

public class Spot {
    private int scenicSpotId;
    private String scenicSpotName;
    private String pictureUrl;

    public Spot(int SpotId, String SpotName, String SpotPictureUrl){
        this.scenicSpotId = SpotId;
        this.scenicSpotName = SpotName;
        this.pictureUrl = SpotPictureUrl;
    }

    public int getScenicSpotId() {
        return scenicSpotId;
    }

    public void setScenicSpotId(int scenicSpotId) {
        this.scenicSpotId = scenicSpotId;
    }

    public String getScenicSpotName() {
        return scenicSpotName;
    }

    public void setScenicSpotName(String scenicSpotName) {
        this.scenicSpotName = scenicSpotName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    @Override
    public String toString() {
        return "spot: id=" + scenicSpotId + " name:" + scenicSpotName + " url:" + pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
