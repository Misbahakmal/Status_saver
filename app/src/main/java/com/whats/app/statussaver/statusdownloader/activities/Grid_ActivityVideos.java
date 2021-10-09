package com.whats.app.statussaver.statusdownloader.activities;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.whats.app.statussaver.statusdownloader.R;

import java.io.File;


public class Grid_ActivityVideos extends AppCompatActivity {



	private static final String MEDIA_PATH = new String(
			"/sdcard/StatusVideos/");

	String[] fileList = null;
	GridView gridView;
	String FILE_PATH = "/sdcard/StatusVideos/";
	String MiME_TYPE = "video/mp4";

	@SuppressLint("MissingPermission")
	@Override
	public void onCreate(Bundle savedInstanceState) {



		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		updateSongList();
		gridView = (GridView) findViewById(R.id.gridView1);
		AdView mAdView = (AdView) findViewById(R.id.banner222);
		AdRequest adRequest = new AdRequest.Builder()
				.build();
		mAdView.loadAd(adRequest);
		if (fileList != null) {
			gridView.setAdapter(new ImageAdapter(this, fileList));
		}
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
									int position, long id) {
//				Toast.makeText(
//						getApplicationContext();
				String videoFilePath = FILE_PATH + fileList[position];
				System.out.println("******************************videoFilePath****************" + videoFilePath);

				System.out.println("******************************MiME_TYPE****************" + MiME_TYPE);
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
				File newFile = new File(videoFilePath);
				intent.setDataAndType(Uri.fromFile(newFile), MiME_TYPE);
				startActivity(intent);
			}
		});



	}



	public void updateSongList() {
		File videoFiles = new File(MEDIA_PATH);
	//	Log.d("*********Value of videoFiles******", videoFiles.toString());



		if (videoFiles.isDirectory()) {
			fileList = videoFiles.list();
		}
		if (fileList == null) {
			System.out.println("File doesnot exit");
			Toast.makeText(this, "There is no file", Toast.LENGTH_SHORT).show();
		} else {
			System.out.println("fileList****************" + fileList);
			for (int i = 0; i < fileList.length; i++) {
				Log.e("Video:" + i + " File name", fileList[i]);



			}
		}



	}



	public class ImageAdapter extends BaseAdapter {
		private Context context;



		private final String[] VideoValues;



		public ImageAdapter(Context context, String[] VideoValues) {
			this.context = context;
			this.VideoValues = VideoValues;
		}



		public View getView(int position, View convertView, ViewGroup parent) {
			System.out.println("***********IngetView************");
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



			View gridView;



			if (convertView == null) {



				gridView = new View(context);



				// get layout from gridlayout.xml
				gridView = inflater.inflate(R.layout.gridlayout, null);



				// set value into textview
//				TextView textView = (TextView) gridView
//						.findViewById(R.id.grid_item_label);
//				textView.setText(fileList[position]);
				System.out.println("value of fileList[position]" + fileList[0]);
				// set image
				ImageView imageThumbnail = (ImageView) gridView
						.findViewById(R.id.grid_item_image);



				Bitmap bmThumbnail;



				System.out
						.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>> file path>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
								+ fileList[position]);



				bmThumbnail = ThumbnailUtils.createVideoThumbnail(FILE_PATH
								+ fileList[position],
						MediaStore.Video.Thumbnails.MINI_KIND);
				if (bmThumbnail != null) {
					System.out
							.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>> THUMB NAIL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");



					imageThumbnail.setImageBitmap(bmThumbnail);
				} else {
					System.out
							.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>NO THUMB NAIL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");



				}



			} else {
				gridView = (View) convertView;
			}



			return gridView;
		}



		public int getCount() {
			// return 0;
			return VideoValues.length;
		}



		public Object getItem(int position) {
			return null;
		}



		public long getItemId(int position) {
			return 0;
		}



	}
}