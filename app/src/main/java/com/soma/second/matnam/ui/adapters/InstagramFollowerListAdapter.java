package com.soma.second.matnam.ui.adapters;

/**
 * Created by Dongjun on 15. 11. 2..
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.soma.second.matnam.R;
import com.soma.second.matnam.Utils.RecycleUtils;
import com.soma.second.matnam.ui.models.InstagramFollwer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author manish.s
 *
 */
public class InstagramFollowerListAdapter extends ArrayAdapter<InstagramFollwer> {

    private List<WeakReference<View>> mRecycleList = new ArrayList<WeakReference<View>>();

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

        final InstagramFollwer item = data.get(position);
        holder.txtTitle.setText(item.getUserName() + " (" + item.getFullName() + ")");
        Glide.with(context).load(item.getProfile_img_url()).into(holder.imageItem);

        return row;

    }

    static class RecordHolder {
        TextView txtTitle;
        ImageView imageItem;

    }

    public void recycle() {
        RecycleUtils.recursiveRecycle(mRecycleList);
    }

    //만들었던 뷰 목록 중 반을 지우는 메소드
    public void recycleHalf() {
        int halfSize = mRecycleList.size() / 2;
        List<WeakReference<View>> recycleHalfList = mRecycleList.subList(0,
                halfSize);
        RecycleUtils.recursiveRecycle(recycleHalfList);
        for (int i = 0; i < halfSize; i++)
            mRecycleList.remove(0);
    }
}