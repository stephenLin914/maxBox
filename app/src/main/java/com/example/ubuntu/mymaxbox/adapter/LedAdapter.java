package com.example.ubuntu.mymaxbox.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.example.ubuntu.mymaxbox.R;
import com.example.ubuntu.mymaxbox.entity.Led;
import com.example.ubuntu.mymaxbox.listener.MyLedClickListener;
import com.example.ubuntu.mymaxbox.view.MyLedView;

import java.util.ArrayList;

public class LedAdapter extends RecyclerView.Adapter<LedAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Led> mLedList;
    private MyLedView imgContent;

    public LedAdapter(ArrayList<Led> mLedList, MyLedView imgContent) {
        this.mLedList = mLedList;
        this.imgContent = imgContent;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        FrameLayout ledItem;
        ImageView iteImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ledItem = (FrameLayout) itemView;
            iteImg = ledItem.findViewById(R.id.led_img);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.led_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.ledItem.setOnClickListener(new MyLedClickListener(imgContent));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Led led = mLedList.get(i);
        viewHolder.iteImg.setTag(led.getName());
        viewHolder.iteImg.setImageResource(led.getLedImg());
    }

    @Override
    public int getItemCount() {
        return mLedList.size();
    }

}
