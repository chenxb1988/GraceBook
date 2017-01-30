package com.grace.book.fragments;


import android.support.v4.app.Fragment;
import android.view.View;

import com.grace.book.R;
import com.grace.book.activitys.MainActivity;
import com.grace.book.base.BaseFragment;
import com.grace.book.theme.ColorBackButton;
import com.grace.book.utils.ThemeUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeMainFragment extends BaseFragment {
    @Bind(R.id.btn_read_book)
    ColorBackButton mBtnReadBook;
    @Bind(R.id.btn_read_bible)
    ColorBackButton mBtnReadBible;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    public void initFragment() {
        ThemeUtils.addThemeToView(mBtnReadBook);
        ThemeUtils.addThemeToView(mBtnReadBible);
    }

    @OnClick({R.id.ll_mall, R.id.ll_record, R.id.ll_contact, R.id.ll_more})
    public void onClick(View view) {
        MainActivity activity = (MainActivity) getActivity();
        switch (view.getId()) {
            case R.id.ll_mall:
                activity.switchFragment(1);
                break;
            case R.id.ll_record:
                activity.switchFragment(2);
                break;
            case R.id.ll_contact:
                activity.switchFragment(3);
                break;
            case R.id.ll_more:
                activity.switchFragment(4);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        EventBus.getDefault().unregister(this);

    }
}
