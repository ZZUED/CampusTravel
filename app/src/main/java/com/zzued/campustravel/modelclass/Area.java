package com.zzued.campustravel.modelclass;

public class Area {
    private String scenicAreaName;
    private String scenicAreaIntroduce;
    private String pictureUrl;

    public Area(String AreaName, String AreaIntro){
        this.scenicAreaName = AreaName;
        this.scenicAreaIntroduce = AreaIntro;
    }

    public void set_ScenicAreaName(String scenicAreaName){
        this.scenicAreaName = scenicAreaName;
    }

    public String get_ScenicAreaName() {
        return scenicAreaName;
    }

    public void set_SceniAreaIntro(String scenicAreaIntro){
        this.scenicAreaIntroduce = scenicAreaIntro;
    }

    public String get_SceniAreaIntro() {
        return scenicAreaIntroduce;
    }

    public String get_PictureUrl() {
        return pictureUrl;
    }

    public void set_PictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
