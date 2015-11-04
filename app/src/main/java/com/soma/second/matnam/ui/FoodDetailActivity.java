package com.soma.second.matnam.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.kimyoungjoon.myapplication.backend.matnamApi.MatnamApi;
import com.example.kimyoungjoon.myapplication.backend.matnamApi.model.PlaceRecord;
import com.example.kimyoungjoon.myapplication.backend.matnamApi.model.UserRecord;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.soma.second.matnam.R;
import com.soma.second.matnam.Utils.CloudEndpointBuildHelper;
import com.soma.second.matnam.Utils.InstagramRestClient;
import com.soma.second.matnam.listdubbies.provider.FoodImgUrls;
import com.soma.second.matnam.ui.adapters.FoodDetailGridAdapter;
import com.soma.second.matnam.ui.adapters.FriendGridAdapter;
import com.soma.second.matnam.ui.advrecyclerview.LikeListActivity;
import com.soma.second.matnam.ui.models.Food;
import com.soma.second.matnam.ui.models.Friend;
import com.soma.second.matnam.ui.widget.ExpandableHeightGridView;
import com.soma.second.matnam.ui.widget.Indicator;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;

public class FoodDetailActivity extends BaseActivity {

    Indicator mIndicator;

    ExpandableHeightGridView gridView;
    ArrayList<Food> foodArray = new ArrayList<Food>();
    FoodDetailGridAdapter foodDetailGridAdapter;

    public static final String FOOD_IMG_URL = "url";
    public static final String FOOD_NAME = "name";
    @InjectView(R.id.image)
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fooddetail);
        mIndicator = new Indicator(this);

        ButterKnife.inject(this);
        mBackground = mImageView;

        gridView = (ExpandableHeightGridView) findViewById(R.id.food_grid);
        gridView.setExpanded(true);
        foodDetailGridAdapter = new FoodDetailGridAdapter(this, R.layout.item_food, foodArray);
        gridView.setAdapter(foodDetailGridAdapter);

        String imageUrl = getIntent().getExtras().getString(FOOD_IMG_URL);
        String name = getIntent().getExtras().getString(FOOD_NAME);

        InstagramRestClient.get(InstagramRestClient.tagMediaRecent(name.trim()), null, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                if (!mIndicator.isShowing())
                    mIndicator.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (mIndicator.isShowing())
                    mIndicator.hide();

                try {
                    JSONArray dataArr = response.getJSONArray("data");
                    for(int i = 0; i < dataArr.length(); i++) {
                        JSONObject data = (JSONObject) dataArr.get(i);
                        String row_images_url = data.getJSONObject("images").getJSONObject("low_resolution").getString("url");
                        new loadBitmapAsyncTask().execute(row_images_url);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (mIndicator.isShowing())
                    mIndicator.hide();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable e) {
                if (mIndicator.isShowing())
                    mIndicator.hide();
            }
        });

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

    class loadBitmapAsyncTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            return getBitmapFromURL(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            foodDetailGridAdapter.add(new Food(result));
            if(foodDetailGridAdapter.getCount() > 5)
                foodDetailGridAdapter.notifyDataSetChanged();
        }
    }

    public Bitmap getBitmapFromURL(String src) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(src);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally{
            if(connection!=null)connection.disconnect();
        }
    }

}


