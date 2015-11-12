package com.soma.second.matnam.ui.widget;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import com.example.kimyoungjoon.myapplication.backend.matnamApi.model.LikeRoomRecord;
import com.soma.second.matnam.R;
import com.soma.second.matnam.ui.InstagramSession;
import com.soma.second.matnam.ui.adapters.MyRoomListAdapter;
import com.soma.second.matnam.ui.adapters.ProfileDialogGridAdapter;
import com.soma.second.matnam.ui.adapters.joinRequestListDialogAdapter;
import com.soma.second.matnam.ui.advrecyclerview.data.LikeRoomDataProvider;
import com.soma.second.matnam.ui.advrecyclerview.data.MyRoomDataProvider;
import com.soma.second.matnam.ui.models.Photo;
import android.widget.RelativeLayout.LayoutParams;
import android.util.Log;
import android.support.v4.util.Pair;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by youngjoosuh on 2015. 11. 13..
 */
public class JoinRequestListDialog extends Dialog {

    MyRoomDataProvider myRoomDataProvider;
    ArrayList<String> requestJoinUsers;
    String[] membersInOneRequest;
    List<Pair<LikeRoomRecord, ArrayList<String>>> joinRequestData;
    int position;

    static final float[] DIMENSIONS_LANDSCAPE = { 460, 260 };
    static final float[] DIMENSIONS_PORTRAIT = { 320, 420 };
    static final FrameLayout.LayoutParams FILL = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.FILL_PARENT,
            ViewGroup.LayoutParams.FILL_PARENT);
    static final int MARGIN = 4;
    static final int PADDING = 2;

    private LinearLayout mContent;
    private TextView textView;
    private ListView listView;
    LikeRoomDataProvider likeRoomDataProvider;

    public JoinRequestListDialog(Context context, int position) {
        super(context);
        //this.context = context;
        this.position = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContent = new LinearLayout(getContext());
        mContent.setOrientation(LinearLayout.VERTICAL);

        //setUpTitle();
        setListView();

        Display display = getWindow().getWindowManager().getDefaultDisplay();
        final float scale = getContext().getResources().getDisplayMetrics().density;
        float[] dimensions = (display.getWidth() < display.getHeight()) ? DIMENSIONS_PORTRAIT
                : DIMENSIONS_LANDSCAPE;

        addContentView(mContent, new FrameLayout.LayoutParams(
                (int) (dimensions[0] * scale + 0.5f), (int) (dimensions[1]
                * scale + 0.5f)));

        myRoomDataProvider = new MyRoomDataProvider();
        requestJoinUsers = new ArrayList<String>();
        membersInOneRequest = new String[]{};
        joinRequestData = myRoomDataProvider.getmData();
        //requestJoinUsers = joinRequestData.get(position).second;//request들의 집합

        /*for(int i=0; i<requestJoinUsers.size(); i++) {
            membersInOneRequest = requestJoinUsers.get(i).split(",");
        }*/
    }

    private void setUpTitle() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        textView = new TextView(getContext());
        textView.setText("이 방에 들어온 Join 요청");
        textView.setTextColor(Color.WHITE);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setBackgroundColor(getContext().getResources().getColor(R.color.deep_orange_400));
        textView.setPadding(MARGIN + PADDING, MARGIN, MARGIN, MARGIN);
        mContent.addView(textView);
    }

    private void setListView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setPadding(20, 20, 20, 20);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        textView = new TextView(getContext());
        textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        textView.setText("이 방에 들어온 Join 요청");
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(25);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        //textView.setGravity(Gravity.CENTER);
        linearLayout.addView(textView);

        listView = new ListView(getContext());
        listView.setPadding(0,20,0,20);
        listView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        //listView.setBackgroundColor(Color.BLUE);
        //requestJoinUsers = joinRequestData.get(position).second;//request들의 집합
        Log.v("requestJoinUsers", "" + requestJoinUsers);
        listView.setAdapter(new joinRequestListDialogAdapter(getContext(), requestJoinUsers));
        linearLayout.addView(listView);

        mContent.addView(linearLayout);
    }
}
