package za.co.smileyjoedev.debug;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import za.co.smileyjoedev.lib.debug.Debug;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Debug.d("This is a test", 1);
    }
}
