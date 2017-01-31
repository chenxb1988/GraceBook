package com.grace.book.fragments;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.grace.book.R;
import com.grace.book.base.BaseFragment;
import com.grace.book.widget.PagerSlidingTabStrip;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeRecordFragment extends BaseFragment {
    @Bind(R.id.tabs)
    PagerSlidingTabStrip mTabs;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    private TabFragmentPageAdapter mPagerAdapter;

    private String mTitles[] = {"tab1", "tab2", "tab3", "tab4"};
    private int mPosition;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home_record;
    }

    @Override
    public void initFragment() {
        mPagerAdapter = new TabFragmentPageAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(mTitles.length);
        mTabs.setViewPager(mViewPager);
        mTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public boolean isOnFirstTab() {
        return mPosition == 0;
    }

    class TabFragmentPageAdapter extends FragmentPagerAdapter {
        private Fragment[] mFragments;

        public TabFragmentPageAdapter(FragmentManager fm) {
            super(fm);
            mFragments = new BaseFragment[mTitles.length];
        }

        @Override
        public Fragment getItem(int position) {
            if (mFragments[position] == null) {
                mFragments[position] = RecordFragment.newInstance();
            }
            return mFragments[position];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position % mTitles.length];
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }
    }

}