package com.rever.rever_b2b.utils;

/**
 * Created by Matheswari on 6/2/2016.
 */

import android.content.SharedPreferences;

/**
 * Created by maniselvaraj on 14/6/15.
 */
public class SharedPreferenceManager {

    private static SharedPreferences preferences;
    public static String SESSION_TOKEN = "rever_session_token";
    public static String USER_TYPE = "rever_user_type";

    public static void initializePreferenceManager(SharedPreferences _preferences) {
        preferences = _preferences;
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public static void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    public static void setInt(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static Long getLong(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }

    public static void setLong(String key, long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static String getString(String key,String defaultValue) {
        return preferences.getString(key,defaultValue);
    }

    public static void setString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}

