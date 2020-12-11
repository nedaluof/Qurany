package com.nedaluof.qurany.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.inject.Singleton;

/**
 * Created by nedaluof on 6/23/2020.
 */
@Singleton
public class PreferencesHelper {

    private final Context context;

    public PreferencesHelper(Context context) {
        this.context = context;
    }

    /**
     * Called to save supplied value in shared preferences against given key.
     * @param key     Key of value to save against
     * @param value   Value to save
     */
    public void saveToPrefs(String key, Object value) {
        WeakReference<Context> contextWeakReference = new WeakReference<>(context);
        if (contextWeakReference.get() != null) {
            SharedPreferences prefs =
                    PreferenceManager.getDefaultSharedPreferences(contextWeakReference.get());
            final SharedPreferences.Editor editor = prefs.edit();
            if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof String) {
                editor.putString(key, value.toString());
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            } else if (value instanceof Float) {
                editor.putFloat(key, (Float) value);
            } else if (value instanceof Double) {
                editor.putLong(key, Double.doubleToRawLongBits((double) value));
            }
            editor.apply();
        }
    }

    /**
     * Called to retrieve required value from shared preferences, identified by given key.
     * Default value will be returned of no value found or error occurred.
     * @param key          Key to find value against
     * @param defaultValue Value to return if no data found against given key
     * @return Return the value found against given key, default if not found or any error occurs
     */
    public Object getFromPrefs(String key, Object defaultValue) {
        WeakReference<Context> contextWeakReference = new WeakReference<>(context);
        if (contextWeakReference.get() != null) {
            SharedPreferences sharedPrefs =
                    PreferenceManager.getDefaultSharedPreferences(contextWeakReference.get());
            try {
                if (defaultValue instanceof String) {
                    return sharedPrefs.getString(key, defaultValue.toString());
                } else if (defaultValue instanceof Integer) {
                    return sharedPrefs.getInt(key, (Integer) defaultValue);
                } else if (defaultValue instanceof Boolean) {
                    return sharedPrefs.getBoolean(key, (Boolean) defaultValue);
                } else if (defaultValue instanceof Long) {
                    return sharedPrefs.getLong(key, (Long) defaultValue);
                } else if (defaultValue instanceof Float) {
                    return sharedPrefs.getFloat(key, (Float) defaultValue);
                } else if (defaultValue instanceof Double) {
                    return Double.longBitsToDouble(sharedPrefs.getLong(key, Double.doubleToLongBits((double) defaultValue)));
                }
            } catch (Exception e) {
                Log.e("Exception", e.getMessage());
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * @param key     Key to delete from SharedPreferences
     */
    public void removeFromPrefs(String key) {
        WeakReference<Context> contextWeakReference = new WeakReference<>(context);
        if (contextWeakReference.get() != null) {
            SharedPreferences prefs =
                    PreferenceManager.getDefaultSharedPreferences(contextWeakReference.get());
            final SharedPreferences.Editor editor = prefs.edit();
            editor.remove(key);
            editor.apply();
        }
    }

    public boolean hasKey(String key) {
        WeakReference<Context> contextWeakReference = new WeakReference<>(context);
        if (contextWeakReference.get() != null) {
            SharedPreferences prefs =
                    PreferenceManager.getDefaultSharedPreferences(contextWeakReference.get());
            return prefs.contains(key);
        }
        return false;
    }

    public void saveArrayList(ArrayList<String> list, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();

    }

    public ArrayList<String> getArrayList(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }


}
