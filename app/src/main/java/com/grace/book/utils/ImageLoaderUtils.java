package com.grace.book.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.grace.book.MyApplication;
import com.grace.book.R;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.xiaopan.java.lang.StringUtils;

/**
 * Created by chenxb
 * 17/2/5.
 */

public class ImageLoaderUtils {

    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int id) {
        return Build.VERSION.SDK_INT >= 21 ? context.getResources().getDrawable(id) : context.getResources().getDrawable(id);
    }

    public static void setIconDrawable(TextView view, IIcon icon) {
        setIconDrawable(view, icon, Color.WHITE, 16);
    }

    public static void setIconDrawable(ImageView view, IIcon icon) {
        setIconDrawable(view, icon, Color.WHITE, 20);
    }

    public static void setIconDrawable(TextView view, IIcon icon, int color, int dp) {
        view.setCompoundDrawablesWithIntrinsicBounds(new IconicsDrawable(view.getContext())
                        .icon(icon)
                        .color(color)
                        .sizeDp(dp),
                null, null, null);
        view.setCompoundDrawablePadding(DimenUtils.dp2px(view.getContext(), 10));
    }

    public static void setIconDrawable(ImageView view, IIcon icon, int color, int dp) {
        view.setImageDrawable(new IconicsDrawable(view.getContext())
                .color(color)
                .icon(icon)
                .sizeDp(dp));
    }

    public static Drawable getDrawable(Context context, IIcon icon) {
        return new IconicsDrawable(context)
                .icon(icon)
                .color(Color.WHITE)
                .sizeDp(20);
    }

    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void setImageUrl(ImageView imageView, String url, int resId) {
        Glide.with(imageView.getContext()).load(url)
                .placeholder(resId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void setImageUrl(ImageView imageView, String url, int width, int height) {
        Glide.with(imageView.getContext()).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(width, height)
                .into(imageView);
    }

    public static void setCircleImageUrl(ImageView imageView, String url) {
        DrawableRequestBuilder builder = Glide.with(imageView.getContext()).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        builder.bitmapTransform(new CropCircleTransformation(imageView.getContext()));
        builder.into(imageView);
    }

    public static void setUserAvatarUrl(ImageView imageView, String url) {
        if (StringUtils.isEmpty(url)) {
            setCircleImageSource(imageView, R.drawable.default_avatar);
            return;
        }
        Glide.with(imageView.getContext()).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.default_avatar)
                .bitmapTransform(new CropCircleTransformation(imageView.getContext()))
                .into(imageView);
    }

    public static void setLocalImage(ImageView imageView, String path) {
        imageView.setImageURI(Uri.parse("file://" + path));
    }

    public static void setLocalCircleImage(ImageView imageView, String path) {
        Glide.with(imageView.getContext()).load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new CropCircleTransformation(imageView.getContext()))
                .into(imageView);
    }

    public static void setImageSource(ImageView imageView, int resId) {
        Glide.with(imageView.getContext()).load("android.resource://" + MyApplication.getInstance().getPackageName() + "/drawable/" + resId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

    }

    public static void setCircleImageSource(ImageView imageView, int resId) {
        Glide.with(imageView.getContext()).load("android.resource://com.grace.book/drawable/" + resId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new CropCircleTransformation(imageView.getContext()))
                .into(imageView);
    }
}
