package com.furkanbalci.mesat.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LocalDataManager {

    private static final Gson GSON = new Gson();

    public static void setString(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static void setLong(Context context, String key, long value) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putLong(key, value);
        edit.apply();
    }

    public static String getString(Context context, String key, String defaultValue) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).getString(key, defaultValue);
    }

    public static long getLong(Context context, String key, long defaultValue) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).getLong(key, defaultValue);
    }

    public static void clear(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.clear();
        edit.apply();
    }

    public static void remove(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.remove(key);
        edit.apply();
    }

    @Nullable
    public static <T> T get(@Nonnull Context context, @Nonnull String key, @Nonnull Class<T> clazz) {
        String object = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).getString(key, null);
        if (object == null) return null;
        return GSON.fromJson(object, clazz);
    }

    public static void put(@Nonnull Context context, @Nonnull String key, @Nonnull Object object) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putString(key, GSON.toJson(object));
        edit.apply();
    }
}