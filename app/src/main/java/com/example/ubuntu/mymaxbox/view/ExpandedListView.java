package com.example.ubuntu.mymaxbox.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ExpandedListView extends ListView {
    public ExpandedListView(Context context) {
        super(context);
    }

    public ExpandedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandedListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ExpandedListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
    }
}
