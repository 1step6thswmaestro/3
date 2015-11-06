package com.soma.second.matnam.ui.adapters;

/**
 * Created by Dongjun on 15. 11. 2..
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.soma.second.matnam.R;
import com.soma.second.matnam.ui.models.InstagramFollwer;

import java.util.ArrayList;

/**
 *
 * @author manish.s
 *
 */
public class InstagramFollowerListAdapter extends ArrayAdapter<InstagramFollwer> {
    Context context;
    int layoutResourceId;
    ArrayList<InstagramFollwer> data = new ArrayList<InstagramFollwer>();

    public InstagramFollowerListAdapter(Context context, int layoutResourceId, ArrayList<InstagramFollwer> data) {
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
            holder.txtTitle = (TextView) row.findViewById(R.id.insta_item_name);
            holder.imageItem = (ImageView) row.findViewById(R.id.insta_item_img);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        InstagramFollwer item = data.get(position);
        holder.txtTitle.setText(item.getUserName() + " (" + item.getFullName() + ")");
        holder.imageItem.setImageBitmap(item.getProfileImg());
        return row;

    }

    static class RecordHolder {
        TextView txtTitle;
        ImageView imageItem;

    }
}