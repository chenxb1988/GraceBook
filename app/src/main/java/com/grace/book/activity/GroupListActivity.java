package com.grace.book.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.grace.book.R;
import com.grace.book.activity.base.BaseLoadingActivity;
import com.grace.book.adapter.GroupAdapter;
import com.grace.book.http.CallBack;
import com.grace.book.http.HttpData;
import com.grace.book.http.RequestManager;
import com.grace.book.http.request.GroupListRequest;
import com.grace.book.http.response.GroupListResponse;
import com.grace.book.utils.ImageLoaderUtils;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.grace.book.utils.ExtraUtils.CHURCH_ID;

/**
 * Created by chenxb
 * 17/2/16.
 */

public class GroupListActivity extends BaseLoadingActivity implements OnRefreshListener, OnLoadMoreListener {
    @Bind(R.id.swipe_target)
    ListView mListView;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;
    private GroupAdapter adapter;
    private List<GroupListResponse.GroupInfo> groupInfos = new ArrayList<>();

    private int pageSize = 10;
    private int pageIndex = 1;

    private String mChurchId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLoadingContentView(R.layout.fragment_list);
        ImageLoaderUtils.setIconDrawable(mIcon, MaterialDesignIconic.Icon.gmi_accounts_list);
        setTitle("小组");

        initView();
        loadData();
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
        adapter = new GroupAdapter(GroupListActivity.this, groupInfos);
        mListView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        mChurchId = getIntent().getStringExtra(CHURCH_ID);
        onRefresh();
    }

    public void onRefresh() {
        pageIndex = 1;
        getData(true);
    }

    private void getData(final boolean isRefresh) {
        GroupListRequest request = new GroupListRequest();
        request.setChurchId(mChurchId);
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        RequestManager.post(getName(), HttpData.GROUP_LIST, request, new CallBack<GroupListResponse>() {
            @Override
            public void onSuccess(GroupListResponse result) {
                if (isRefresh) {
                    groupInfos.clear();
                    pageIndex = 2;
                } else {
                    pageIndex++;
                }
                showContentView();
                groupInfos.addAll(result.getRecords());
                adapter.notifyDataSetChanged();
                if (mSwipeToLoadLayout != null) {
                    mSwipeToLoadLayout.setRefreshing(false);
                    mSwipeToLoadLayout.setLoadingMore(false);
                }
                if (groupInfos.size() > 0) {
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

    @Override
    public void onLoadMore() {
        getData(false);
    }
}
