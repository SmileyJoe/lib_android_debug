package za.co.smileyjoedev.debug;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cody on 2016/10/15.
 */

public class Debug {

    private static final String TAG = "smileyjoedev.debug";

    private static String LOG_NAME = "";
    private static final String LINE_BREAK = "\n";
    private static boolean ENABLED = true;
    private static int[] LOG_TYPES;

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
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            LOG_NAME = bundle.getString("debug_tag");
            ENABLED = bundle.getBoolean("debug_enabled");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, context.getString(R.string.error_no_meta_tags, e.getMessage()));
        } catch (NullPointerException e) {
            Log.e(TAG, context.getString(R.string.error_no_meta_tags, e.getMessage()));
        }
    }

    public static boolean isEnabled(){
        return ENABLED;
    }

    public static void setLogTypes(int... logTypes) {
        LOG_TYPES = logTypes;
    }

    public static void d(Object... params) {
        if (Debug.isLogging(DEBUG)) {
            Log.d(Debug.LOG_NAME, Helper.handleMessage(params));
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
            if (LOG_TYPES == null || LOG_TYPES.length == 0) {
                LOG_TYPES = new int[]{ALL};
            }

            List typeList = Arrays.asList(LOG_TYPES);

            if (typeList.contains(type)) {
                isLogging = true;
            } else if (typeList.contains(ALL)) {
                isLogging = true;
            }
        }

        return isLogging;
    }
}
