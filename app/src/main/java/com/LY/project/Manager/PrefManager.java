package com.LY.project.Manager;

import android.content.Context;

/**
 * 对SharePreference的封装
 */
public class PrefManager {

    public static final String TOKEN = "TOKEN";//用户Token
    public static final String USERNAME = "USERNAME";//用户名
    public static final String ADDRESS = "ADDRESS";//用户地址

    public static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static void setToken(String token) {
        PrefUtils.putString(TOKEN, token, mContext);
    }

    public static String getToken() {
        return PrefUtils.getString(TOKEN, "882c003d25143cf14984f38add864df1", mContext);
    }

    public static void setUsername(String username) {
        PrefUtils.putString(USERNAME, username, mContext);
    }

    public static String getUsername() {
        return PrefUtils.getString(USERNAME, "", mContext);
    }


    public static void setAddress(String address) {
        PrefUtils.putString(ADDRESS, address, mContext);
    }

    public static String getAddress() {
        return PrefUtils.getString(ADDRESS, "", mContext);
    }

}
