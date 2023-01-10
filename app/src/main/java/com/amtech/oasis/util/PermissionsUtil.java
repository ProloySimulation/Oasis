package com.amtech.oasis.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PermissionsUtil {

    private static PermissionsUtil permissionsUtil;
    private static Context context;

    public static final String PERMISSION_UTIL = "permission_util";
    public static final String KEY_READ_EXTERNAL_STORAGE = "key_read_external_storage";
    public static final String KEY_WRITE_EXTERNAL_STORAGE = "key_write_external_storage";

    private PermissionsUtil(Context context){
        this.context = context;
    }

    public static synchronized PermissionsUtil getInstance(Context context){
        if (permissionsUtil == null){
            permissionsUtil = new PermissionsUtil(context);
        }
        return permissionsUtil;
    }

    public void updateReadExternalStoragePermissionPreference(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PERMISSION_UTIL,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_READ_EXTERNAL_STORAGE,true);
        editor.apply();
    }

    public boolean checkReadExternalStoragePermissionPreference(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PERMISSION_UTIL,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_READ_EXTERNAL_STORAGE,false);
    }

    public void updateWriteExternalStoragePermissionPreference(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PERMISSION_UTIL,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_WRITE_EXTERNAL_STORAGE,true);
        editor.apply();
    }

    public boolean checkWriteExternalStoragePermissionPreference(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PERMISSION_UTIL,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_WRITE_EXTERNAL_STORAGE,false);
    }
}