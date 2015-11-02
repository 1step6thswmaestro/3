package com.soma.second.matnam.ui.adapters;

/**
 * Created by Dongjun on 15. 11. 2..
 */
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.soma.second.matnam.R;
import com.soma.second.matnam.ui.models.Friend;

/**
 *
 * @author manish.s
 *
 */
public class FriendGridAdapter extends ArrayAdapter<Friend> {
    Context context;
    int layoutResourceId;
    ArrayList<Friend> data = new ArrayList<Friend>();

    public FriendGridAdapter(Context context, int layoutResourceId, ArrayList<Friend> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.friend_item_name);
            holder.imageItem = (ImageView) row.findViewById(R.id.friend_item_img);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        Friend item = data.get(position);
        holder.txtTitle.setText(item.getName());
//        holder.imageItem.setImageBitmap(item.getUrl());
        return row;

    }

    static class RecordHolder {
        TextView txtTitle;
        ImageView imageItem;

    }
}