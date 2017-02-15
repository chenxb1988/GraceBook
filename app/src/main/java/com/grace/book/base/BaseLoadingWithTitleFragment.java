package com.grace.book.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.grace.book.R;
import com.grace.book.activitys.MainActivity;


/**
 * 基类-加载内容Fragment
 * 带标题栏
 */
public abstract class BaseLoadingWithTitleFragment extends BaseLoadingFragment {
    protected View mStatusBar;
    protected ImageView mIcon;
    protected TextView mTitle;

    protected int getLayoutResource() {
        return R.layout.fragment_loading_base;
    }

    protected void setLoadingContentView(int layout) {
        mStatusBar = rootView.findViewById(R.id.status_bar);
        mIcon = (ImageView) rootView.findViewById(R.id.icon);
        mTitle = (TextView) rootView.findViewById(R.id.title);

        mIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).openResideLayout();
                }
            }
        });
        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).openResideLayout();
                }
            }
        });

        super.setLoadingContentView(layout);
    }
}
