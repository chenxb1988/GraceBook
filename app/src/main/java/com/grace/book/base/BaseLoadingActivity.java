package com.grace.book.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grace.book.R;
import com.grace.book.utils.SystemUtils;

import butterknife.ButterKnife;


/**
 * 基类-加载内容页面
 */
public abstract class BaseLoadingActivity extends BaseActivity {
    protected View mToolBarLayout;
    protected View mStatusBar;
    protected ImageView mIcon;
    protected TextView mTitle, mRightText;

    ViewGroup mContentView;
    View mProgressBar;
    View mErrorView;
    View mEmptyView;
    private View[] mViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayout());

        mToolBarLayout = findViewById(R.id.tool_bar_layout);
        mStatusBar = findViewById(R.id.status_bar);
        mIcon = (ImageView) findViewById(R.id.icon);
        mTitle = (TextView) findViewById(R.id.title);
        mRightText = (TextView) findViewById(R.id.tv_right);
        SystemUtils.setStatusBar(this, mStatusBar);

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
        if (mIcon != null) {
            mIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BaseLoadingActivity.this.finish();
                }
            });
        }
        showContentView();
    }

    protected int getContentLayout() {
        return R.layout.activity_loading_base;
    }

    protected abstract void loadData();

    protected void setLoadingContentView(int layout) {
        LayoutInflater.from(this).inflate(layout, mContentView);
        ButterKnife.bind(this);
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

    public void setTitle(String title) {
        if (mTitle != null) {
            mTitle.setText(title);
        }
    }

    public void setTitle(int id) {
        if (mTitle != null) {
            mTitle.setText(id);
        }
    }

    public void setTitle(int id, int rid) {
        if (mTitle != null) {
            mTitle.setText(id);
        }
        if (mRightText != null) {
            mRightText.setText(rid);
            mRightText.setVisibility(View.VISIBLE);
            mRightText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickRightText();
                }
            });
        }
    }

    public void setTitle(String title, String right) {
        if (mTitle != null) {
            mTitle.setText(title);
        }
        if (mRightText != null) {
            mRightText.setText(right);
            mRightText.setVisibility(View.VISIBLE);
            mRightText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickRightText();
                }
            });
        }
    }

    public void onClickRightText() {

    }
}
