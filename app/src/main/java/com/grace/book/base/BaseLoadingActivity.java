package com.grace.book.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grace.book.R;

import butterknife.Bind;


/**
 * 基类-加载内容页面
 */
public abstract class BaseLoadingActivity extends BaseActivity {
    @Bind(R.id.status_bar)
    View mStatusBar;
    @Bind(R.id.icon)
    ImageView mIcon;
    @Bind(R.id.title)
    TextView mTitle;

    ViewGroup mContentView;
    View mProgressBar;
    View mErrorView;
    View mEmptyView;
    private View[] mViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayout());

        mContentView = (ViewGroup) findViewById(R.id.content);
        mProgressBar = findViewById(R.id.common_progress_view);
        mErrorView = findViewById(R.id.common_error_view);
        mEmptyView = findViewById(R.id.common_empty_view);
        mViews = new View[]{mContentView, mProgressBar, mErrorView, mEmptyView};

        if (mErrorView != null) {
            mErrorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadData();
                }
            });
        }
        if (mEmptyView != null) {
            mEmptyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadData();
                }
            });
        }
    }

    protected int getContentLayout() {
        return R.layout.activity_loading_base;
    }

    protected abstract void loadData();

    protected void setLoadingContentView(int layout) {
        LayoutInflater.from(this).inflate(layout, mContentView);
    }

    public void showContentView() {
        showView(mContentView);
    }

    public void showEmptyView() {
        showView(mEmptyView);
    }

    public void showErrorView() {
        showView(mErrorView);
    }

    public void showLoadingView() {
        showView(mProgressBar);
    }

    private void showView(View view) {
        if (view == null) {
            return;
        }
        view.setVisibility(View.VISIBLE);
        for (View v : mViews) {
            if (view != v) {
                v.setVisibility(View.GONE);
            }
        }
    }

}
