package com.grace.book.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.grace.book.base.BaseFragment;

import java.util.List;

/**
 * Created by chenxb on 17/1/30.
 */
public class TabFragmentPageAdapter extends FragmentPagerAdapter {
    String[] mTitles;
    List<BaseFragment> mFragments;

    public TabFragmentPageAdapter(FragmentManager fm, List<BaseFragment> mFragments, String[] mTitles) {
        super(fm);
        this.mFragments = mFragments;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position % mTitles.length];
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
