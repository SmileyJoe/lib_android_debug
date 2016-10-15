package za.co.smileyjoedev.debug;

import android.app.Application;

import za.co.smileyjoedev.lib.debug.Debug;

/**
 * Created by cody on 2016/10/15.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Debug.init(getApplicationContext());
    }
}
