package com.susin.icalendar.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 * 说明：
 *
 * @作者 Susin
 * @创建时间 2016/11/23 21:46
 */
public class Setting {

    public static SharedPreferences sPref = null;

    private static SharedPreferences getNewSharedPreferences(Context context){

        return getSharedPreferences(context, context.getPackageName()+"_NewValue");
    }

    public static SharedPreferences getSharedPreferences(Context context, String sharePreferencesName){

        if (sPref !=  null) {
            return sPref;
        }

        if (sharePreferencesName == null || sharePreferencesName.length() == 0) {

            sPref = PreferenceManager.getDefaultSharedPreferences(context);
        }else{

            sPref = context.getSharedPreferences(sharePreferencesName, Context.MODE_PRIVATE |Context.MODE_MULTI_PROCESS);
        }

        return sPref;
    }

    public static String getValue(Context context, String key){
        SharedPreferences prefs = getNewSharedPreferences(context);
        return prefs.getString(key, "false");
    }

    public static void setValue(Context context, String key, String value){
        SharedPreferences.Editor editor = getNewSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.commit();
    }
}