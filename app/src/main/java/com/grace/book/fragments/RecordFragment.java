package com.grace.book.fragments;


import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.grace.book.R;
import com.grace.book.adapter.AndroidAdapter;
import com.grace.book.base.BaseFragment;
import com.grace.book.beans.GanHuo;
import com.grace.book.http.CallBack;
import com.grace.book.http.RequestManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {

    @Bind(R.id.swipe_target)
    ListView mListView;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;
    private AndroidAdapter adapter;
    private List<GanHuo> ganHuos = new ArrayList<>();

    private int pageSize = 30;
    private int page = 1;

    public static RecordFragment newInstance() {
        return new RecordFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_record;
    }

    @Override
    public void initFragment() {
        initView();
        onRefresh();
    }

    private void getData(final boolean isRefresh) {
        RequestManager.get(getName(), "http://gank.io/api/data/前端/"
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
                        ganHuos.addAll(result);
                        adapter.notifyDataSetChanged();
                        if (mSwipeToLoadLayout != null) {
                            mSwipeToLoadLayout.setRefreshing(false);
                            mSwipeToLoadLayout.setLoadingMore(false);
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        super.onFailure(message);
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
}