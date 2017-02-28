package com.grace.book.activitys;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;

import com.grace.book.R;
import com.grace.book.base.BaseLoadingActivity;
import com.grace.book.utils.ImageLoaderUtils;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

/**
 * Created by chenxb
 * 17/2/16.
 */

public class BookInfoActivity extends BaseLoadingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLoadingContentView(R.layout.activity_book_info);
        ImageLoaderUtils.setIconDrawable(mIcon, MaterialDesignIconic.Icon.gmi_book);
        setTitle("书籍信息");

//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintColor(ThemeUtils.getThemePrimaryColor(this));

//        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("书名");
    }

    @Override
    protected void loadData() {

    }
}
