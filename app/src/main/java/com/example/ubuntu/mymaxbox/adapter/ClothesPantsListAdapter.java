package com.example.ubuntu.mymaxbox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ubuntu.mymaxbox.R;

import java.util.List;

public class ClothesPantsListAdapter extends ArrayAdapter<List<String>> {

    private List<List<String>> mListData;
    private Context mContext;
    private int mResourceId;

    public ClothesPantsListAdapter(Context context, int resource,List<List<String>> objects) {
        super(context, resource, objects);
        mContext = context;
        mListData = objects;
        mResourceId = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ClothesPantsListAdapter.ViewHolder viewHolder;
        if( convertView==null ) {
            view = LayoutInflater.from(mContext).inflate(mResourceId, parent, false);
            viewHolder = new ClothesPantsListAdapter.ViewHolder();
            viewHolder.inch = view.findViewById(R.id.pants_inch);
            viewHolder.cm = view.findViewById(R.id.pants_cm);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ClothesPantsListAdapter.ViewHolder) view.getTag();
        }
        viewHolder.inch.setText(String.valueOf(mListData.get(position).get(0)));
        viewHolder.cm.setText(String.valueOf(mListData.get(position).get(1)));
        return view;
    }

    class ViewHolder {
        TextView inch;
        TextView cm;
    }
}