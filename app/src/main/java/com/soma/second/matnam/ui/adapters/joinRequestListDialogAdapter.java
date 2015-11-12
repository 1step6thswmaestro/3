package com.soma.second.matnam.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kimyoungjoon.myapplication.backend.matnamApi.model.LikeRoomRecord;
import com.soma.second.matnam.R;

import java.util.ArrayList;

/**
 * Created by youngjoosuh on 2015. 11. 13..
 */
public class joinRequestListDialogAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> data;

    public joinRequestListDialogAdapter(Context context, ArrayList<String> data) {
        if(data==null) Log.v("arraydata", "null");
        else Log.v("arraydata", data.toString());
        this.context = context;
        this.data = data;
    }


    @Override
    public int getCount() {
        if(data==null) {
            return 0;
        } else {
            return data.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if(data==null) {
            return null;
        } else {
            return data.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        public TextView tv;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.myroom_list_item, null);

            holder.tv = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String str;
        if(data ==null) {
            str = null;
        } else {
            str = data.get(position);
        }
        if(str==null || str.equals("")) {
            holder.tv.setText("NULL");
        } else {
            holder.tv.setText(str);
        }


        return convertView;
    }
}
