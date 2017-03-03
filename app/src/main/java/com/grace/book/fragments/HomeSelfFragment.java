package com.grace.book.fragments;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.grace.book.R;
import com.grace.book.adapter.AndroidAdapter;
import com.grace.book.base.BaseLoadingWithTitleFragment;
import com.grace.book.http.CallBack;
import com.grace.book.http.RequestManager;
import com.grace.book.http.response.GanHuo;
import com.grace.book.utils.SystemUtils;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeSelfFragment extends BaseLoadingWithTitleFragment implements OnRefreshListener, OnLoadMoreListener {

    @Bind(R.id.swipe_target)
    ListView mListView;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;
    private AndroidAdapter adapter;
    private List<GanHuo> ganHuos = new ArrayList<>();

    private int pageSize = 30;
    private int page = 1;

    @Override
    public void initFragment() {
        setLoadingContentView(R.layout.fragment_android);

        SystemUtils.setStatusBar(getActivity(), mStatusBar);
        mIcon.setImageDrawable(new IconicsDrawable(getActivity()).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_account).sizeDp(20));
        mTitle.setText(R.string.self);

        initView();
        onRefresh();
    }

    private void getData(final boolean isRefresh) {
        RequestManager.get(getName(), "http://gank.io/api/data/iOS/"
                        + String.valueOf(pageSize) + "/"
                        + String.valueOf(page), isRefresh,
                new CallBack<List<GanHuo>>() {
                    @Override
                    public void onSuccess(List<GanHuo> result) {
                        if (isRefresh) {
                            ganHuos.clear();
                            page = 2;
                        } else {
                            page++;
                        }
                        showContentView();
                        ganHuos.addAll(result);
                        adapter.notifyDataSetChanged();
                        if (mSwipeToLoadLayout != null) {
                            mSwipeToLoadLayout.setRefreshing(false);
                            mSwipeToLoadLayout.setLoadingMore(false);
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        showFailMsg(getActivity(), message);
                        if (mSwipeToLoadLayout != null) {
                            mSwipeToLoadLayout.setRefreshing(false);
                            mSwipeToLoadLayout.setLoadingMore(false);
                        }
                    }
                });
    }

    private void initView() {
        mSwipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeToLoadLayout.setRefreshing(true);
            }
        });
        mSwipeToLoadLayout.setOnRefreshListener(this);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);
        adapter = new AndroidAdapter(getActivity(), ganHuos);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        page = 1;
        getData(true);
    }

    @Override
    public void onLoadMore() {
        getData(false);
    }

    @Override
    protected void loadData() {
        onRefresh();
    }
}