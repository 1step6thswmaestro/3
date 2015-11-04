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
import com.soma.second.matnam.ui.models.Food;
import com.soma.second.matnam.ui.models.Friend;

import java.util.ArrayList;

/**
 *
 * @author manish.s
 *
 */
public class FoodDetailGridAdapter extends ArrayAdapter<Food> {
    Context context;
    int layoutResourceId;
    ArrayList<Food> data = new ArrayList<Food>();

    public FoodDetailGridAdapter(Context context, int layoutResourceId, ArrayList<Food> data) {
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
            holder.imageItem = (ImageView) row.findViewById(R.id.food_item_img);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        Food item = data.get(position);
        holder.imageItem.setImageBitmap(item.getBitmap());
        return row;

    }

    static class RecordHolder {
        ImageView imageItem;

    }
}