package com.zzued.campustravel.util;


public class Constant {
    //    private static String MengLei = "http://maxerwinsmith.qicp.io:49291";
    private static String MengLei = "http://119.23.224.182:11291/guide";

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

    public static String Url_ModifyPasswordActivity = MengLei + "/updatePassword";
    //修改密码接口

    public static String Url_ModifyProfileActivity = MengLei + "/updateUserByEmailAddressAndPassword";
    //修改个人信息

    public static String Url_HomeLeftFragment = MengLei + "/scenicArea/getScenicAreaInfoById?scenicAreaId=1";
    //景区介绍

    public static String Url_ScenicAreaIntroduceActivity = MengLei + "/map/searchScenicSpotsByAreaId?areaId=1&start=10&length=30";
    //获取景区介绍里的recycle view内容

    public static String Url_VoiceIntroActivity = MengLei + "/voice/requestVoiceExplain?";

    public static String Url_VoiceAssistActivity = MengLei + "/voice/requestIntroduceVoiceHelper?voiceHelper=";

    public static String Url_SearchActivity = MengLei + "/map/requestSearchScenicSpotByName?name=";
    //搜索

    public static String Url_ScenicSpotActivity = MengLei + "/map/requestScenicSpotIntroduceInfo?scenicSpotId=";
    //景点介绍

    public static String Url_GetThermalMapData = MengLei + "/map/getHotMapPoints";
    //热力图数据

    public static String Url_HomeMiddleFragment = MengLei + "/map/requestSearchScenicSpotRandom";
    //每日推荐

    public static String Url_CouponActivity = MengLei + "/findAllRecord";
    //优惠券
}
