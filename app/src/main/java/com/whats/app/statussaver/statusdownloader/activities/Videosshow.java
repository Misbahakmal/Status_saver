package com.whats.app.statussaver.statusdownloader.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

import com.whats.app.statussaver.statusdownloader.R;
import com.whats.app.statussaver.statusdownloader.fragments.BaseStatusesFragment;

import java.io.File;

public class Videosshow extends AppCompatActivity {
    VideoView videoviewaaaaaaaaaaaa;
    String videommmmm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view);
        videoviewaaaaaaaaaaaa= (VideoView) findViewById(R.id.VideoView);

        try {
            MediaController mediacontroller = new MediaController(
                    Videosshow.this);
            mediacontroller.setAnchorView(videoviewaaaaaaaaaaaa);
            Uri video = Uri.parse(String.valueOf(Uri.fromFile(new File(String.valueOf(BaseStatusesFragment.file)))));
            videoviewaaaaaaaaaaaa.setMediaController(mediacontroller);
            videoviewaaaaaaaaaaaa.setVideoURI(video);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        videoviewaaaaaaaaaaaa.requestFocus();
        videoviewaaaaaaaaaaaa.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                videoviewaaaaaaaaaaaa.start();
            }
        });


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.video_share, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.mSharevideo:
                shareVideoWhatsApp();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void shareVideoWhatsApp() {
        Uri uri = Uri.fromFile(new File(String.valueOf(BaseStatusesFragment.file)));
        Intent videoshare = new Intent(Intent.ACTION_SEND);
        videoshare.setType("video/mp4");
        videoshare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        videoshare.putExtra(Intent.EXTRA_STREAM,uri);

        startActivity(videoshare);

    }


}
