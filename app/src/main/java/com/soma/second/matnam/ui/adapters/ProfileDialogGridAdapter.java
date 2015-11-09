package com.soma.second.matnam.ui.adapters;

import android.widget.BaseAdapter;
import java.util.ArrayList;
import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.soma.second.matnam.R;
import android.graphics.Bitmap;
import com.soma.second.matnam.ui.models.Photo;
/**
 * Created by youngjoosuh on 2015. 11. 7..
 */
public class ProfileDialogGridAdapter extends BaseAdapter {

    private ArrayList<Photo> imageList;
    private Dialog dialog;

    public ProfileDialogGridAdapter(Dialog dialog, ArrayList<Photo> photoList) {
        super();
        Log.v("constructor", "abc");
        Log.v("constructor", ""+photoList.size());
        this.dialog = dialog;
        this.imageList = photoList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public static class ViewHolder {
        public ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder view;
        LayoutInflater inflater = dialog.getLayoutInflater();

        if(convertView==null) {
            view = new ViewHolder();
            convertView = inflater.inflate(R.layout.profile_dialog_grid_item, null);
            view.imageView = (ImageView) convertView.findViewById(R.id.imageView);

            convertView.setTag(view);
        } else {
            view = (ViewHolder) convertView.getTag();
        }

        //view.imageView.setImageResource(R.drawable.ic_launcher);
        Photo item = imageList.get(position);
        view.imageView.setImageBitmap(item.getBitmap());

        return convertView;
    }

}
