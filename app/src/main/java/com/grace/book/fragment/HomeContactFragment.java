package com.grace.book.fragment;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.grace.book.R;
import com.grace.book.adapter.FellowAdapter;
import com.grace.book.fragment.base.BaseLoadingWithTitleFragment;
import com.grace.book.http.CallBack;
import com.grace.book.http.HttpData;
import com.grace.book.http.RequestManager;
import com.grace.book.http.request.FellowListRequest;
import com.grace.book.http.response.FellowListResponse;
import com.grace.book.utils.SharedUtils;
import com.grace.book.utils.SystemUtils;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeContactFragment extends BaseLoadingWithTitleFragment implements OnRefreshListener, OnLoadMoreListener {

    @Bind(R.id.swipe_target)
    ListView mListView;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;
    private FellowAdapter adapter;
    private List<FellowListResponse.FellowInfo> fellowInfos = new ArrayList<>();

    private int pageSize = 10;
    private int page = 1;

    @Override
    public void initFragment() {
        setLoadingContentView(R.layout.fragment_list);

        SystemUtils.setStatusBar(getActivity(), mStatusBar);
        mIcon.setImageDrawable(new IconicsDrawable(getActivity()).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_accounts_list).sizeDp(20));
        mTitle.setText("团契");

        initView();
        onRefresh();
    }

    private void getData(final boolean isRefresh) {
        FellowListRequest request = new FellowListRequest();
        request.setChurchId("*");
        RequestManager.post(getName(), HttpData.FELLOW_LIST, request, new CallBack<FellowListResponse>() {
            @Override
            public void onSuccess(FellowListResponse result) {
                SharedUtils.saveFellowList(result);
                if (isRefresh) {
                    fellowInfos.clear();
                    page = 2;
                } else {
                    page++;
                }
                showContentView();
                fellowInfos.addAll(result.getRecords());
                adapter.notifyDataSetChanged();
                if (mSwipeToLoadLayout != null) {
                    mSwipeToLoadLayout.setRefreshing(false);
                    mSwipeToLoadLayout.setLoadingMore(false);
                }
                if (fellowInfos.size() > 0) {
                    showContentView();
                } else {
                    showEmptyView();
                }
            }

            @Override
            public void onFailure(String message) {
                showFailMsg(message);
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
        adapter = new FellowAdapter(getActivity(), fellowInfos);
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