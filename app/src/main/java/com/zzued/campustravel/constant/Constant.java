package com.zzued.campustravel.constant;


public class Constant {
    public static String Spot;

    public static String Url_HomePageActivity = "http://120.78.213.131:8080/guide_war/map/requestMainInterfaceScenicSpotInfos?";
    //发送经纬度，获取homepage下面recycleview的内容

    public static String Url_LoginActivity = "http://120.78.213.131:8080/guide_war/login";
    //登录

    public static String Url_RigisterActivity_one = "http://120.78.213.131:8080/guide_war/sendEmailAndReturnState?emailAddress=";
    //注册——获取验证码

    public static String Url_RigisterActivity_two = "http://120.78.213.131:8080/guide_war/registerWithEmailAddress";
    //注册——完成注册

    public static String Url_SeeProfileActivity = "http://120.78.213.131:8080/guide_war/findARecordByEmailAndPassword";
    //查看个人信息

    public static String Url_ModifyPasswordActivity = "http://120.78.213.131:8080/guide_war/updatePassword";
    //修改密码接口

    public static String Url_ModifyProfileActivity = "http://120.78.213.131:8080/guide_war/updateUserByEmailAddressAndPassword";
    //修改个人信息

    public static String Url_HomeLeftFragment =  "http://maxerwinsmith.qicp.io:49291/scenicArea/getScenicAreaInfoById?scenicAreaId=1";
    //景区介绍

    public static String Url_ScenicAreaIntroduceActivity =  "http://120.78.213.131:8080/guide_war/map/requestSearchScenicSpotByName?name=";
    //获取景区介绍里的recycleview内容

    public static String Url_VoiceIntroActivity = "http://maxerwinsmith.qicp.io:49291/voice/requestVoiceExplain?";

    public static String Url_SearchActivity = "http://maxerwinsmith.qicp.io:49291/map/requestSearchScenicSpotByName?name=";
    //搜索

    public static String Url_ScenicSpotActivity = "http://maxerwinsmith.qicp.io:49291/map/requestScenicSpotIntroduceInfo?scenicSpotId=";
    //景点介绍

    public static String Url_HomeMiddleFragment = "http://maxerwinsmith.qicp.io:49291/map/requestSearchScenicSpotRandom";

    public static String Url_CouponActivity = "http://maxerwinsmith.qicp.io:49291/findAllRecord";
    //

    public static void addSpot(String newSpot) {
        Spot = newSpot;
    }
}
