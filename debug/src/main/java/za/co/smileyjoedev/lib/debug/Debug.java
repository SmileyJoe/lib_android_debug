package za.co.smileyjoedev.lib.debug;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cody on 2016/10/15.
 */

public class Debug {

    private static final String TAG = "smileyjoedev_debug";

    private static String LOG_NAME = "";
    private static final String LINE_BREAK = "\n";
    private static boolean ENABLED = true;
    private static ArrayList<Integer> TYPES = new ArrayList<Integer>();

    // Types //
    public static final int DEBUG = 1;
    public static final int ERROR = 2;
    public static final int INFO = 3;
    public static final int VERBOSE = 4;
    public static final int WARNING = 5;
    public static final int ALL = 6;
    public static final int NONE = 7;

    public static void init(Context context){
        try {
            String keyTag = context.getString(R.string.lib_debug_meta_tag);
            String keyEnabled = context.getString(R.string.lib_debug_meta_enabled);
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            LOG_NAME = bundle.getString(keyTag);
            ENABLED = bundle.getBoolean(keyEnabled);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, context.getString(R.string.error_no_meta_tags, e.getMessage()));
        } catch (NullPointerException e) {
            Log.e(TAG, context.getString(R.string.error_no_meta_tags, e.getMessage()));
        }
    }

    public static boolean isEnabled(){
        return ENABLED;
    }

    public static void setTYPES(ArrayList<Integer> types) {
        Debug.TYPES = types;
    }

    public static void d(Object... params) {
        if (Debug.isLogging(DEBUG)) {
            Log.d(LOG_NAME, Helper.handleMessage(params));
        }
    }

    public static void e(Object... params) {
        if (Debug.isLogging(ERROR)) {
            Log.e(Debug.LOG_NAME, Helper.handleMessage(params));
        }
    }

    public static void i(Object... params) {
        if (Debug.isLogging(INFO)) {
            Log.i(Debug.LOG_NAME, Helper.handleMessage(params));
        }
    }

    public static void v(Object... params) {
        if (Debug.isLogging(VERBOSE)) {
            Log.v(Debug.LOG_NAME, Helper.handleMessage(params));
        }
    }

    public static void w(Object... params) {
        if (Debug.isLogging(WARNING)) {
            Log.w(Debug.LOG_NAME, Helper.handleMessage(params));
        }
    }

    private static boolean isLogging(int type) {
        boolean isLogging = false;

        if (ENABLED) {
            if (TYPES.size() == 0) {
                TYPES.add(ALL);
            }
            if (TYPES.contains(type)) {
                isLogging = true;
            } else if (TYPES.contains(ALL)) {
                isLogging = true;
            }
        }

        return isLogging;
    }
}
