package com.example.ubuntu.mymaxbox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ubuntu.mymaxbox.R;
import com.example.ubuntu.mymaxbox.entity.UnitListItem;


import java.util.List;

public class UnitListAdapter extends ArrayAdapter<UnitListItem> {

    private List<UnitListItem> mListData;
    private Context mContext;
    private int mResourceId;

    public UnitListAdapter(Context context, int resource, List<UnitListItem> objects) {
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
            viewHolder.valueText = view.findViewById(R.id.list_item_value);
            viewHolder.nameText = view.findViewById(R.id.list_item_name);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.valueText.setText(String.valueOf(mListData.get(position).getValue()));
        viewHolder.nameText.setText(mListData.get(position).getName());
        return view;
    }

}

class ViewHolder {
    TextView valueText;
    TextView nameText;
}