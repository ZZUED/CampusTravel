package com.zzued.campustravel.constant;


public class Constant {
    public static String Spot;

    //    private static String MengLei = "http://maxerwinsmith.qicp.io:49291";
    private static String MengLei = "http://120.78.213.131:8080/guide_war";

    public static String Url_HomePageActivity = MengLei + "/map/requestMainInterfaceScenicSpotInfos?";
    //发送经纬度，获取homepage下面recycleview的内容

    public static String Url_LoginActivity = MengLei + "/login";
    //登录

    public static String Url_RigisterActivity_one = MengLei + "/sendEmailAndReturnState?emailAddress=";
    //注册——获取验证码

    public static String Url_RigisterActivity_two = MengLei + "/registerWithEmailAddress";
    //注册——完成注册

    public static String Url_SeeProfileActivity = MengLei + "/findARecordByEmailAndPassword";
    //查看个人信息

    public static String Url_ModifyPasswordActivity = MengLei + "/guide_war/updatePassword";
    //修改密码接口

    public static String Url_ModifyProfileActivity = MengLei + "/guide_war/updateUserByEmailAddressAndPassword";
    //修改个人信息

    public static String Url_HomeLeftFragment = MengLei + "/scenicArea/getScenicAreaInfoById?scenicAreaId=1";
    //景区介绍

    public static String Url_ScenicAreaIntroduceActivity = MengLei + "/map/searchScenicSpotsByAreaId?areaId=1&start=10&length=30";
    //获取景区介绍里的recycle view内容

    public static String Url_VoiceIntroActivity = MengLei + "/voice/requestVoiceExplain?";

    public static String Url_SearchActivity = MengLei + "/map/requestSearchScenicSpotByName?name=";
    //搜索

    public static String Url_ScenicSpotActivity = MengLei + "/map/requestScenicSpotIntroduceInfo?scenicSpotId=";
    //景点介绍

    public static String Url_HomeMiddleFragment = MengLei + "/map/requestSearchScenicSpotRandom";

    public static String Url_CouponActivity = MengLei + "/findAllRecord";

    public static void addSpot(String newSpot) {
        Spot = newSpot;
    }
}
