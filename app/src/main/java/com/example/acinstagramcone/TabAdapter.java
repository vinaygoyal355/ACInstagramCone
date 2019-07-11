package com.example.acinstagramcone;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {
    public TabAdapter(FragmentManager fm) {
       super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                ProfileTab A=new ProfileTab();
                return A;

            case 1:
                UserTab a=new UserTab();
                return a;

            case 2:
                return new SharePicTab();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){

            case 0: return "Profile";

            case 1: return "Users";

            case 2: return "Share Picture";

            default: return null;
        }

    }
}
