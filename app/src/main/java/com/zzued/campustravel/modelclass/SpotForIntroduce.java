package com.zzued.campustravel.modelclass;

public class SpotForIntroduce {
    private int scenicSpotId;
    private String scenicSpotName;
    private String pictureUrl;
    private String scenicSpotIntroduce;

    public SpotForIntroduce(int SpotId, String SpotName, String SpotPictureUrl,String scenicSpotIntroduce){
        this.scenicSpotId = SpotId;
        this.scenicSpotName = SpotName;
        this.pictureUrl = SpotPictureUrl;
        this.scenicSpotIntroduce = scenicSpotIntroduce;
    }

    public int _getScenicSpotId() {
        return scenicSpotId;
    }

    public void _setScenicSpotId(int scenicSpotId) {
        this.scenicSpotId = scenicSpotId;
    }

    public String _getScenicSpotName() {
        return scenicSpotName;
    }

    public void _setScenicSpotName(String scenicSpotName) {
        this.scenicSpotName = scenicSpotName;
    }

    public String _getPictureUrl() {
        return pictureUrl;
    }

    public void _setScenicSpotIntroduce(String scenicSpotIntroduce){
        this.scenicSpotIntroduce = scenicSpotIntroduce;
    }

    public String _getScenicSpotIntroduce(){
        return scenicSpotIntroduce;
    }
}
