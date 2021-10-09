package com.whats.app.statussaver.statusdownloader.activities.base;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by theapache64 on 17/7/17.
 */

public class BaseAppCompatActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
