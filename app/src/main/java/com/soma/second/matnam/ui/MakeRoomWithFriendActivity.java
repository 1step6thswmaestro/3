package com.soma.second.matnam.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.soma.second.matnam.R;
import com.soma.second.matnam.listdubbies.provider.DataProvider;
import com.soma.second.matnam.ui.adapters.InstagramFollowerListAdapter;
import com.soma.second.matnam.ui.advrecyclerview.LikeListActivity;
import com.soma.second.matnam.ui.models.InstagramFollwer;
import com.soma.second.matnam.ui.models.User;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MakeRoomWithFriendActivity extends AppCompatActivity implements View.OnClickListener {

    GridView gridView;
    InstagramFollowerListAdapter registerFollowerGridAdapter;
    InstagramFollowerListAdapter instagramFollowerListAdapter;
    ArrayList<String> memberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_room_with_friend);
        setOnClickViews();

        memberList = new ArrayList<>();
        memberList.add(User.getId());

        ArrayList<InstagramFollwer> instagramFollwerList = new ArrayList<>();
        instagramFollwerList.add(new InstagramFollwer(User.getId(), User.getFullName(), User.getUserName(), User.getProfileImgUrl()));

        gridView = (GridView) findViewById(R.id.friend_grid);
        registerFollowerGridAdapter = new InstagramFollowerListAdapter(this, R.layout.item_insta_follower_grid, instagramFollwerList);
        gridView.setAdapter(registerFollowerGridAdapter);

        instagramFollowerListAdapter = new InstagramFollowerListAdapter(this, R.layout.item_insta_follower_list, DataProvider.instagramFollwerList);
    }

    private void setOnClickViews() {
        findViewById(R.id.friend_search_text).setOnClickListener(this);
        findViewById(R.id.friend_compose_text).setOnClickListener(this);
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
                                addRegisterUser((InstagramFollwer) item);
                            }
                        })
                        .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                        .setHeader(R.layout.dialogplus_header_search)
                        .create();
                dialog.show();
                break;
            case R.id.friend_compose_text :
                String members_id = memberList.toString();
                members_id = members_id.substring(1, members_id.length() - 1);
                LikeListActivity.makeRoomMembersId = members_id.trim();

                finish();
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
                    memberList.add(_user.getId());
                } else {
                    new SweetAlertDialog(MakeRoomWithFriendActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("가능 인원 수를 초과하였습니다.")
                            .setContentText("최대 6명의 친구들을 초대하여 방을 구성할 수 있습니다.")
                            .show();
                }
            }
        });
    }
}