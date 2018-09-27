package com.zzued.campustravel.modelclass;

public class Intro {
    private String scenicSpotName;
    private String scenicSpotIntroduce;

    public Intro(String n, String i){
        this.scenicSpotName = n;
        this.scenicSpotIntroduce = i;
    }

    public void setScenicSpotName(String scenicSpotName) {
        this.scenicSpotName = scenicSpotName;
    }

    public void setScenicSpotIntroduce(String scenicSpotIntroduce) {
        this.scenicSpotIntroduce = scenicSpotIntroduce;
    }

    public String getScenicSpotIntroduce() {
        return scenicSpotIntroduce;
    }

    public String getScenicSpotName() {
        return scenicSpotName;
    }
}
