package com.grace.book.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grace.book.R;

import butterknife.ButterKnife;


/**
 * 基类-加载内容页面
 */
public abstract class BaseLoadingFragment extends BaseFragment {
    protected ViewGroup mContentView;
    protected View mProgressView;
    protected View mErrorView;
    protected View mEmptyView;
    private View[] mViews;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResource(), container, false);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        mContentView = (ViewGroup) rootView.findViewById(R.id.content);
        mProgressView = rootView.findViewById(R.id.common_progress_view);
        mErrorView = rootView.findViewById(R.id.common_error_view);
        mEmptyView = rootView.findViewById(R.id.common_empty_view);
        mViews = new View[]{mContentView, mProgressView, mErrorView, mEmptyView};

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
        return rootView;
    }

    protected abstract void loadData();

    protected void setLoadingContentView(int layout) {
        View view = LayoutInflater.from(getActivity()).inflate(layout, mContentView);
        ButterKnife.bind(this, view);
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
        showView(mProgressView);
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
