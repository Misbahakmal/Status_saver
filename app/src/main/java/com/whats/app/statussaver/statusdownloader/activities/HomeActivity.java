package com.whats.app.statussaver.statusdownloader.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.whats.app.statussaver.statusdownloader.R;
import com.whats.app.statussaver.statusdownloader.activities.base.BaseAppCompatActivity;


public class HomeActivity extends BaseAppCompatActivity implements View.OnClickListener {
    Button download, savedimages, savedVideos, rateApp, moreApp, Shareapp;
    int PERMISSION_ALL = 1;
    LinearLayout layout_no, layoutrateus, layout_yes, adlayout;
    LinearLayout yesMore, NoMore;
    public static  int direc;

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET};
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        download = findViewById(R.id.downloadstatus);
        savedimages = findViewById(R.id.savedStatusimages);
        savedVideos = findViewById(R.id.savedStatusvideo);

        Shareapp = findViewById(R.id.shaaare);
        download.setOnClickListener(this);
        savedVideos.setOnClickListener(this);

        Shareapp.setOnClickListener(this);
        savedimages.setOnClickListener(this);
        AdView mAdView = (AdView) findViewById(R.id.bannerhome);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.downloadstatus:
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.setType("image/jpg");
                startActivity(i);


                break;
            case R.id.savedStatusimages:
                direc=0;
                Intent savedimg = new Intent(getApplicationContext(),ImageGallery.class);
                startActivity(savedimg);
//                if (mInterstitialAd.isLoaded()) {
//                    mInterstitialAd.show();
//                }
//                InterstitialAdmob();
                break;
            case R.id.savedStatusvideo:
                direc=1;
                Intent savedvideo = new Intent(getApplicationContext(), ImageGallery.class);
                startActivity(savedvideo);

                break;
            case R.id.shaaare:
                Intent shareit = new Intent(Intent.ACTION_SEND);
                shareit.setType("text/plain");
                shareit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                shareit.putExtra(
                        Intent.EXTRA_TEXT,
                        getResources().getString(R.string.app_name)
                                + ":\nhttps://play.google.com/store/apps/details?id="
                                + getPackageName());
                startActivity(shareit);
                break;
            default:
        }
    }

    @SuppressLint("MissingPermission")
    public void onexit() {
        LayoutInflater layout1 = LayoutInflater.from(this);
        View view1 = layout1.inflate(R.layout.exit_diloge, null);
        final AlertDialog.Builder gotoBuilder1 = new AlertDialog.Builder(this);
        gotoBuilder1.setView(view1);
        final AlertDialog gotoDialog1 = gotoBuilder1.create();
        //        NativeExpressAdView adViews = (NativeExpressAdView)dialog.findViewById(R.id.adViewnative);
//        adViews.loadAd(new AdRequest.Builder().build());
        adlayout = (LinearLayout) view1.findViewById(R.id.Ladview);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        adView.setAdUnitId(getResources().getString(R.string.bannerid));
        adlayout.addView(adView);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        adView.loadAd(adRequest1);
        layout_no = (LinearLayout) view1.findViewById(R.id.layout_no);
        layoutrateus = (LinearLayout) view1.findViewById(R.id.layoutrateus);
        layout_yes = (LinearLayout) view1.findViewById(R.id.layout_yes);
        layout_no.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                gotoDialog1.dismiss();
            }
        });
        layoutrateus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent mintent = new Intent(Intent.ACTION_VIEW);
                    mintent.setData(Uri.parse("market://details?id="
                            + getPackageName()));
                    startActivity(mintent);
                } catch (Exception e1) {
                    try {
                        Uri uriUrl = Uri
                                .parse("https://market.android.com/details?id="
                                        + getPackageName());
                        Intent launchBrowser = new Intent(Intent.ACTION_VIEW,
                                uriUrl);
                        startActivity(launchBrowser);
                    } catch (Exception e2) {
                        Toast.makeText(getApplicationContext(),
                                "No Application Found to open link",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                gotoDialog1.dismiss();
            }
        });
        layout_yes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                } catch (Exception e) {

                }
                gotoDialog1.dismiss();
                finish();

            }
        });

        gotoDialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        gotoDialog1.show();

    }



    @Override
    public void onBackPressed() {

        onexit();
    }
}

