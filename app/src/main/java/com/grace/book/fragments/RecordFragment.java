package com.grace.book.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.grace.book.R;
import com.grace.book.activitys.BookInfoActivity;
import com.grace.book.adapter.RecordReadAdapter;
import com.grace.book.adapter.RecordReadingAdapter;
import com.grace.book.adapter.RecordStarAdapter;
import com.grace.book.base.BaseLoadingNoTitleFragment;
import com.grace.book.event.AddBorrowEvent;
import com.grace.book.event.AddStarEvent;
import com.grace.book.event.CancelBorrowEvent;
import com.grace.book.event.CancelStarEvent;
import com.grace.book.http.CallBack;
import com.grace.book.http.HttpData;
import com.grace.book.http.RequestManager;
import com.grace.book.http.request.RecordRequest;
import com.grace.book.http.response.RecordResponse;
import com.grace.book.utils.ExtraUtils;
import com.grace.book.utils.SharedUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordFragment extends BaseLoadingNoTitleFragment implements OnRefreshListener, OnLoadMoreListener {
    @Bind(R.id.swipe_target)
    ListView mListView;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;
    private BaseAdapter adapter;
    private List<RecordResponse.RecordInfo> records = new ArrayList<>();

    private int pageSize = 10;
    private int pageIndex = 1;

    private int type;//0-在读;1-已读;2-收藏

    public static RecordFragment newInstance(int type) {
        RecordFragment fragment = new RecordFragment();
        fragment.type = type;
        return fragment;
    }

    @Override
    public void initFragment() {
        setLoadingContentView(R.layout.fragment_record);

        initView();
        onRefresh();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onAddBorrowEvent(AddBorrowEvent event) {
        if (type == 0) {
            //在读界面更新
            loadData();
        }
    }

    @Subscribe
    public void onCancelBorrowEvent(CancelBorrowEvent event) {
        if (type == 0) {
            for (int i = records.size() - 1; i >= 0; i--) {
                if (event.getBorrowId().equals(records.get(i).getBorrowId())) {
                    records.remove(i);
                    if (records.size() > 0) {
                        showContentView();
                        adapter.notifyDataSetChanged();
                    } else {
                        showEmptyView();
                    }
                    return;
                }
            }
        }
    }

    @Subscribe
    public void onAddStarEvent(AddStarEvent event) {
        if (type == 2) {
            //收藏界面增加该条
            loadData();
        } else if (type == 0) {
            //在读界面更新
            for (int i = records.size() - 1; i >= 0; i--) {
                if (event.getBookId().equals(records.get(i).getBookId())) {
                    records.get(i).setHasCollected();
                    adapter.notifyDataSetChanged();
                    return;
                }
            }
        }
    }

    @Subscribe
    public void onCancelStarEvent(CancelStarEvent event) {
        if (type == 2) {
            //收藏界面取消该条
            for (int i = records.size() - 1; i >= 0; i--) {
                if (event.getCollectId().equals(records.get(i).getCollectId())) {
                    records.remove(i);
                    if (records.size() > 0) {
                        showContentView();
                        adapter.notifyDataSetChanged();
                    } else {
                        showEmptyView();
                    }
                    return;
                }
            }
        } else if (type == 0) {
            //在读界面更新
            loadData();
        }
    }

    private void getData(final boolean isRefresh) {
        RecordRequest request = new RecordRequest();
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        request.setAuthToken(SharedUtils.getUserToken());
        request.setType(type + "");

        RequestManager.post(getName(), HttpData.RECORD_LIST, request,
                new CallBack<RecordResponse>() {
                    @Override
                    public void onSuccess(RecordResponse result) {
                        if (isRefresh) {
                            records.clear();
                            pageIndex = 1;
                        } else {
                            pageIndex++;
                        }
                        records.addAll(result.getRecords());
                        adapter.notifyDataSetChanged();

                        if (mSwipeToLoadLayout != null) {
                            mSwipeToLoadLayout.setRefreshing(false);
                            mSwipeToLoadLayout.setLoadingMore(false);
                            if (result.getRecords().size() < pageSize) {
                                // 如果最后一页,取消上拉加载更多
                                mSwipeToLoadLayout.setLoadMoreEnabled(false);
                            }
                        }
                        if (records.size() > 0) {
                            showContentView();
                        } else {
                            showEmptyView();
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        showFailMsg(getActivity(), message);
                        showErrorView();
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

        switch (type) {
            case 0:
                adapter = new RecordReadingAdapter(getActivity(), records);
                break;
            case 1:
                adapter = new RecordReadAdapter(getActivity(), records);
                break;
            case 2:
                adapter = new RecordStarAdapter(getActivity(), records);
                break;
            default:
                adapter = new RecordStarAdapter(getActivity(), records);
                break;
        }
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), BookInfoActivity.class);
                intent.putExtra(ExtraUtils.BOOK_ID, records.get(i).getBookId());
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        showLoadingView();
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