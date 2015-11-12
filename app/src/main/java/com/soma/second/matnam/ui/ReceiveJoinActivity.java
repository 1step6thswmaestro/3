package com.soma.second.matnam.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.soma.second.matnam.R;
import com.soma.second.matnam.ui.adapters.InstagramFollowerListAdapter;
import com.soma.second.matnam.ui.advrecyclerview.ProfileDialog;
import com.soma.second.matnam.ui.models.Friend;
import com.soma.second.matnam.ui.models.InstagramFollwer;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ReceiveJoinActivity extends AppCompatActivity implements View.OnClickListener {

    GridView gridView;
    ArrayList<InstagramFollwer> gridArray = new ArrayList<>();
    InstagramFollowerListAdapter instaGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_join);

        setOnClickListener();

        gridView = (GridView) findViewById(R.id.receive_grid);
        instaGridAdapter = new InstagramFollowerListAdapter(this, R.layout.item_insta_follower_grid, gridArray);
        gridView.setAdapter(instaGridAdapter);

        instaGridAdapter.add(new InstagramFollwer("3", "test1", "test1", null));
        instaGridAdapter.add(new InstagramFollwer("4", "test2", "test2", null));
        instaGridAdapter.add(new InstagramFollwer("5", "test3", "test3", null));

        instaGridAdapter.notifyDataSetChanged();

        gridView.setOnItemClickListener(mItemClickListener);
    }

    private void setOnClickListener() {
        findViewById(R.id.receive_accept_text_text).setOnClickListener(this);
        findViewById(R.id.receive_decline_text).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.receive_accept_text_text :
                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("성사되었습니다.")
                        .setContentText("즐겁고 맛있는 식사되세요^^.")
                        .setConfirmText("확인")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                finish();
                            }
                        })
                        .show();

                break;
            case R.id.receive_decline_text :
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("거절하였습니다.")
                        .setContentText("다른 방에서 좋은 맛남을 가지시길 바래요!")
                        .setCancelText("취소")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .setConfirmText("확인")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                finish();
                            }
                        })
                        .show();
                break;
        }
    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long l_position) {
            ProfileDialog profileDialog = new ProfileDialog(ReceiveJoinActivity.this, instaGridAdapter.getItem(position).getId());
            profileDialog.show();
        }
    };
}