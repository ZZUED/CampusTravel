package com.zzued.campustravel.constant;

import com.zzued.campustravel.modelclass.Spot;

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





    public static void addSpot(String newSpot) {
        Spot = newSpot;
    }
}
