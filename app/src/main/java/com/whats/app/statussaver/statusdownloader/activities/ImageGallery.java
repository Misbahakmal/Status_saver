
package com.whats.app.statussaver.statusdownloader.activities;

        import android.annotation.SuppressLint;
        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Environment;
        import android.os.Handler;
        import android.os.Parcelable;
        import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.GridLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;
        import androidx.appcompat.widget.Toolbar;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.Toast;

        import com.google.android.gms.ads.AdListener;
        import com.google.android.gms.ads.AdRequest;
        import com.google.android.gms.ads.AdView;

        import com.google.android.gms.ads.interstitial.InterstitialAd;
        import com.whats.app.statussaver.statusdownloader.R;
        import com.whats.app.statussaver.statusdownloader.adapters.GalleryAdapter;
        import com.whats.app.statussaver.statusdownloader.models.GalleryModel;

        import java.io.File;
        import java.util.ArrayList;

public class ImageGallery extends AppCompatActivity {
    public  static  Toolbar mToolbar;
    private GalleryAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private File[] files;
    private SwipeRefreshLayout recyclerLayout;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;
    Parcelable listState;
    CheckBox chk_select_all;
    Button btn_delete_all;
    ArrayList<GalleryModel> filesList;
    File dir;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_gallery);
        initComponents();
        AdView mAdView = (AdView) findViewById(R.id.bannerg);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);

//        setSupportActionBar(mToolbar);
   //     getSupportActionBar().setDisplayHomeAsUpEnabled(true);
   //     getSupportActionBar().setDisplayShowTitleEnabled(false);
        setUpRecyclerView();
        chk_select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chk_select_all.isChecked()) {

                    for (GalleryModel model : filesList) {
                        model.setSelected(true);
                    }
                } else {

                    for (GalleryModel model : filesList) {
                        model.setSelected(false);
                    }
                }

                recyclerViewAdapter.notifyDataSetChanged();
            }
        });

        btn_delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

//                if (chk_select_all.isChecked()) {
////                    GalleryModel f;
////                    String destPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.SAVE_FOLDER_NAME;
////                    File destFile = new File(destPath);
////                    filesList.remove(destFile);
//                    File dir = new File(Environment.getExternalStorageDirectory() + Constants.SAVE_FOLDER_NAME);
//                    if (dir.isDirectory()) {
//                        String[] children = dir.list();
//                        for (int i = 0; i < children.length; i++) {
//                            new File(dir, children[i]).delete();
//                        }
//                    }
//                    filesList.clear();
//                    recyclerViewAdapter.notifyDataSetChanged();
//                    chk_select_all.setChecked(false);
//                } else {
//                    Snackbar.make(v, "Please click on select all check box, to delete all items.", Snackbar.LENGTH_LONG).show();
//                }
                if (chk_select_all.isChecked()) {
                    if(HomeActivity.direc==0) {
                         dir = new File(Environment.getExternalStorageDirectory() + File.separator + "StatusImages");
                    }
                    if(HomeActivity.direc==1) {
                        dir = new File(Environment.getExternalStorageDirectory() + File.separator + "StatusVideos");
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(ImageGallery.this);

                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure you want to delete this?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                            try {
                                if (dir.isDirectory()) {
                                    String[] children = dir.list();
                                    for (int i = 0; i < children.length; i++) {
                                        new File(dir, children[i]).delete();
                                    }
                                }
                                filesList.clear();
                                recyclerViewAdapter.notifyDataSetChanged();
                                //   chk_select_all.setChecked(false);
                                dialog.dismiss();
                                chk_select_all.setChecked(false);

                            } catch (Exception e) {
                                // TODO let the user know the file couldn't be deleted
                                e.printStackTrace();
                            }
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
//                    FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(ImageGallery.this)
//                            .setTextTitle("DELETE?")
//                            .setBody("Are you sure you want to delete all files?")
//                            .setNegativeButtonText("Cancel")
//                            .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
//                                @Override
//                                public void OnClick(View view, Dialog dialog) {
//                                    dialog.dismiss();
//                                }
//                            })
//                            .setPositiveButtonText("Delete")
//                            .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
//                                @Override
//                                public void OnClick(View view, Dialog dialog) {
//                                    try {
//                                        if (dir.isDirectory()) {
//                                            String[] children = dir.list();
//                                            for (int i = 0; i < children.length; i++) {
//                                                new File(dir, children[i]).delete();
//                                            }
//                                        }
//                                        filesList.clear();
//                                        recyclerViewAdapter.notifyDataSetChanged();
//                                        //   chk_select_all.setChecked(false);
//                                        dialog.dismiss();
//                                        chk_select_all.setChecked(false);
//
//                                    } catch (Exception e) {
//                                        // TODO let the user know the file couldn't be deleted
//                                        e.printStackTrace();
//                                    }
//                                }
//                            })
//                            .build();
//                    alert.show();

                } else {
                    Toast.makeText(ImageGallery.this, "To delete All image you need to check Select All", Toast.LENGTH_SHORT).show();
//                    if(direc==0) {
//                        dir = new File(Environment.getExternalStorageDirectory() + File.separator + "StatusImages");
//                    }
//                    if(direc==1) {
//                        dir = new File(Environment.getExternalStorageDirectory() + File.separator + "StatusVideos");
//                    }
//                    AlertDialog.Builder builder = new AlertDialog.Builder(ImageGallery.this);
//
//                    builder.setTitle("Confirm");
//                    builder.setMessage("Are you sure you want to delete this?");
//
//                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//
//                        public void onClick(DialogInterface dialog, int which) {
//                            // Do nothing but close the dialog
//
//                            try {
//                                String[] children = dir.list();
//                                for (int i = 0; i < children.length; i++) {
//                                    new File(dir, children[seriesOfNumbers.get(i)]).delete();
//                                    Snackbar.make(v, "selected items deleted .", Snackbar.LENGTH_LONG).show();
//                                    chk_select_all.setChecked(false);
//                                    dialog.dismiss();
//                                    //  recyclerViewAdapter.notifyItemRemoved(seriesOfNumbers.get(i));
//                                    //  filesList.remove(seriesOfNumbers.get(i));
//                                    recyclerViewAdapter.notifyDataSetChanged();
//                                    MediaScannerConnection.scanFile(ImageGallery.this, new String[]{ new File(dir, children[seriesOfNumbers.get(i)]).getAbsolutePath()}, null, null);
//                                    setUpRecyclerView();
//                                }
//
//
//
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//
//                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            // Do nothing
//                            dialog.dismiss();
//                        }
//                    });
//
//                    AlertDialog alert = builder.create();
//                    alert.show();
//                    FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(ImageGallery.this)
//                            .setTextTitle("DELETE?")
//                            .setBody("Are you sure you want to delete all files?")
//                            .setNegativeButtonText("Cancel")
//                            .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
//                                @Override
//                                public void OnClick(View view, Dialog dialog) {
//                                    dialog.dismiss();
//                                }
//                            })
//                            .setPositiveButtonText("Delete")
//                            .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
//                                @Override
//                                public void OnClick(View view, Dialog dialog) {
//                                    try {
//                                        String[] children = dir.list();
//                                        for (int i = 0; i < children.length; i++) {
//                                            new File(dir, children[seriesOfNumbers.get(i)]).delete();
//                                            Snackbar.make(v, "selected items deleted .", Snackbar.LENGTH_LONG).show();
//                                            chk_select_all.setChecked(false);
//                                            dialog.dismiss();
//                                            //  recyclerViewAdapter.notifyItemRemoved(seriesOfNumbers.get(i));
//                                            //  filesList.remove(seriesOfNumbers.get(i));
//                                            recyclerViewAdapter.notifyDataSetChanged();
//                                            MediaScannerConnection.scanFile(ImageGallery.this, new String[]{ new File(dir, children[seriesOfNumbers.get(i)]).getAbsolutePath()}, null, null);
//                                            setUpRecyclerView();
//                                        }
//
//
//
//
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            })
//                            .build();
//                    alert.show();

                }
            }
        });

    }

    private void setUpRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
       // linearLayoutManager.setStackFromEnd(true);
    //    recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter = new GalleryAdapter(ImageGallery.this, getData());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

    }

    private ArrayList<GalleryModel> getData() {
        filesList = new ArrayList<>();
        GalleryModel f;
        if(HomeActivity.direc==0) {
            String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "StatusImages";

            File targetDirector = new File(targetPath);
            files = targetDirector.listFiles();
        }
        if(HomeActivity.direc==1) {
            String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "StatusVideos";

            File targetDirector = new File(targetPath);
            files = targetDirector.listFiles();
        }
        if (files == null) {
//            noImageText.setVisibility(View.INVISIBLE);
        }
        try {
//            Arrays.sort(files, new Comparator() {
//                public int compare(Object o1, Object o2) {
//
//                    if (((File) o1).lastModified() > ((File) o2).lastModified()) {
//                        return -1;
//                    } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
//                        return +1;
//                    } else {
//                        return 0;
//                    }
//                }
//
//            });

            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                f = new GalleryModel();
                f.setName("Saved Status: " + (i + 1));
                f.setFilename(file.getName());
                f.setUri(Uri.fromFile(file));
                f.setPath(files[i].getAbsolutePath());
                f.setSelected(false);
                filesList.add(f);
            // Collections.reverse(filesList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filesList;
    }

    private void initComponents() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRecyclerView);
        chk_select_all = (CheckBox) findViewById(R.id.chk_select_all);
        btn_delete_all = (Button) findViewById(R.id.btn_delete_all);
        recyclerLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerLayout.setRefreshing(true);
                setUpRecyclerView();
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerLayout.setRefreshing(false);
                        Toast.makeText(ImageGallery.this, "Refreshed!", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        // save RecyclerView state
        if (mBundleRecyclerViewState != null && recyclerView != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            if (recyclerView.getLayoutManager() != null) {
                recyclerView.getLayoutManager().onRestoreInstanceState(listState);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();

    }
}

