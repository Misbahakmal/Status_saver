package com.whats.app.statussaver.statusdownloader.fragments;


import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.whats.app.statussaver.statusdownloader.R;
import com.whats.app.statussaver.statusdownloader.adapters.StatusAdapter;
import com.whats.app.statussaver.statusdownloader.models.Status;
import com.whats.app.statussaver.statusdownloader.activities.Videosshow;
import com.whats.app.statussaver.statusdownloader.utils.DialogUtils;
import com.whats.app.statussaver.statusdownloader.utils.StatusManager;
import com.whats.app.statussaver.statusdownloader.utils.UriCompat;
import com.whats.app.statussaver.statusdownloader.utils.APIRequestBuilder;
import com.whats.app.statussaver.statusdownloader.utils.APIRequestGateway;
import com.whats.app.statussaver.statusdownloader.utils.OkHttpUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseStatusesFragment extends Fragment implements StatusAdapter.Callback, StatusManager.Callback {

    private static final String X = BaseStatusesFragment.class.getSimpleName();
    private static final String TYPE_VIDEO = "VIDEO";
    private static final String TYPE_PHOTO = "PHOTO";
    private static final String ACTION_TYPE_VIEW = "VIEW";
    private static final String ACTION_TYPE_DOWNLOAD = "DOWNLOAD";
    private StatusManager statusManager;
    private RecyclerView rvStatuses;
    private DialogUtils dialogUtils;
    private AlertDialog loadingDialog;
    public static File file;
    public BaseStatusesFragment() {
        // Required empty public constructor
    }

    protected StatusManager getStatusManager() {
        return statusManager;
    }

    public abstract List<Status> getStatuses();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootLayout = inflater.inflate(R.layout.fragment_statuses, container, false);
        rvStatuses = (RecyclerView) rootLayout.findViewById(R.id.rvStatuses);
        rvStatuses.setLayoutManager(new GridLayoutManager(getActivity(), 3));


        dialogUtils = new DialogUtils(getActivity());
        loadingDialog = dialogUtils.getLoadingDialog(R.string.Loading);
        loadingDialog.show();

        statusManager = new StatusManager(getActivity(), this);

        return rootLayout;
    }

    @Override
    public void onItemClicked(int position) {

        final Status status = getStatuses().get(position);

        final String type, iType;
        if (status.isVideo()) {
            type = TYPE_VIDEO;
            iType = "video/mp4";
            Intent i = new Intent(getActivity(), Videosshow.class);
          file =status.getFile();
            startActivity(i);
           // Toast.makeText(getActivity(), "video found"+status.getFile(), Toast.LENGTH_SHORT ).show();
        } else {
            type = TYPE_PHOTO;
            iType = "image/jpg";

            try {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(UriCompat.fromFile(getActivity(), status.getFile(), intent), iType);
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), R.string.No_viewer_found, Toast.LENGTH_SHORT).show();
            }
            addToDb(type, ACTION_TYPE_VIEW);
        }

    }

    private void addToDb(final String type, final String actionType) {

        //Updating analytics
        new APIRequestGateway(getContext(), new APIRequestGateway.APIRequestGatewayCallback() {

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
                Log.e(X, "ERRRRRRRROR: " + reason);
            }
        });
    }

    private static final String APP_DIR = Environment.getExternalStorageDirectory() + File.separator + "StatusImages";
   private static final String APP_DIR1 = Environment.getExternalStorageDirectory() + File.separator + "StatusVideos";
    @Override
    public void onSaveToGalleryClicked(int position) {
        if (getStatuses().get(position).getFile().toString().endsWith(".jpg")) {

            final File appFolder = new File(APP_DIR);
            if (!appFolder.exists()) {
                //noinspection ResultOfMethodCallIgnored
                appFolder.mkdirs();
            }

            final Status status = getStatuses().get(position);
            final File destFile = new File(appFolder + File.separator + status.getTitle());

            if (destFile.exists()) {
                //noinspection ResultOfMethodCallIgnored
                destFile.delete();
            }


            try {
                copyFile(status.getFile(), destFile);
              Toast.makeText(getActivity(), R.string.Saved_to_gallery, Toast.LENGTH_SHORT).show();

                Intent intent =
                        new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(UriCompat.fromFile(getActivity(), destFile, intent));
                getActivity().sendBroadcast(intent);


            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), R.string.Failed_to_save_to_gallery, Toast.LENGTH_SHORT).show();
            }

            addToDb(status.isVideo() ? TYPE_VIDEO : TYPE_PHOTO, ACTION_TYPE_DOWNLOAD);

        }else  if(getStatuses().get(position).getFile().toString().endsWith(".png"))
        {
            final File appFolder = new File(APP_DIR);
            if (!appFolder.exists()) {
                //noinspection ResultOfMethodCallIgnored
                appFolder.mkdirs();
            }

            final Status status = getStatuses().get(position);
            final File destFile = new File(appFolder + File.separator + status.getTitle());

            if (destFile.exists()) {
                //noinspection ResultOfMethodCallIgnored
                destFile.delete();
            }


            try {
                copyFile(status.getFile(), destFile);
                Toast.makeText(getActivity(), R.string.Saved_to_gallery, Toast.LENGTH_SHORT).show();

                Intent intent =
                        new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(UriCompat.fromFile(getActivity(), destFile, intent));
                getActivity().sendBroadcast(intent);


            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), R.string.Failed_to_save_to_gallery, Toast.LENGTH_SHORT).show();
            }

            addToDb(status.isVideo() ? TYPE_VIDEO : TYPE_PHOTO, ACTION_TYPE_DOWNLOAD);

        }
        else if(getStatuses().get(position).getFile().toString().endsWith(".mp4"))
        {
            final File appFolder = new File(APP_DIR1);
            if (!appFolder.exists()) {
                //noinspection ResultOfMethodCallIgnored
                appFolder.mkdirs();
            }

            final Status status = getStatuses().get(position);
            final File destFile = new File(appFolder + File.separator + status.getTitle());

            if (destFile.exists()) {
                //noinspection ResultOfMethodCallIgnored
                destFile.delete();
            }
            try {
                copyFile(status.getFile(), destFile);
                Toast.makeText(getActivity(), R.string.Saved_to_gallery, Toast.LENGTH_SHORT).show();

                Intent intent =
                        new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(UriCompat.fromFile(getActivity(), destFile, intent));
                getActivity().sendBroadcast(intent);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), R.string.Failed_to_save_to_gallery, Toast.LENGTH_SHORT).show();
            }
            addToDb(status.isVideo() ? TYPE_VIDEO : TYPE_PHOTO, ACTION_TYPE_DOWNLOAD);
        }}
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void copyFile(File sourceFile, File destFile) throws IOException {

        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

    @Override
    public void onLoaded() {
        loadingDialog.dismiss();
        rvStatuses.setAdapter(new StatusAdapter(getActivity(), getStatuses(), BaseStatusesFragment.this));
    }

    @Override
    public void onFailed(String reason) {
        loadingDialog.dismiss();
        dialogUtils.showErrorDialog(reason, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void onShare(int position) {

        Toast.makeText(getActivity(), "Choose app to share", Toast.LENGTH_SHORT).show();

        final Status status = getStatuses().get(position);

        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(status.isVideo() ? "video/mp4" : "image/jpg");
        shareIntent.putExtra(Intent.EXTRA_STREAM, UriCompat.fromFile(getActivity(), status.getFile(), shareIntent));
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Shared using WhatsApp Status Downloader - https://play.google.com/store/apps/details?id=com.statusdownloaderforwhatsapp.download.status");
        startActivity(Intent.createChooser(shareIntent, "Share using"));
    }
}
