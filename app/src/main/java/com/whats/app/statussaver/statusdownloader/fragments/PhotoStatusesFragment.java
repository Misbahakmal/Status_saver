package com.whats.app.statussaver.statusdownloader.fragments;

import com.whats.app.statussaver.statusdownloader.models.Status;
import com.whats.app.statussaver.statusdownloader.fragments.BaseStatusesFragment;

import java.util.List;

/**
 * Created by theapache64 on 16/7/17.
 */

public class PhotoStatusesFragment extends BaseStatusesFragment {
    @Override
    public List<Status> getStatuses() {
        return getStatusManager().getPhotoStatuses();
    }
}
