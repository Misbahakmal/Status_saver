package com.whats.app.statussaver.statusdownloader.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.whats.app.statussaver.statusdownloader.fragments.PhotoStatusesFragment;
import com.whats.app.statussaver.statusdownloader.fragments.VideoStatusesFragment;

/**
 * Created by theapache64 on 17/7/17.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private final PhotoStatusesFragment photoStatusesFragment;
    private final VideoStatusesFragment videoStatusesFragment;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        photoStatusesFragment = new PhotoStatusesFragment();
        videoStatusesFragment = new VideoStatusesFragment();
    }

    @Override
    public Fragment getItem(int position) {
        return position == 0 ? photoStatusesFragment : videoStatusesFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? "PHOTO" : "VIDEO";
    }

    @Override
    public int getCount() {
        return 2;
    }
}
