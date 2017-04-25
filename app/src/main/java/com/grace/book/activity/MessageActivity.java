package com.grace.book.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.grace.book.R;
import com.grace.book.activity.base.BaseLoadingActivity;
import com.grace.book.adapter.MessageAdapter;
import com.grace.book.http.CallBack;
import com.grace.book.http.HttpData;
import com.grace.book.http.RequestManager;
import com.grace.book.http.request.MessageRequest;
import com.grace.book.http.response.MessageResponse;
import com.grace.book.utils.ImageLoaderUtils;
import com.grace.book.utils.SharedUtils;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by chenxb on 2017/4/24.
 */

public class MessageActivity extends BaseLoadingActivity implements OnRefreshListener, OnLoadMoreListener {
    @Bind(R.id.swipe_target)
    ListView mListView;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;
    private MessageAdapter adapter;
    private List<MessageResponse.Message> messages = new ArrayList<>();

    private int pageSize = 10;
    private int pageIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLoadingContentView(R.layout.activity_message);
        ImageLoaderUtils.setIconDrawable(mIcon, MaterialDesignIconic.Icon.gmi_email);
        setTitle("消息");
        initViews();
        loadData();
    }

    @Override
    protected void loadData() {
        onRefresh();
    }

    private void initViews() {
        mSwipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeToLoadLayout.setRefreshing(true);
            }
        });
        mSwipeToLoadLayout.setOnRefreshListener(this);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);

        adapter = new MessageAdapter(MessageActivity.this, messages);
        mListView.setAdapter(adapter);
    }

    private void getData(final boolean isRefresh) {
        MessageRequest request = new MessageRequest();
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        request.setUserId(SharedUtils.getUserId());

        RequestManager.post(getName(), HttpData.MESSAGE_LIST, request,
                new CallBack<MessageResponse>() {
                    @Override
                    public void onSuccess(MessageResponse result) {
                        if (isRefresh) {
                            messages.clear();
                            pageIndex = 1;
                        } else {
                            pageIndex++;
                        }
                        messages.addAll(result.getRecords());
                        adapter.notifyDataSetChanged();

                        if (mSwipeToLoadLayout != null) {
                            mSwipeToLoadLayout.setRefreshing(false);
                            mSwipeToLoadLayout.setLoadingMore(false);
                            if (result.getRecords().size() < pageSize) {
                                // 如果最后一页,取消上拉加载更多
                                mSwipeToLoadLayout.setLoadMoreEnabled(false);
                            }
                        }
                        if (messages.size() > 0) {
                            showContentView();
                        } else {
                            showEmptyView();
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        showFailMsg(MessageActivity.this, message);
                        showErrorView();
                        if (mSwipeToLoadLayout != null) {
                            mSwipeToLoadLayout.setRefreshing(false);
                            mSwipeToLoadLayout.setLoadingMore(false);
                        }
                    }
                });
    }

    @Override
    public void onLoadMore() {
        getData(false);
    }

    @Override
    public void onRefresh() {
        getData(true);
    }
}
