package com.whats.app.statussaver.statusdownloader.adapters;

import android.content.Context;
import android.os.Environment;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.whats.app.statussaver.statusdownloader.R;
import com.whats.app.statussaver.statusdownloader.models.Status;

import java.io.File;
import java.util.List;

/**
 * Created by theapache64 on 16/7/17.
 */
public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {


    private final List<Status> statusList;
    private final LayoutInflater inflater;
    Status status;
    private final Callback callback;
    Context context;

    public StatusAdapter(final Context context, List<Status> statusList, Callback callback) {
        this.statusList = statusList;
        this.inflater = LayoutInflater.from(context);
        this.callback = callback;
        this.context=context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View rowLayout = inflater.inflate(R.layout.status_row, parent, false);
        return new ViewHolder(rowLayout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
         status = statusList.get(position);
        holder.ivThumbnail.setImageBitmap(status.getThumbnail());
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView ivThumbnail;

        ViewHolder(View itemView) {
            super(itemView);
            this.ivThumbnail = (ImageView) itemView.findViewById(R.id.ivThumbnail);
            itemView.setOnClickListener(this);
            itemView.findViewById(R.id.ibSaveToGallery).setOnClickListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    callback.onShare(getLayoutPosition());
                    return true;
                }
            });
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.ibSaveToGallery) {
                callback.onSaveToGalleryClicked(getLayoutPosition());
//                checkFolder();
//                //Status st=new Status();
//                final String path =((Status) statusList.get(position)).getSubtitle();
//                // ((Status) statusList.get(position)).getPath();
//                final File file = new File(path);
//                String destPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/StatusImages/";
//                File destFile = new File(destPath);
//                try {
//                    FileUtils.copyFileToDirectory(file, destFile);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                MediaScannerConnection.scanFile(
//                        context,
//                        new String[]{destPath + status.getTitle()},
//                        new String[]{"*/*"},
//                        new MediaScannerConnection.MediaScannerConnectionClient() {
//                            public void onMediaScannerConnected() {
//                            }
//
//                            public void onScanCompleted(String path, Uri uri) {
//                                Log.d("path: ", path);
//                            }
//                        });
//                Toast.makeText(context, "Saved to: " + destPath + status.getTitle(), Toast.LENGTH_LONG).show();
            } else {
                callback.onItemClicked(getLayoutPosition());
            }
        }
    }

    public interface Callback {
        void onItemClicked(int position);

        void onShare(int position);

        void onSaveToGalleryClicked(int position);
    }

    public void checkFolder() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "StatusImages" ;
        File dir = new File(path);

        boolean isDirectoryCreated = dir.exists();
        if (!isDirectoryCreated) {
            isDirectoryCreated = dir.mkdir();
        }
        if (isDirectoryCreated) {
            Log.d("Folder", "Already Created");
        }
    }
}
