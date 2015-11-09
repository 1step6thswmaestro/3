package com.soma.second.matnam.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.soma.second.matnam.R;
import com.soma.second.matnam.listdubbies.provider.DataProvider;
import com.soma.second.matnam.ui.adapters.InstagramFollowerListAdapter;
import com.soma.second.matnam.ui.models.InstagramFollwer;
import com.soma.second.matnam.ui.models.User;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RequestWithFriendActivity extends AppCompatActivity implements View.OnClickListener {

    GridView gridView;
    InstagramFollowerListAdapter registerFollowerGridAdapter;
    InstagramFollowerListAdapter instagramFollowerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);
        setOnClickViews();

        ArrayList<InstagramFollwer> instagramFollwerList = new ArrayList<>();
        instagramFollwerList.add(new InstagramFollwer(User.getId(), User.getFullName(), User.getUserName(), User.getProfileImg()));

        gridView = (GridView) findViewById(R.id.friend_grid);
        registerFollowerGridAdapter = new InstagramFollowerListAdapter(this, R.layout.item_insta_follower_grid, instagramFollwerList);
        gridView.setAdapter(registerFollowerGridAdapter);

        instagramFollowerListAdapter = new InstagramFollowerListAdapter(this, R.layout.item_insta_follower_list, DataProvider.instagramFollwerList);
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
                        .setAdapter(instagramFollowerListAdapter)
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
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, ReceiveJoinActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

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

    private void addRegisterUser(final InstagramFollwer _user) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int regCount = registerFollowerGridAdapter.getCount();
                if(regCount < 6) {
                    registerFollowerGridAdapter.add(_user);
                    registerFollowerGridAdapter.notifyDataSetChanged();
                } else {
                    new SweetAlertDialog(RequestWithFriendActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("가능 인원 수를 초과하였습니다.")
                            .setContentText("최대 6명의 친구들을 초대하여 방을 구성할 수 있습니다.")
                            .show();
                }
            }
        });
    }
}