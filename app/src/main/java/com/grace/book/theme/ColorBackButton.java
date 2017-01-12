package com.grace.book.theme;

import android.content.Context;
import android.content.res.Resources;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.grace.book.R;
import com.grace.book.utils.ThemeUtils;


/**
 * Created by chengli on 15/6/8.
 */
public class ColorBackButton extends Button implements ColorUiInterface {

    private int attr_drawable = -1;

    public ColorBackButton(Context context) {
        super(context);
    }

    public ColorBackButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attr_drawable = ThemeUtils.getThemeColor(getContext(), R.attr.colorPrimary);
    }

    public ColorBackButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attr_drawable = ThemeUtils.getThemeColor(getContext(), R.attr.colorPrimary);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {
        Log.d("COLOR", "id = " + getId());
        attr_drawable = ThemeUtils.getThemeColor(getContext(), R.attr.colorPrimary);
        if (attr_drawable != -1) {
            setBackgroundColor(attr_drawable);
        }
    }
}
