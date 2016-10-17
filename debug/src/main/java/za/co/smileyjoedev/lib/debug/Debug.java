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
 * Class to make logging with logcat easier.
 * <p>
 * Takes a default log tag so it doesn't need to be set each time, each
 * log call also takes a list of any type of object, so logging a {@link String} can easily be
 * logged with a {@link Integer}, for example.
 * <p>
 * All objects are formatted based on their type so they are easier to read.
 * <p>
 * All logs can turned on and off in the Manifest file so they can be automatically turned off
 * for release builds.
 *
 * Created by cody on 2016/10/15.
 */

public class Debug {
    // Tag for internal logs //
    private static final String TAG = "smileyjoedev_debug";

    private static String LOG_NAME = "";
    private static boolean ENABLED = false;
    private static ArrayList<Integer> TYPES = new ArrayList<Integer>();

    // Types //
    public static final int DEBUG = 1;
    public static final int ERROR = 2;
    public static final int INFO = 3;
    public static final int VERBOSE = 4;
    public static final int WARNING = 5;
    public static final int ALL = 6;
    public static final int NONE = 7;

    /**
     * Initialize the library, this can be called in the {@link android.app.Application} class.
     * <p>
     * Checks the manifest meta data and sets the required data:
     * <ul>
     *     <li>@string/lib_debug_meta_tag - the tag that will be used by default</li>
     *     <li>@string/lib_debug_meta_enabled - whether to enable the logs or not</li>
     * </ul>
     * <p>
     * If the tags are not present, an error is logged and the logging wont be enabled by default.
     *
     * @param context the current context
     */
    public static void init(Context context){
        try {
            // The tags that are used in the manifest //
            String keyTag = context.getString(R.string.lib_debug_meta_tag);
            String keyEnabled = context.getString(R.string.lib_debug_meta_enabled);

            // Get the info from the manifest //
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;

            // Get the settings out of the manifest info //
            LOG_NAME = bundle.getString(keyTag);
            ENABLED = bundle.getBoolean(keyEnabled);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, context.getString(R.string.error_no_meta_tags, e.getMessage()));
        } catch (NullPointerException e) {
            Log.e(TAG, context.getString(R.string.error_no_meta_tags, e.getMessage()));
        }
    }

    /**
     * Check if logging is enabled or not.
     *
     * @return whether logs are enabled or not
     */
    public static boolean isEnabled(){
        return ENABLED;
    }

    /**
     * Set the types of logs that can be logged, defaults to all types.
     *
     * @param types the types to allow to log
     */
    public static void setTYPES(ArrayList<Integer> types) {
        Debug.TYPES = types;
    }

    /**
     * Wrapper for {@link Log#d(String, String)}
     * <p>
     * Takes an array of any object type, these can be mixed, formats and logs the params.
     * <p>
     * The following are used:
     * <ul>
     *     <li>Tag - the tag set in the manifest</li>
     *     <li>Message - Formatted message from the params passed in</li>
     * </ul>
     *
     * @param params array of items to be logged
     */
    public static void d(Object... params) {
        if (Debug.isLogging(DEBUG)) {
            Log.d(LOG_NAME, Helper.handleMessage(params));
        }
    }

    /**
     * Wrapper for {@link Log#e(String, String)}
     * <p>
     * Takes an array of any object type, these can be mixed, formats and logs the params.
     * <p>
     * The following are used:
     * <ul>
     *     <li>Tag - the tag set in the manifest</li>
     *     <li>Message - Formatted message from the params passed in</li>
     * </ul>
     *
     * @param params array of items to be logged
     */
    public static void e(Object... params) {
        if (Debug.isLogging(ERROR)) {
            Log.e(Debug.LOG_NAME, Helper.handleMessage(params));
        }
    }

    /**
     * Wrapper for {@link Log#i(String, String)}
     * <p>
     * Takes an array of any object type, these can be mixed, formats and logs the params.
     * <p>
     * The following are used:
     * <ul>
     *     <li>Tag - the tag set in the manifest</li>
     *     <li>Message - Formatted message from the params passed in</li>
     * </ul>
     *
     * @param params array of items to be logged
     */
    public static void i(Object... params) {
        if (Debug.isLogging(INFO)) {
            Log.i(Debug.LOG_NAME, Helper.handleMessage(params));
        }
    }

    /**
     * Wrapper for {@link Log#v(String, String)}
     * <p>
     * Takes an array of any object type, these can be mixed, formats and logs the params.
     * <p>
     * The following are used:
     * <ul>
     *     <li>Tag - the tag set in the manifest</li>
     *     <li>Message - Formatted message from the params passed in</li>
     * </ul>
     *
     * @param params array of items to be logged
     */
    public static void v(Object... params) {
        if (Debug.isLogging(VERBOSE)) {
            Log.v(Debug.LOG_NAME, Helper.handleMessage(params));
        }
    }

    /**
     * Wrapper for {@link Log#w(String, String)}
     * <p>
     * Takes an array of any object type, these can be mixed, formats and logs the params.
     * <p>
     * The following are used:
     * <ul>
     *     <li>Tag - the tag set in the manifest</li>
     *     <li>Message - Formatted message from the params passed in</li>
     * </ul>
     *
     * @param params array of items to be logged
     */
    public static void w(Object... params) {
        if (Debug.isLogging(WARNING)) {
            Log.w(Debug.LOG_NAME, Helper.handleMessage(params));
        }
    }

    /**
     * Checks if the provided type is enabled to be logged.
     *
     * @param type the type being logged
     * @return whether the type is enabled to be logged.
     */
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
