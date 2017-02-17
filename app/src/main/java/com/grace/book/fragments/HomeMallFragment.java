package com.grace.book.fragments;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CompoundButton;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.grace.book.R;
import com.grace.book.adapter.MallItemAdapter;
import com.grace.book.base.BaseLoadingWithTitleFragment;
import com.grace.book.http.response.BookSummaryList;
import com.grace.book.event.SkinChangeEvent;
import com.grace.book.http.CallBack;
import com.grace.book.http.HttpData;
import com.grace.book.http.RequestManager;
import com.grace.book.http.request.BookListRequest;
import com.grace.book.utils.SystemUtils;
import com.grace.book.widget.SwitchButton;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeMallFragment extends BaseLoadingWithTitleFragment implements OnRefreshListener, OnLoadMoreListener {
    @Bind(R.id.swipe_target)
    RecyclerView recyclerView;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;
    @Bind(R.id.switch_list)
    SwitchButton switchButton;

    private MallItemAdapter itemAdapter;
    private GridLayoutManager gridLayoutManager;
    private List<BookSummaryList.BookSummary> items = new ArrayList<>();

    private int page = 1;
    private int pageSize = 10;

    @Override
    public void initFragment() {
        setLoadingContentView(R.layout.fragment_home_mall);

        SystemUtils.setStatusBar(getActivity(), mStatusBar);
        mIcon.setImageDrawable(new IconicsDrawable(getActivity()).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_city).sizeDp(20));
        mTitle.setText(R.string.mall);

        EventBus.getDefault().register(this);
        initView();
        onRefresh();
    }

    @Subscribe
    public void onEvent(SkinChangeEvent event) {
//        adapter.notifyDataSetChanged();

    }

    private void getData(final boolean isRefresh) {
        showLoadingView();
        BookListRequest request = new BookListRequest();
        request.setChurchId("2673e224363301f5");
        request.setPageIndex(page + "");
        request.setPageSize(pageSize + "");
        request.setTypeId("*");

        RequestManager.post(getName(), HttpData.BOOK_LIST,
                request, isRefresh,
                new CallBack<BookSummaryList>() {
                    @Override
                    public void onSuccess(BookSummaryList result) {
                        if (result == null || result.getRecords() == null) {
                            return;
                        }
                        if (isRefresh) {
                            items.clear();
                        }

                        items.addAll(result.getRecords());
                        itemAdapter.notifyDataSetChanged();

                        if (mSwipeToLoadLayout != null) {
                            mSwipeToLoadLayout.setRefreshing(false);
                            mSwipeToLoadLayout.setLoadingMore(false);
                            if (result.getRecords().size() < pageSize) {
                                // 如果最后一页,取消上拉加载更多
                                mSwipeToLoadLayout.setLoadMoreEnabled(false);
                            }
                        }
                        if (items.size() > 0) {
                            showContentView();
                        } else {
                            showEmptyView();
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        showFailMsg(message);
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

        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        itemAdapter = new MallItemAdapter(items, gridLayoutManager);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                checkLoadMore(newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchLayout();
            }
        });
    }

    private void checkLoadMore() {
        checkLoadMore(RecyclerView.SCROLL_STATE_IDLE);
    }

    private void checkLoadMore(int state) {
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        // 当不滚动时
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition()
                    * (gridLayoutManager.getSpanCount() + 1) - 1;
            int totalItemCount = manager.getItemCount();

            if (lastVisibleItem >= (totalItemCount - 1)) {
                mSwipeToLoadLayout.setLoadingMore(true);
            }
        }
    }

    private void switchLayout() {
        switch (gridLayoutManager.getSpanCount()) {
            case 1:
                gridLayoutManager.setSpanCount(3);
                break;
            case 3:
                gridLayoutManager.setSpanCount(1);
                break;
            default:
                gridLayoutManager.setSpanCount(1);
                break;
        }
        itemAdapter.notifyItemRangeChanged(0, itemAdapter.getItemCount());
        mSwipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLoadMore();
            }
        }, 500);
    }

    @Override
    public void onRefresh() {
        page = 1;
        getData(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onLoadMore() {
        page++;
        getData(false);
    }

    @Override
    protected void loadData() {
        getData(true);
    }
}
