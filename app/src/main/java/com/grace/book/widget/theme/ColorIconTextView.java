package com.grace.book.widget.theme;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.grace.book.utils.DrawableUtils;
import com.grace.book.utils.ThemeUtils;
import com.mikepenz.iconics.typeface.IIcon;


/**
 * Created by chengli on 15/6/8.
 */
public class ColorIconTextView extends TextView implements ColorUiInterface {

    private IIcon iicon;
    private int height;

    public ColorIconTextView(Context context) {
        super(context);
    }

    public ColorIconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorIconTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setIIcon(IIcon iicon, int height) {
        this.iicon = iicon;
        this.height = height;
        int color = ThemeUtils.getThemePrimaryColor(getContext());
        DrawableUtils.setIconDrawable(this, iicon, color, height);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {
        Log.d("COLOR", "id = " + getId());
        if (iicon != null) {
            int color = ThemeUtils.getThemePrimaryColor(getContext());
            DrawableUtils.setIconDrawable(this, iicon, color, height);
        }
    }
}
