package com.soma.second.matnam.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.soma.second.matnam.R;
import com.soma.second.matnam.ui.adapters.FriendGridAdapter;
import com.soma.second.matnam.ui.advrecyclerview.LikeListActivity;
import com.soma.second.matnam.ui.models.Friend;
import com.soma.second.matnam.ui.widget.ExpandableHeightGridView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FoodDetailActivity extends BaseActivity {

    ExpandableHeightGridView gridView;
    ArrayList<Friend> gridArray = new ArrayList<Friend>();
    FriendGridAdapter customGridAdapter;

    public static final String EXTRA_URL = "url";
    @InjectView(R.id.image)
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fooddetail);
        ButterKnife.inject(this);
        mBackground = mImageView;
        String imageUrl = getIntent().getExtras().getString(EXTRA_URL);
        Picasso.with(this).load(imageUrl).into((ImageView) findViewById(R.id.image), new Callback() {
            @Override
            public void onSuccess() {
//                moveBackground();
            }

            @Override
            public void onError() {
            }
        });

        gridView = (ExpandableHeightGridView) findViewById(R.id.food_grid);
        gridView.setExpanded(true);
        customGridAdapter = new FriendGridAdapter(this, R.layout.item_food, gridArray);
        gridView.setAdapter(customGridAdapter);

        customGridAdapter.add(new Friend("음식사진", "TEST"));
        customGridAdapter.add(new Friend("음식사진", "TEST"));
        customGridAdapter.add(new Friend("음식사진", "TEST"));
        customGridAdapter.add(new Friend("음식사진", "TEST"));
        customGridAdapter.add(new Friend("음식사진", "TEST"));
        customGridAdapter.add(new Friend("음식사진", "TEST"));
        customGridAdapter.add(new Friend("음식사진", "TEST"));
        customGridAdapter.add(new Friend("음식사진", "TEST"));
        customGridAdapter.add(new Friend("음식사진", "TEST"));

        customGridAdapter.notifyDataSetChanged();

        FloatingActionButton like_fab = (FloatingActionButton) findViewById(R.id.like_fab);
        like_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodDetailActivity.this, LikeListActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}


