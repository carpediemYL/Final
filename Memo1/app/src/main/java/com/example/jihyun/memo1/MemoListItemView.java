package com.example.jihyun.memo1;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MemoListItemView extends LinearLayout {

    private TextView itemDate;
    private TextView itemText;
    private Context mContext;

    public MemoListItemView(Context context) {
        super(context);
        mContext = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.memo_listitem, this, true);

        itemDate = (TextView) findViewById(R.id.itemDate);
        itemText = (TextView) findViewById(R.id.itemText);

    }

    public void setContents(int index, String data) {
        if (index == 0) {
            itemDate.setText(data);
        } else if (index == 1) {
            itemText.setText(data);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
