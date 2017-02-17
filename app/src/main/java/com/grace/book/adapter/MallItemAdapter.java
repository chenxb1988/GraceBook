package com.grace.book.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grace.book.R;
import com.grace.book.http.response.BookSummaryList;

import java.util.List;

/**
 * Created by gjz on 16/01/2017.
 */

public class MallItemAdapter extends RecyclerView.Adapter<MallItemAdapter.ItemViewHolder>{
    private List<BookSummaryList.BookSummary> mItems;
    private GridLayoutManager mLayoutManager;

    public MallItemAdapter(List<BookSummaryList.BookSummary> items, GridLayoutManager layoutManager) {
        mItems = items;
        mLayoutManager = layoutManager;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        int spanCount = mLayoutManager.getSpanCount();

        if (spanCount == 1) {
            holder.layoutBig.setVisibility(View.VISIBLE);
            holder.layoutSmall.setVisibility(View.GONE);
        } else {
            holder.layoutSmall.setVisibility(View.VISIBLE);
            holder.layoutBig.setVisibility(View.GONE);
        }

        BookSummaryList.BookSummary item = mItems.get(position);
        holder.titleSmall.setText(item.getBookName());
        holder.titleBig.setText(item.getBookName());
        holder.ivSmall.setImageResource(R.drawable.logo);
        holder.ivBig.setImageResource(R.drawable.logo);
        holder.info.setText(item.getComment());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        View layoutSmall;
        ImageView ivSmall;
        TextView titleSmall;
        View layoutBig;
        ImageView ivBig;
        TextView titleBig;
        TextView info;

        ItemViewHolder(View itemView) {
            super(itemView);
            layoutSmall = itemView.findViewById(R.id.layout_small);
            ivSmall = (ImageView) itemView.findViewById(R.id.image_small);
            titleSmall = (TextView) itemView.findViewById(R.id.title_small);
            layoutBig = itemView.findViewById(R.id.layout_big);
            ivBig = (ImageView) itemView.findViewById(R.id.image_big);
            titleBig = (TextView) itemView.findViewById(R.id.title_big);
            info = (TextView) itemView.findViewById(R.id.tv_info);
        }
    }
}
