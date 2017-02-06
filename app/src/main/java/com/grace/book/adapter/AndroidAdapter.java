package com.grace.book.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.grace.book.R;
import com.grace.book.entity.GanHuo;
import com.grace.book.utils.ActivityUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dongjunkun on 2016/2/15.
 */
public class AndroidAdapter extends BaseAdapter {
    private Context context;
    private List<GanHuo> ganHuos;

    public AndroidAdapter(Context context, List<GanHuo> ganHuos) {
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
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_android, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        final GanHuo ganHuo = ganHuos.get(position);
        ActivityUtils.setTitleByTheme(viewHolder.mText, ganHuo.getDesc(), "[" + ganHuo.getWho() + "]");
        viewHolder.mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.launchGanhuoDetail(context, ganHuo);

            }
        });
//        viewHolder.mText.setText(Html.fromHtml("<a href=\""
//                + ganHuos.get(position).getUrl() + "\">"
//                + ganHuos.get(position).getDesc() + "</a>"
//                + "[" + ganHuos.get(position).getWho() + "]"));
//        viewHolder.mText.setMovementMethod(LinkMovementMethod.getInstance());
//        viewHolder.mText.setText(ganHuos.get(position).getDesc()+"["+ganHuos.get(position).getWho()+"]");
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.text)
        TextView mText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
