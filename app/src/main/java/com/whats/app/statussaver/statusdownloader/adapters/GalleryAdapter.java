package com.whats.app.statussaver.statusdownloader.adapters;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.StrictMode;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.whats.app.statussaver.statusdownloader.R;
import com.whats.app.statussaver.statusdownloader.activities.FullScreenView;
import com.whats.app.statussaver.statusdownloader.activities.ImageGallery;
import com.whats.app.statussaver.statusdownloader.models.GalleryModel;

import java.io.File;
import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private Context context;
  public static ArrayList<Integer> seriesOfNumbers = new ArrayList<Integer>();
    private ArrayList<GalleryModel> filesList;
    public boolean showCheckbox = false;
    private int selectedItem = -1;
    public static int posi;

    public GalleryAdapter(Context context, ArrayList<GalleryModel> filesList) {
        this.context = context;
        this.filesList = filesList;
    }

    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_card_row, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GalleryAdapter.ViewHolder holder, final int position) {
        final GalleryModel files = filesList.get(position);
        holder.checkBox.setVisibility(files.showCheckbox ? View.VISIBLE : View.GONE);
        final Uri uri = Uri.parse(files.getUri().toString());
        final File file = new File(uri.getPath());
        holder.userName.setText(files.getName());
        holder.checkBox.setChecked(filesList.get(position).isSelected());

        holder.checkBox.setTag(filesList.get(position));

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                GalleryModel model = (GalleryModel) cb.getTag();
                seriesOfNumbers.add(position);
                model.setSelected(cb.isChecked());
                filesList.get(position).setSelected(cb.isChecked());


            }
        });

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (files.getUri().toString().endsWith(".mp4")) {
            holder.playIcon.setVisibility(View.VISIBLE);
        } else {
            holder.playIcon.setVisibility(View.INVISIBLE);
        }
        Glide.with(context)
                .load(files.getUri())
                .into(holder.savedImage);


        holder.savedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.getVisibility() == View.VISIBLE) {
                    if (holder.checkBox.isSelected() == true) {
                        holder.checkBox.setChecked(true);
                    }

                    holder.checkBox.setChecked(false);
                }
//                if( holder.checkBox.isSelected()==true)
//                {
//                    holder.checkBox.setChecked(false);
//                }
                else {
                    if (files.getUri().toString().endsWith(".mp4")) {
                        Uri VideoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
                        Intent intent = new Intent();
                        intent.setAction(android.content.Intent.ACTION_VIEW);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        intent.setDataAndType(VideoURI, "video/*");
                        try {
                            context.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(context, "No application found to open this file.", Toast.LENGTH_LONG).show();
                        }
                    } else if (files.getUri().toString().endsWith(".jpg")) {
                        Uri VideoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
                      String uri=VideoURI.toString();
//                       Intent intent = new Intent();
//                        intent.setAction(android.content.Intent.ACTION_VIEW);
//                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);


                     //   context.startActivity(i);
                        try {
                           posi=position;
                            Intent i = new Intent(context, FullScreenView.class);
                            i.putExtra("position", posi);
//                            i.putExtra("POS",posi);
//                            Bundle bundle=new Bundle();
//                            bundle.putInt("position", position);
//                            i.putExtra("uri", uri);
//                            i.putExtras(bundle);
                            context.startActivity(i);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(context, "No application found to open this file.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
//        if (selectedItem != -1 && selectedItem == position) {
//            holder.checkBox.setVisibility(View.VISIBLE);
//            //holder.isSelected.setVisibility(View.VISIBLE);
//            //  assignButton.setVisibility(View.VISIBLE);
//        } else {
//            holder.checkBox.setVisibility(View.GONE);
//            //  assignButton.setVisibility(View.GONE);
//        }
        holder.savedImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //  if (selectedItem != -1) {
                //     selectedItem = position;
                ImageGallery.mToolbar.setVisibility(View.VISIBLE);
                holder.checkBox.setChecked(true);
                showAllBoxes();

                //  } else {
                //    selectedItem = -1;
                // hideAllBoxes();
                //  }
                return true;// returning true instead of false, works for me
            }
        });
        holder.repostID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri mainUri = Uri.fromFile(file);
                if (files.getUri().toString().endsWith(".jpg")) {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("image/*");
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, mainUri);
                    sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    sharingIntent.setPackage("com.whatsapp");
                    try {
                        context.startActivity(Intent.createChooser(sharingIntent, "Share Image using"));
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(context, "No application found to open this file.", Toast.LENGTH_LONG).show();
                    }
                } else if (files.getUri().toString().endsWith(".mp4")) {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("video/*");
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, mainUri);
                    sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    sharingIntent.setPackage("com.whatsapp");
                    try {
                        context.startActivity(Intent.createChooser(sharingIntent, "Share Video using"));
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(context, "No application found to open this file.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        holder.deleteID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String path = filesList.get(position).getPath();
                final File file = new File(path);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to delete this?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            if (file.exists()) {
                                boolean del = file.delete();
                                filesList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, filesList.size());
                                notifyDataSetChanged();
                                Toast.makeText(context, "File  Deleted Sucessfully", Toast.LENGTH_SHORT).show();
                                if (del) {
                                    MediaScannerConnection.scanFile(
                                            context,
                                            new String[]{path, path},
                                            new String[]{"image/jpg", "video/mp4"},
                                            new MediaScannerConnection.MediaScannerConnectionClient() {
                                                public void onMediaScannerConnected() {
                                                }

                                                public void onScanCompleted(String path, Uri uri) {
                                                    Log.d("Video path: ", path);
                                                }
                                            });
                                }
                            }
                            dialog.dismiss();
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
//                FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(context)
//                        .setTextTitle("DELETE?")
//                        .setBody("Are you sure you want to delete this file?")
//
//                        .setNegativeButtonText("Cancel")
//                        .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
//                            @Override
//                            public void OnClick(View view, Dialog dialog) {
//                                dialog.dismiss();
//                            }
//                        })
//                        .setPositiveButtonText("Delete")
//                        .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
//                            @Override
//                            public void OnClick(View view, Dialog dialog) {
//                                try {
//                                    if (file.exists()) {
//                                        boolean del = file.delete();
//                                        filesList.remove(position);
//                                        notifyItemRemoved(position);
//                                        notifyItemRangeChanged(position, filesList.size());
//                                        notifyDataSetChanged();
//                                        Toast.makeText(context, "File  Deleted Sucessfully", Toast.LENGTH_SHORT).show();
//                                        if (del) {
//                                            MediaScannerConnection.scanFile(
//                                                    context,
//                                                    new String[]{path, path},
//                                                    new String[]{"image/jpg", "video/mp4"},
//                                                    new MediaScannerConnection.MediaScannerConnectionClient() {
//                                                        public void onMediaScannerConnected() {
//                                                        }
//
//                                                        public void onScanCompleted(String path, Uri uri) {
//                                                            Log.d("Video path: ", path);
//                                                        }
//                                                    });
//                                        }
//                                    }
//                                    dialog.dismiss();
//                                } catch (Exception e) {
//                                    // TODO let the user know the file couldn't be deleted
//                                    e.printStackTrace();
//                                }
//                            }
//                        })
//                        .build();
//                alert.show();
            }
        });

        holder.shareID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri mainUri = Uri.fromFile(file);
                if (files.getUri().toString().endsWith(".jpg")) {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("image/*");
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, mainUri);
                    sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    try {
                        context.startActivity(Intent.createChooser(sharingIntent, "Share Image using"));
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(context, "No application found to open this file.", Toast.LENGTH_LONG).show();
                    }
                } else if (files.getUri().toString().endsWith(".mp4")) {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("video/*");
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, mainUri);
                    sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    try {
                        context.startActivity(Intent.createChooser(sharingIntent, "Share Video using"));
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(context, "No application found to open this file.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void showAllBoxes() {
        for (GalleryModel item : filesList) {
            item.showCheckbox = true;

        }

        notifyDataSetChanged();
    }

    private void hideAllBoxes() {
        for (GalleryModel item : filesList) {
            item.showCheckbox = false;
        }
        notifyDataSetChanged();
    }

    public void deleteContact(GalleryModel object) {
        filesList.remove(object);
        //To update the ListView in Android
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return filesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        ImageView savedImage;
        ImageView playIcon;
        ImageView repostID, shareID, deleteID;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.profileUserName);
            savedImage = (ImageView) itemView.findViewById(R.id.mainImageView);
            playIcon = (ImageView) itemView.findViewById(R.id.playButtonImage);
            repostID = (ImageView) itemView.findViewById(R.id.repostID);
            shareID = (ImageView) itemView.findViewById(R.id.shareID);
            deleteID = (ImageView) itemView.findViewById(R.id.deleteID);
            checkBox = itemView.findViewById(R.id.chk_selected);
        }
    }
}
