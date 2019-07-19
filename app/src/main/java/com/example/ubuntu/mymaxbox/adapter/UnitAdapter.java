package com.example.ubuntu.mymaxbox.adapter;

import android.content.Context;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ubuntu.mymaxbox.R;
import com.example.ubuntu.mymaxbox.UnitActivity;
import com.example.ubuntu.mymaxbox.entity.UnitType;


public class UnitAdapter extends BaseAdapter {
    private UnitType[] mUnitDatas;
    private Context mContext;
    private int lastPostion = 0;
    private Handler mHandler;
    private static final int UPDATE_ACTIVITY = 1;
    private boolean isFirst = true;

    public UnitAdapter(UnitType[] mUnitDatas, Context context, Handler handler) {
        this.mUnitDatas = mUnitDatas;
        mContext = context;
        mHandler = handler;
    }

    @Override
    public int getCount() {
        return mUnitDatas.length;
    }

    @Override
    public Object getItem(int position) {
        return mUnitDatas[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final View view;
        ViewHolder viewHolder;
        if( convertView==null ) {
            view = LayoutInflater.from(mContext).inflate(R.layout.unit_item, parent, false);
            ImageView imageView = view.findViewById(R.id.unit_item_image);
            TextView textView = view.findViewById(R.id.unit_item_name);
            viewHolder = new ViewHolder(imageView, textView);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageResource(mUnitDatas[position].getTypeImage());
        viewHolder.textView.setText(mUnitDatas[position].getTypeName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.getChildAt(lastPostion).setBackgroundResource(R.drawable.unitconverter_gvew_item_background);
                v.setBackgroundResource(R.drawable.unitconverter_gvew_item_selected_background);
                lastPostion = position;

                Message message = new Message();
                message.arg1 = position;
//                message.what = UPDATE_ACTIVITY;
                mHandler.sendMessage(message);
            }
        });
        if( isFirst && position==0 ) {
            view.setBackgroundResource(R.drawable.unitconverter_gvew_item_selected_background);
        }
        if( position==1 ) {
            isFirst = false;
        }
        return view;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(ImageView imageView, TextView textView) {
            this.imageView = imageView;
            this.textView = textView;
        }
    }
}
