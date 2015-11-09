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
/**
 * Created by youngjoosuh on 2015. 11. 7..
 */
public class ProfileDialogGridAdapter extends BaseAdapter {

    private ArrayList<String> imageList;
    private Dialog dialog;

    public ProfileDialogGridAdapter(Dialog dialog, ArrayList<String> photoList) {
        super();
        Log.v("constructor", "abc");
        Log.v("constructor", ""+photoList.size());
        this.imageList = photoList;
        this.dialog = dialog;
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

        view.imageView.setImageResource(R.drawable.ic_launcher);

        return convertView;
    }

}
