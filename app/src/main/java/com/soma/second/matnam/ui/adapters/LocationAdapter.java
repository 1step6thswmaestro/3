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
import com.soma.second.matnam.ui.models.Friend;
import com.soma.second.matnam.ui.models.Location;

import java.util.ArrayList;

/**
 *
 * @author manish.s
 *
 */
public class LocationAdapter extends ArrayAdapter<Location> {
    Context context;
    int layoutResourceId;
    ArrayList<Location> data = new ArrayList<Location>();

    public LocationAdapter(Context context, int layoutResourceId, ArrayList<Location> data) {
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
            holder.txtTitle = (TextView) row.findViewById(R.id.location_item_name);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        Location item = data.get(position);
        holder.txtTitle.setText(item.getName());
        return row;

    }

    static class RecordHolder {
        TextView txtTitle;
    }
}