package com.xlkx.testaidl;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    private List<Activity> allActivity=new ArrayList<>();
    private static final ActivityCollector ourInstance = new ActivityCollector();

    public static ActivityCollector getInstance() {
        return ourInstance;
    }

    private ActivityCollector() {
    }
    public void addActivity(Activity activity){
        allActivity.add(activity);
    }
    public void finishAll(){
        try {
            for(Activity activity:allActivity){
                activity.finish();
            }
        }catch (Exception e){

        }

    }
}
