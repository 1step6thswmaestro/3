package com.soma.second.matnam.ui;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.soma.second.matnam.R;
import com.soma.second.matnam.ui.adapters.FriendGridAdapter;
import com.soma.second.matnam.ui.models.Friend;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RequestJoinActivity extends AppCompatActivity implements View.OnClickListener {

    GridView gridView;
    ArrayList<Friend> gridArray = new ArrayList<Friend>();
    FriendGridAdapter customGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_join);

        gridView = (GridView) findViewById(R.id.friend_grid);
        customGridAdapter = new FriendGridAdapter(this, R.layout.item_male_sample, gridArray);
        gridView.setAdapter(customGridAdapter);

        customGridAdapter.add(new Friend("name1", "TEST"));
        customGridAdapter.add(new Friend("name2", "TEST"));
        customGridAdapter.add(new Friend("name3", "TEST"));

        customGridAdapter.notifyDataSetChanged();

        findViewById(R.id.request_accept_text_text).setOnClickListener(this);
        findViewById(R.id.request_decline_text).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.request_accept_text_text :
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
            case R.id.request_decline_text :
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
}