package com.example.ubuntu.mymaxbox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ubuntu.mymaxbox.R;

import java.util.List;

public class ClothesListAdapter extends ArrayAdapter<List<String>> {

    private List<List<String>> mListData;
    private Context mContext;
    private int mResourceId;

    public ClothesListAdapter(Context context, int resource,List<List<String>> objects) {
        super(context, resource, objects);
        mContext = context;
        mListData = objects;
        mResourceId = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if( convertView==null ) {
            view = LayoutInflater.from(mContext).inflate(mResourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.us = view.findViewById(R.id.clothes_item_us);
            viewHolder.uk = view.findViewById(R.id.clothes_item_uk);
            viewHolder.france = view.findViewById(R.id.clothes_item_france);
            viewHolder.italy = view.findViewById(R.id.clothes_item_italy);
            viewHolder.korea = view.findViewById(R.id.clothes_item_korea);
            viewHolder.japan = view.findViewById(R.id.clothes_item_japan);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.us.setText(String.valueOf(mListData.get(position).get(0)));
        viewHolder.uk.setText(String.valueOf(mListData.get(position).get(1)));
        viewHolder.france.setText(String.valueOf(mListData.get(position).get(2)));
        viewHolder.italy.setText(String.valueOf(mListData.get(position).get(3)));
        viewHolder.korea.setText(String.valueOf(mListData.get(position).get(4)));
        viewHolder.japan.setText(String.valueOf(mListData.get(position).get(5)));
        return view;
    }

    class ViewHolder {
        TextView us;
        TextView uk;
        TextView france;
        TextView italy;
        TextView korea;
        TextView japan;
    }
}

