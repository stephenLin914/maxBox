package com.example.ubuntu.mymaxbox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ubuntu.mymaxbox.R;

import java.util.List;

public class UnderClothesListAdapter extends ArrayAdapter<List<String>> {

    private List<List<String>> mListData;
    private Context mContext;
    private int mResourceId;

    public UnderClothesListAdapter(Context context, int resource, List<List<String>> objects) {
        super(context, resource, objects);
        mContext = context;
        mListData = objects;
        mResourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        UnderClothesListAdapter.ViewHolder viewHolder;
        if( convertView==null ) {
            view = LayoutInflater.from(mContext).inflate(mResourceId, parent, false);
            viewHolder = new UnderClothesListAdapter.ViewHolder();
            viewHolder.us = view.findViewById(R.id.under_clothes_item_us);
            viewHolder.france = view.findViewById(R.id.under_clothes_item_france);
            viewHolder.eu = view.findViewById(R.id.under_clothes_item_eu);
            viewHolder.japan = view.findViewById(R.id.under_clothes_item_japan);
            viewHolder.top = view.findViewById(R.id.under_clothes_item_top);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (UnderClothesListAdapter.ViewHolder) view.getTag();
        }
        viewHolder.us.setText(String.valueOf(mListData.get(position).get(0)));
        viewHolder.france.setText(String.valueOf(mListData.get(position).get(1)));
        viewHolder.eu.setText(String.valueOf(mListData.get(position).get(2)));
        viewHolder.japan.setText(String.valueOf(mListData.get(position).get(3)));
        viewHolder.top.setText(String.valueOf(mListData.get(position).get(4)));
        return view;
    }

    class ViewHolder {
        TextView us;
        TextView france;
        TextView eu;
        TextView japan;
        TextView top;
    }
}
