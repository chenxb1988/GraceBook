package com.grace.book.widget.theme;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.grace.book.utils.ThemeUtils;

/**
 * Created by DuoNuo on 2017/3/4.
 */
public class ColorTintImageView extends ImageView implements ColorUiInterface {
    public ColorTintImageView(Context context) {
        super(context);
    }

    public ColorTintImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorTintImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {
        Log.d("COLOR", "id = " + getId());
        int color = ThemeUtils.getThemePrimaryColor(getContext());
        setColorFilter(color);
    }
}
