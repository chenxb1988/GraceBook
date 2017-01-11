package com.grace.book.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.grace.book.R;
import com.grace.book.beans.GanHuo;
import com.grace.book.utils.ActivityUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dongjunkun on 2016/2/15.
 */
public class AllAdapter extends BaseAdapter {
    private Context context;
    private List<GanHuo> ganHuos;

    public AllAdapter(Context context, List<GanHuo> ganHuos) {
        this.context = context;
        this.ganHuos = ganHuos;
    }

    @Override
    public int getCount() {
        return ganHuos.size();
    }

    @Override
    public Object getItem(int position) {
        return ganHuos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final GanHuo ganHuo = ganHuos.get(position);
        View view1;
        View view2;
        if (ganHuo.getType().equals("福利")) {
            ImageViewHolder viewHolder1 = null;
            if (convertView != null && convertView.getTag() instanceof ImageViewHolder) {
                viewHolder1 = (ImageViewHolder) convertView.getTag();
            } else {
                view1 = LayoutInflater.from(context).inflate(R.layout.item_fuli, null);
                viewHolder1 = new ImageViewHolder(view1);
                view1.setTag(viewHolder1);
                convertView = view1;
            }
            Glide.with(context).load(ganHuo.getUrl())
                    .placeholder(R.mipmap.avatar)
                    .into(viewHolder1.mImage);
        } else {
            ViewHolder viewHolder2 = null;
            if (convertView != null && convertView.getTag() instanceof ViewHolder) {
                viewHolder2 = (ViewHolder) convertView.getTag();
            } else {
                view2 = LayoutInflater.from(context).inflate(R.layout.item_android, null);
                viewHolder2 = new ViewHolder(view2);
                view2.setTag(viewHolder2);
                convertView = view2;
            }
            ActivityUtils.setTitleByTheme(viewHolder2.mText, ganHuo.getDesc(), "[" + ganHuo.getWho() + "]");
//            viewHolder2.mText.setText(ganHuo.getDesc() + "[" + ganHuo.getWho() + "]");
            viewHolder2.mText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityUtils.launchGanhuoDetail(context, ganHuo);
                }
            });
            ActivityUtils.setMenuTextByType(ganHuo.getType(), viewHolder2.mText);
//            viewHolder2.mText.setText(Html.fromHtml("<a href=\""
//                    + ganHuo.getUrl() + "\">"
//                    + ganHuo.getDesc() + "</a>"
//                    + "[" + ganHuo.getWho() + "]"));
//            viewHolder2.mText.setMovementMethod(LinkMovementMethod.getInstance());
        }
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.text)
        TextView mText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ImageViewHolder {
        @Bind(R.id.image)
        ImageView mImage;

        ImageViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
