package com.soma.second.matnam.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.soma.second.matnam.R;
import com.soma.second.matnam.ui.advrecyclerview.ExpandableDraggableSwipeableExampleActivity;
import com.soma.second.matnam.ui.advrecyclerview.ExpandableExampleActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FoodDetailActivity extends BaseActivity {

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

        FloatingActionButton like_fab = (FloatingActionButton) findViewById(R.id.like_fab);
        like_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodDetailActivity.this, ExpandableDraggableSwipeableExampleActivity.class);
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


