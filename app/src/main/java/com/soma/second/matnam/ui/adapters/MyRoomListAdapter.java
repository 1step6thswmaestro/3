package com.soma.second.matnam.ui.adapters;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.soma.second.matnam.R;
import com.example.kimyoungjoon.myapplication.backend.matnamApi.model.LikeRoomRecord;

import java.util.ArrayList;
import android.content.Context;

/**
 * Created by youngjoosuh on 2015. 11. 10..
 */
public class MyRoomListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<LikeRoomRecord> data;//= new ArrayList<LikeRoomRecord>();

    public MyRoomListAdapter(Context context, ArrayList<LikeRoomRecord> data) {
        if(data!=null) Log.v("myroomdata", "null");
        else Log.v("myroomdata", data.toString());
        this.context = context;
        this.data = data;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
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

        String str = data.get(position).getTitle();
        holder.tv.setText(str);

        return convertView;
    }
}
