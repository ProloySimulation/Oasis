package com.amtech.oasis.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {

    private static SharedPreferenceManager mInstance;
    private static Context mContext;

    public static final String SHARED_PREFERENCE_MANAGER = "shared_preference_manager";
    public static final String KEY_USER_TOKEN = "key_user_token";


    private SharedPreferenceManager(Context context){
        mContext = context;
    }

    public static synchronized SharedPreferenceManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new SharedPreferenceManager(context);
        }
        return mInstance;
    }

    public String GetUserToken(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERENCE_MANAGER, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_TOKEN,null);
    }

    public boolean SetUserToken(String token){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERENCE_MANAGER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_TOKEN,token);
        editor.apply();
        return true;
    }
}
