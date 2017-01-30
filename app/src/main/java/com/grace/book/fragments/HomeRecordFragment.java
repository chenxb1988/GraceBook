package com.grace.book.fragments;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.grace.book.R;
import com.grace.book.adapter.TabFragmentPageAdapter;
import com.grace.book.base.BaseFragment;
import com.grace.book.widget.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

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
    private List<BaseFragment> mFragments;

    private String mTitles[] = {"tab1", "tab2", "tab3", "tab4"};
    private int mPosition;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home_record;
    }

    @Override
    public void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(new RecordFragment());
        mFragments.add(new RecordFragment());
        mFragments.add(new RecordFragment());
        mFragments.add(new RecordFragment());

        mPagerAdapter = new TabFragmentPageAdapter(getChildFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(mPagerAdapter);
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

    public boolean isOnFirstTab(){
        return mPosition == 0;
    }
}