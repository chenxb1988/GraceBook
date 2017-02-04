package com.grace.book.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;

import com.grace.book.R;
import com.grace.book.widget.RoundDrawable;

/**
 * Created by dongjunkun on 2016/2/6.
 */
public class ThemeUtils {
    public static int getThemeColor(Context context, int attrRes) {
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{attrRes});
        int color = typedArray.getColor(0, 0xffffff);
        typedArray.recycle();
        return color;
    }

    public static int getThemePrimaryColor(Context context){
        return getThemeColor(context, R.attr.colorPrimary);
    }

    public static void addDrawableStateResource(View view, int idNormal, int idPressed) {
        StateListDrawable sd = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : view.getResources().getDrawable(idNormal);
        Drawable pressed = idPressed == -1 ? null : view.getResources().getDrawable(idPressed);
        sd.addState(new int[]{android.R.attr.state_pressed}, pressed);
        sd.addState(new int[]{}, normal);
        view.setBackgroundDrawable(sd);
    }

    public static void addDrawableStateColor(View view, int colorNormal, int colorPressed) {
        StateListDrawable sd = new StateListDrawable();
        RoundDrawable normal = new RoundDrawable(colorNormal);
        RoundDrawable pressed = new RoundDrawable(colorPressed);
        sd.addState(new int[]{android.R.attr.state_pressed}, pressed);
        sd.addState(new int[]{}, normal);
        view.setBackgroundDrawable(sd);
    }

    public static void addThemeToView(View view) {
        int colorPrimary = getThemePrimaryColor(view.getContext());
        int colorPrimaryDark = getThemeColor(view.getContext(), R.attr.colorPrimaryDark);
        addDrawableStateColor(view, colorPrimary, colorPrimaryDark);
    }

}
