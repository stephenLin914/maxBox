package com.example.ubuntu.mymaxbox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ubuntu.mymaxbox.R;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    private List<String> spinnerData;
    private Context mContext;

    public SpinnerAdapter(List<String> spinnerData, Context context) {
        this.spinnerData = spinnerData;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return spinnerData.size();
    }

    @Override
    public Object getItem(int position) {
        return spinnerData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if( convertView==null ) {
            view = LayoutInflater.from(mContext).inflate(R.layout.spinner_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = view.findViewById(R.id.spinner_text);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView.setText(spinnerData.get(position));
        return view;
    }

    class ViewHolder {
        TextView textView;
    }
}
