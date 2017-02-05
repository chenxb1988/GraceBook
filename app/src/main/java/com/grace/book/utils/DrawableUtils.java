package com.grace.book.utils;

import android.graphics.Color;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;

/**
 * Created by chenxb
 * 17/2/5.
 */

public class DrawableUtils {

    public static void setIconDrawable(TextView view, IIcon icon) {
        setIconDrawable(view, icon, Color.WHITE, 16);
    }

    public static void setIconDrawable(TextView view, IIcon icon, int color, int dp) {
        view.setCompoundDrawablesWithIntrinsicBounds(new IconicsDrawable(view.getContext())
                        .icon(icon)
                        .color(color)
                        .sizeDp(dp),
                null, null, null);
        view.setCompoundDrawablePadding(DimenUtils.dp2px(view.getContext(), 10));
    }
}
