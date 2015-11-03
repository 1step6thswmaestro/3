package com.soma.second.matnam.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import com.soma.second.matnam.ui.adapters.InstaListAdapter;
import com.soma.second.matnam.ui.models.Friend;
import com.soma.second.matnam.ui.models.Insta;

import java.lang.reflect.Array;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class InviteFriendActivity extends AppCompatActivity implements View.OnClickListener {

    GridView gridView;
    ArrayList<Friend> friendArray = new ArrayList<Friend>();
    FriendGridAdapter friendGridAdapter;

    ArrayList<Insta> instaArray = new ArrayList<Insta>();
    InstaListAdapter instaListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);
        setOnClickViews();

        gridView = (GridView) findViewById(R.id.friend_grid);
        friendGridAdapter = new FriendGridAdapter(this, R.layout.item_female_sample, friendArray);
        gridView.setAdapter(friendGridAdapter);

        friendGridAdapter.add(new Friend("name1", "TEST"));
        friendGridAdapter.add(new Friend("name2", "TEST"));
        friendGridAdapter.add(new Friend("name3", "TEST"));

        friendGridAdapter.notifyDataSetChanged();

        instaArray.add(new Insta("name1", "test"));
        instaArray.add(new Insta("name2", "test"));
        instaArray.add(new Insta("name3", "test"));
        instaArray.add(new Insta("name4", "test"));
        instaArray.add(new Insta("name5", "test"));
        instaArray.add(new Insta("name6", "test"));
        instaArray.add(new Insta("name7", "test"));

        instaListAdapter = new InstaListAdapter(this, R.layout.item_insta_follower, instaArray);
    }

    private void setOnClickViews() {
        findViewById(R.id.friend_search_text).setOnClickListener(this);
        findViewById(R.id.friend_request_text).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.friend_search_text:
                DialogPlus dialog = DialogPlus.newDialog(this)
                        .setAdapter(instaListAdapter)
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
            case R.id.friend_request_text :
                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("신청되었습니다!")
                        .setContentText("답변이 오기까지 기다려주세요.")
                        .show();

                NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, RequestJoinActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

                Notification notif = new Notification.Builder(this)
                        .setContentTitle("같이 식사를 하자는 요청이 들어왔습니다.")
                        .setContentText("안녕하세요 저희랑 같이 식사하시겠어요?")
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_logo_64)
                        .build();

                nm.notify(111, notif);
                break;
        }
    }
}