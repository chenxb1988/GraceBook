package com.grace.book.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.grace.book.R;
import com.grace.book.base.BaseLoadingActivity;
import com.grace.book.http.CallBack;
import com.grace.book.http.HttpData;
import com.grace.book.http.RequestManager;
import com.grace.book.http.request.BookInfoRequest;
import com.grace.book.http.response.BookInfoResponse;
import com.grace.book.utils.ExtraUtils;
import com.grace.book.utils.ImageLoaderUtils;
import com.grace.book.utils.SharedUtils;
import com.grace.book.utils.ThemeUtils;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.Bind;

/**
 * Created by chenxb
 * 17/2/16.
 */

public class BookInfoActivity extends BaseLoadingActivity {
    @Bind(R.id.iv_cover)
    ImageView ivCover;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLoadingContentView(R.layout.activity_book_info);
        ImageLoaderUtils.setIconDrawable(mIcon, MaterialDesignIconic.Icon.gmi_arrow_back);
        setTitle("书籍信息");
        showLoadingView();

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(ThemeUtils.getThemePrimaryColor(this));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ImageLoaderUtils.getDrawable(BookInfoActivity.this, MaterialDesignIconic.Icon.gmi_arrow_back));

        collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);

        loadData();
    }

    @Override
    protected void loadData() {
        BookInfoRequest request = new BookInfoRequest();
        request.setBookId(getIntent().getStringExtra(ExtraUtils.BOOK_ID));
        request.setAuthToken(SharedUtils.getUserToken());

        RequestManager.post(getName(), HttpData.BOOK_INFO, request, new CallBack<BookInfoResponse>() {
            @Override
            public void onSuccess(BookInfoResponse result) {
                showContentView();
                mToolBarLayout.setVisibility(View.GONE);

                collapsingToolbar.setTitle(result.getBookName());
                ImageLoaderUtils.setImageUrl(ivCover, result.getPic(), R.drawable.default_book_info);
            }

            @Override
            public void onFailure(String message) {
                showFailMsg(message);
                showErrorView();
            }
        });
    }
}
