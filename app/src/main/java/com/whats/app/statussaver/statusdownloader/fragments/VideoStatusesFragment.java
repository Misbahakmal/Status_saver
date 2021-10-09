package com.whats.app.statussaver.statusdownloader.fragments;


import com.whats.app.statussaver.statusdownloader.fragments.BaseStatusesFragment;
import com.whats.app.statussaver.statusdownloader.models.Status;

import java.util.List;

/**
 * Created by theapache64 on 16/7/17.
 */

public class VideoStatusesFragment extends BaseStatusesFragment {

    @Override
    public List<Status> getStatuses() {
        return getStatusManager().getVideoStatuses();
    }

}
