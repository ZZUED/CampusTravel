package com.campustravel.zzued.campustravel.tool;

import android.app.Activity;

import java.util.ArrayList;

public class ActivityCollector {
    private static ArrayList<Activity> activities = new ArrayList<>();

    /**
     * 将一个活动添加到活动集中
     * @param activity 待添加的活动
     */
    public static void add(Activity activity){
        activities.add(activity);
    }

    /**
     * 从活动集中移除一个活动（不销毁此活动）
     * 此方法应当在活动销毁时调用
     * @param activity 待移除的活动
     */
    public static void remove(Activity activity){
        activities.remove(activity);
    }

    /**
     * 销毁所有活动
     */
    public static void finishAll(){
        for (Activity activity : activities){
            if (!activity.isFinishing())
                activity.finish();
        }
    }

    /**
     * 销毁从第一个活动开始的所有 sz 个活动
     * @param sz 待销毁的活动数目
     */
    public static void finish(int sz){
        for (int i = sz - 1; i >= 0; i--){
            if (!activities.get(i).isFinishing())
                activities.get(i).finish();
        }
    }
}
