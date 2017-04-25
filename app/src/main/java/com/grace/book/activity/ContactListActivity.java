package com.grace.book.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.grace.book.R;
import com.grace.book.activity.base.BaseLoadingActivity;
import com.grace.book.adapter.ContactAdapter;
import com.grace.book.http.CallBack;
import com.grace.book.http.HttpData;
import com.grace.book.http.RequestManager;
import com.grace.book.http.request.UserListRequest;
import com.grace.book.http.response.UserInfo;
import com.grace.book.http.response.UserListResponse;
import com.grace.book.utils.ImageLoaderUtils;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.grace.book.utils.ExtraUtils.CHURCH_ID;
import static com.grace.book.utils.ExtraUtils.GROUP_ID;

/**
 * Created by chenxb
 * 17/2/16.
 */

public class ContactListActivity extends BaseLoadingActivity implements OnRefreshListener, OnLoadMoreListener {
    @Bind(R.id.swipe_target)
    ListView mListView;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;
    private ContactAdapter adapter;
    private List<UserInfo> userInfos = new ArrayList<>();

    private int pageSize = 10;
    private int pageIndex = 1;

    private String mChurchId;
    private String mGroupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLoadingContentView(R.layout.fragment_list);
        ImageLoaderUtils.setIconDrawable(mIcon, MaterialDesignIconic.Icon.gmi_accounts_list);
        setTitle("联系人");

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
        adapter = new ContactAdapter(ContactListActivity.this, userInfos);
        mListView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        mChurchId = getIntent().getStringExtra(CHURCH_ID);
        mGroupId = getIntent().getStringExtra(GROUP_ID);
        onRefresh();
    }

    public void onRefresh() {
        pageIndex = 1;
        getData(true);
    }

    private void getData(final boolean isRefresh) {
        UserListRequest request = new UserListRequest();
        request.setChurchId(mChurchId);
        request.setGroupId(mGroupId);
        request.setRealName("*");
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        RequestManager.post(getName(), HttpData.USER_LIST, request, new CallBack<UserListResponse>() {
            @Override
            public void onSuccess(UserListResponse result) {
                if (isRefresh) {
                    userInfos.clear();
                    pageIndex = 2;
                } else {
                    pageIndex++;
                }
                showContentView();
                userInfos.addAll(result.getRecords());
                adapter.notifyDataSetChanged();
                if (mSwipeToLoadLayout != null) {
                    mSwipeToLoadLayout.setRefreshing(false);
                    mSwipeToLoadLayout.setLoadingMore(false);
                }
                if (userInfos.size() > 0) {
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
