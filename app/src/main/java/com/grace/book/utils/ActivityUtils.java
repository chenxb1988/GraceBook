package com.grace.book.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.grace.book.activitys.WebActivity;
import com.grace.book.beans.GanHuo;
import com.library.viewspread.helper.BaseViewHelper;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import me.xiaopan.android.content.res.DimenUtils;

import static com.grace.book.utils.ThemeUtils.getThemePrimaryColor;

/**
 * Created by chenxb on 16/6/8.
 */

public class ActivityUtils {
    public static final String GANHUO_EXTRA = "ganhuo";

    public static void launchGanhuoDetail(Context context, GanHuo ganhuo) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(GANHUO_EXTRA, ganhuo);
        context.startActivity(intent);
    }

    public static void launchActivity(Activity activityA, Class<?> activityB, View view){
        Intent intent = new Intent(activityA, activityB);
        new BaseViewHelper
                .Builder(activityA,view)
                .startActivity(intent);
    }

    public static void launchActivity(Activity activityA, Class<?> activityB, View view, Bundle bundle){
        Intent intent = new Intent(activityA, activityB);
        intent.putExtras(bundle);
        new BaseViewHelper
                .Builder(activityA,view)
                .startActivity(intent);
    }

    public static void setIconByType(String type, ImageView icon) {
        IIcon iIcon = null;
        switch (type) {
            case "Android":
                iIcon = MaterialDesignIconic.Icon.gmi_android;
                break;
            case "iOS":
                iIcon = MaterialDesignIconic.Icon.gmi_apple;
                break;
            case "休息视频":
                iIcon = MaterialDesignIconic.Icon.gmi_collection_video;
                break;
            case "前端":
                iIcon = MaterialDesignIconic.Icon.gmi_language_javascript;
                break;
            case "拓展资源":
                iIcon = FontAwesome.Icon.faw_location_arrow;
                break;
            case "App":
                iIcon = MaterialDesignIconic.Icon.gmi_apps;
                break;
            case "瞎推荐":
                iIcon = MaterialDesignIconic.Icon.gmi_more;
                break;
        }
        if (iIcon != null) {
            icon.setImageDrawable(new IconicsDrawable(icon.getContext()).color(Color.WHITE).icon(iIcon).sizeDp(20));
        }
    }

    public static void setTitleByTheme(TextView view, String text1, String text2) {
        int color = getThemePrimaryColor(view.getContext());
        SpannableString ss = new SpannableString(text1 + text2);
        ss.setSpan(new ForegroundColorSpan(color), 0, text1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(ss);
    }

    public static void setMenuTextByType(String type, TextView view) {
        switch (type) {
            case "Android":
                setIconDrawable(view, MaterialDesignIconic.Icon.gmi_android);
                break;
            case "iOS":
                setIconDrawable(view, MaterialDesignIconic.Icon.gmi_apple);
                break;
            case "休息视频":
                setIconDrawable(view, MaterialDesignIconic.Icon.gmi_collection_video);
                break;
            case "前端":
                setIconDrawable(view, MaterialDesignIconic.Icon.gmi_language_javascript);
                break;
            case "拓展资源":
                setIconDrawable(view, FontAwesome.Icon.faw_location_arrow);
                break;
            case "App":
                setIconDrawable(view, MaterialDesignIconic.Icon.gmi_apps);
                break;
            case "瞎推荐":
                setIconDrawable(view, MaterialDesignIconic.Icon.gmi_more);
                break;

        }
    }

    private static void setIconDrawable(TextView view, IIcon icon) {
        view.setCompoundDrawablesWithIntrinsicBounds(new IconicsDrawable(view.getContext())
                        .icon(icon)
                        .color(getThemePrimaryColor(view.getContext()))
                        .sizeDp(14),
                null, null, null);
        view.setCompoundDrawablePadding(DimenUtils.dp2px(view.getContext(), 5));
    }
}
