package com.whats.app.statussaver.statusdownloader.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.whats.app.statussaver.statusdownloader.R;

public class GridViewAdapter extends BaseAdapter {

    // Declare variables
    private Activity activity;
    public  static String[] filepath;
    private String[] filename;
    private static LayoutInflater inflater = null;

    public GridViewAdapter(Activity a, String[] fpath, String[] fname) {
        activity = a;
        filepath = fpath;
        filename = fname;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return filepath.length;

    }

    public Object getItem(int position) {
        return position;

    }

    public long getItemId(int position) {
        return position;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.gridview_item, null);

        // Locate the ImageView in gridview_item.xml
        ImageView image = (ImageView) vi.findViewById(R.id.img_tumb);

        // Decode the filepath with BitmapFactory followed by the position
        Bitmap bmp = BitmapFactory.decodeFile(filepath[position]);
        Log.i("pos", "pos" + position);
        // Set the decoded bitmap into ImageView
        image.setImageBitmap(bmp);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(activity, "Choose app to share" + position, Toast.LENGTH_SHORT).show();
                //Toast here
                Intent intent = new Intent(activity, ImageShow.class);
                intent.putExtra("imageID", position);
                activity.startActivity(intent);

            }
        });
        return vi;

    }
}
