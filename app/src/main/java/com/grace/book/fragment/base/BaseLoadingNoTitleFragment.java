package com.grace.book.fragment.base;

import com.grace.book.R;


/**
 * 基类-加载内容Fragment
 * 不带标题栏
 */
public abstract class BaseLoadingNoTitleFragment extends BaseLoadingFragment {

    protected int getLayoutResource() {
        return R.layout.fragment_loading_base_notitle;
    }

}
