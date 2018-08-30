package com.shushang.aishangjia.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseViewPagerAdapter extends FragmentPagerAdapter {

    protected final List<Fragment> mFragments = new ArrayList<>();
    protected final List<String> mFragmentTitles = new ArrayList<>();

    public BaseViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public abstract void addFragment(Fragment fragment, String title);

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }


    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }
}
