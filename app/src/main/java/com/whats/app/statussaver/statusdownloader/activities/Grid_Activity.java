package com.whats.app.statussaver.statusdownloader.activities;


import java.io.File;
import java.io.IOException;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.whats.app.statussaver.statusdownloader.R;
import com.whats.app.statussaver.statusdownloader.utils.APIRequestBuilder;
import com.whats.app.statussaver.statusdownloader.utils.APIRequestGateway;
import com.whats.app.statussaver.statusdownloader.utils.OkHttpUtils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.Toast;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class  Grid_Activity extends AppCompatActivity {
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    GridView grid;
    GridViewAdapter adapter;
    File file;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_layout);
        grid = (GridView) findViewById(R.id.gridView2);
        AdView mAdView = (AdView) findViewById(R.id.banner111);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG).show();
        } else {
            file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "StatusImages");
            file.mkdirs();
        }

        if (file.isDirectory()) {
            listFile = file.listFiles();

            FilePathStrings = new String[listFile.length];

            FileNameStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++) {

                FilePathStrings[i] = listFile[i].getAbsolutePath();

                FileNameStrings[i] = listFile[i].getName();
            }
        }


        adapter = new GridViewAdapter(this, FilePathStrings, FileNameStrings);

        grid.setAdapter(adapter);



    }

    private void addToDb(final String type, final String actionType) {

        //Updating analytics
        new APIRequestGateway(getApplicationContext(), new APIRequestGateway.APIRequestGatewayCallback() {

            @Override
            public void onReadyToRequest(String apiKey) {

                System.out.println("Api key loaded " + apiKey);

                final Request addDownloadRequest = new APIRequestBuilder("/add_download", apiKey)
                        .addParam("type", type)
                        .addParam("action_type", actionType)
                        .build();

                OkHttpUtils.getInstance().getClient().newCall(addDownloadRequest).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        OkHttpUtils.logAndGetStringBody(response);
                    }
                });

            }

            @Override
            public void onFailed(String reason) {
//                Log.e(X, "ERRRRRRRROR: " + reason);
            }
        });
    }


}


