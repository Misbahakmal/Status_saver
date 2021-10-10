package com.whats.app.statussaver.statusdownloader.activities;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.whats.app.statussaver.statusdownloader.R;
import com.whats.app.statussaver.statusdownloader.activities.base.BaseAppCompatActivity;
import com.whats.app.statussaver.statusdownloader.adapters.PagerAdapter;
import com.whats.app.statussaver.statusdownloader.utils.PermissionUtils;

public class MainActivity extends BaseAppCompatActivity implements PermissionUtils.Callback {


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new PermissionUtils(this, this, this).begin();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtils.RQ_CODE_ASK_PERMISSION) {

            boolean isAllPermissionGranted = true;
            for (final int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    isAllPermissionGranted = false;
                    break;
                }
            }

            if (isAllPermissionGranted) {
                onAllPermissionGranted();
            } else {
                onPermissionDenial();
            }
        }
    }

    @Override
    public void onAllPermissionGranted() {
        ViewPager vpStatuses = (ViewPager) findViewById(R.id.vpStatuses);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        vpStatuses.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(vpStatuses);
    }

    @Override
    public void onPermissionDenial() {
        Toast.makeText(this, R.string.Insufficient_app_permission, Toast.LENGTH_SHORT).show();
        finish();
    }
}
