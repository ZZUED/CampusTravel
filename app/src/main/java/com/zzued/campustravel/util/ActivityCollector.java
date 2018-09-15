package com.zzued.campustravel.util;

import android.app.Activity;

import java.util.ArrayList;

public class ActivityCollector {
    private static ArrayList<Activity> activityArrayList = new ArrayList<>();

    public static void add(Activity activity) {
        activityArrayList.add(activity);
    }

    /**
     * 从队列中移除指定活动
     * @param activity 待移除的活动
     */
    public static void remove(Activity activity) {
        activityArrayList.remove(activity);
    }

    /**
     * 销毁队列中的所有活动
     */
    public static void finishAll(){
        for (Activity activity: activityArrayList)
            if (!activity.isFinishing())
                activity.finish();
    }
    /**
     * 从0开始依次销毁活动队列中的num个活动
     * @param num 待销毁的活动的个数
     *             若num大于队列中的活动个数，则不销毁任何活动
     */
    public static void finishFromStart(int num) {
        int sz = activityArrayList.size();
        if (num <= sz)
            for (int i = num - 1; i >= 0; i--)
                if (!activityArrayList.get(i).isFinishing())
                    activityArrayList.get(i).finish();
    }

    public static int size(){
        return activityArrayList.size();
    }
}
