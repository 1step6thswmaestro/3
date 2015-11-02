package com.soma.second.matnam.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.soma.second.matnam.R;
import com.soma.second.matnam.ui.adapters.FriendGridAdapter;
import com.soma.second.matnam.ui.models.Friend;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class InviteFriendActivity extends AppCompatActivity implements View.OnClickListener {

    GridView gridView;
    ArrayList<Friend> gridArray = new ArrayList<Friend>();
    FriendGridAdapter customGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);

        gridView = (GridView) findViewById(R.id.friend_grid);
        customGridAdapter = new FriendGridAdapter(this, R.layout.item_friend, gridArray);
        gridView.setAdapter(customGridAdapter);

        customGridAdapter.add(new Friend("url", "TEST"));
        customGridAdapter.add(new Friend("url", "TEST"));
        customGridAdapter.add(new Friend("url", "TEST"));

        customGridAdapter.notifyDataSetChanged();

        findViewById(R.id.friend_search_text).setOnClickListener(this);

        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("신청되었습니다!")
                .setContentText("답변이 오기까지 기다려주세요.")
                .show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.friend_search_text :
                DialogPlus dialog = DialogPlus.newDialog(this)
                        .setAdapter(customGridAdapter)
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                            }
                        })
                        .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                        .setHeader(R.layout.dialogplus_header_search)
                        .create();
                dialog.show();
                break;
        }
    }
}