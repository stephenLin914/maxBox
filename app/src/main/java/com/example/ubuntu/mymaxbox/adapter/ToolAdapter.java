package com.example.ubuntu.mymaxbox.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ubuntu.mymaxbox.R;
import com.example.ubuntu.mymaxbox.entity.Tool;
import com.example.ubuntu.mymaxbox.listener.MyToolOnClickListener;

import java.util.ArrayList;

public class ToolAdapter extends RecyclerView.Adapter<ToolAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Tool> mToolList;

    public ToolAdapter(ArrayList<Tool> mToolList) {
        this.mToolList = mToolList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView toolItem;
        ImageView toolImage;
        TextView toolName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            toolItem = (CardView) itemView;
            toolImage = itemView.findViewById(R.id.tool_img);
            toolName = itemView.findViewById(R.id.tool_name);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if( mContext == null ) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.tool_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.toolItem.setOnClickListener(new MyToolOnClickListener());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Tool tool = mToolList.get(i);
        viewHolder.toolImage.setImageResource(tool.getImg());
        viewHolder.toolName.setText(tool.getName());
    }

    @Override
    public int getItemCount() {
        return mToolList.size();
    }


}
