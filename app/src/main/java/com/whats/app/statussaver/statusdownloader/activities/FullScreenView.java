package com.whats.app.statussaver.statusdownloader.activities;

import android.content.Intent;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


import com.whats.app.statussaver.statusdownloader.R;
import com.whats.app.statussaver.statusdownloader.adapters.FullScreenImageAdapter;
import com.whats.app.statussaver.statusdownloader.models.GalleryModel;
import com.whats.app.statussaver.statusdownloader.utils.Utils;

import java.util.ArrayList;

public class FullScreenView extends AppCompatActivity {
   ViewPager vp;
    ArrayList<GalleryModel> galleryModels;
    FullScreenImageAdapter fullScreenImageAdapter;
    //private ArrayList<String> _imagePaths;
    private String paths;
    GalleryModel model;
    Utils utils;
    int position;
    int positionring;
    private ArrayList imagePaths = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_view);
        Intent i = getIntent();

        utils = new Utils(this);

// loading all image paths from SD card

        imagePaths = utils.getFilePaths();

// Selected image id

         position  = i.getExtras().getInt("position");
       // Bundle bdl = getIntent().getExtras();
//        Uri myuri= Uri.parse(bdl.getString("uri"));
      //  int pos  = bdl.getInt("position");
    //    Intent it = getIntent();
     //   positionring = it.getIntExtra("POS", 0);
     //   final int position = it.getIntExtra("POS", 0);
       // Toast.makeText(this, "pos"+pos, Toast.LENGTH_SHORT).show();
//        Intent it=getIntent();
//        int pos=it.getIntExtra("position",0);
//        _imagePaths=utils.getFilePaths();
        galleryModels = new ArrayList<>();
           // model = galleryModels.get(pos);


       // Toast.makeText(this, "pos"+model, Toast.LENGTH_SHORT).show();
        // paths=model.getPath();
      //  fullScreenImageAdapter=new FullScreenImageAdapter(this);
       // Collections.reverse(imagePaths);
        fullScreenImageAdapter = new FullScreenImageAdapter(FullScreenView.this,imagePaths);
        vp=findViewById(R.id.pager);
        vp.setAdapter(fullScreenImageAdapter);
        vp.setCurrentItem(position);

      //  String path = adapter._imagePaths.get(vp.getCurrentItem());
       // vp.setCurrentItem(pos);
    }
}
