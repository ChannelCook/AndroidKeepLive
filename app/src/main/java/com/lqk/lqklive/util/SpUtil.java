package com.lqk.lqklive.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

//import com.google.gson.Gson;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import com.google.gson.JsonPrimitive;
//import com.jdcloud.mt.aen.base.BaseApplication;

import com.lqk.lqklive.BaseApplication;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 本地持久化参数工具
 */
public class SpUtil {

    private static volatile SpUtil sInstance;
    private static final String EMPTY = "";
    private static final int DEFAULT = 0;
    private SharedPreferences mShared;
    private static final String AEN_SHARE_FILE = "aen_share_preference";

    public static SpUtil getInstance() {

        if (sInstance == null) {
            synchronized (SpUtil.class) {
                if (sInstance == null) {
                    sInstance = new SpUtil();
                }
            }
        }
        return sInstance;
    }

    private SpUtil() {
        try {
            mShared = BaseApplication.getInstance().getSharedPreferences(AEN_SHARE_FILE, Context.MODE_PRIVATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getString(String key, String defValue) {
        if (mShared == null) {
            return EMPTY;
        }
        return mShared.getString(key, defValue);
    }

    public void putString(String key, String value) {
        if (mShared == null) {
            return;
        }
        mShared.edit().putString(key, value).apply();
    }

    public long getLong(String key, long defValue) {
        return mShared.getLong(key, defValue);
    }

    public void putLong(String key, long value) {
        mShared.edit().putLong(key, value).apply();
    }

    public void putFloat(String key, float value) {
        mShared.edit().putFloat(key, value).apply();
    }

    public float getFloat(String key, float defValue) {
        return mShared.getFloat(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return mShared.getInt(key, defValue);
    }

    public void putInt(String key, int value) {
        mShared.edit().putInt(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        if (mShared == null) {
            return false;
        }
        return mShared.getBoolean(key, defValue);
    }

    public void putBoolean(String key, boolean value) {
        if (mShared == null) {
            return;
        }
        mShared.edit().putBoolean(key, value).apply();
    }

    public void delString(String key) {
        if (mShared == null) {
            return;
        }
        mShared.edit().remove(key).apply();
    }

//    public void putMap(String tag, LinkedHashMap<String, String> map) {
//        if (null == map || map.size() <= DEFAULT || mShared == null) {
//            return;
//        }
//        Gson gson = new Gson();
//        String strJson = gson.toJson(map);
//        mShared.edit().putString(tag, strJson).apply();
//    }

//    public <V> LinkedHashMap<String, V> getMap(String tag, Class<V> cls) {
//        LinkedHashMap<String, V> map = new LinkedHashMap<>();
//        if (mShared == null) {
//            return map;
//        }
//        String strJson = mShared.getString(tag, null);
//        if (TextUtils.isEmpty(strJson)) {
//            return map;
//        }
//        try {
//            Gson gson = new Gson();
//            JsonObject object = new JsonParser().parse(strJson).getAsJsonObject();
//            Set<Map.Entry<String, JsonElement>> entrySet = object.entrySet();
//            for (Map.Entry<String, JsonElement> entry : entrySet) {
//                String entryKey = entry.getKey();
//                JsonPrimitive value = (JsonPrimitive) entry.getValue();
//                map.put(entryKey, gson.fromJson(value, cls));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return map;
//
//    }

}
