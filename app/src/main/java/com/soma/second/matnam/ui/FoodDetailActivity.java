package com.soma.second.matnam.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kimyoungjoon.myapplication.backend.matnamApi.MatnamApi;
import com.example.kimyoungjoon.myapplication.backend.matnamApi.model.PlaceRecord;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.soma.second.matnam.R;
import com.soma.second.matnam.Utils.CloudEndpointBuildHelper;
import com.soma.second.matnam.Utils.InstagramRestClient;
import com.soma.second.matnam.ui.adapters.FoodDetailGridAdapter;
import com.soma.second.matnam.ui.advrecyclerview.LikeListActivity;
import com.soma.second.matnam.ui.models.Food;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;

import static com.soma.second.matnam.Utils.Utils.loadBitmap;

public class FoodDetailActivity extends BaseActivity implements View.OnClickListener {

    MatnamApi matnamApi = null;
    Indicator mIndicator;

    ExpandableHeightGridView gridView;
    ArrayList<Food> foodArray = new ArrayList<Food>();
    FoodDetailGridAdapter foodDetailGridAdapter;

    public static final String FOOD_ID = "id";
    public static final String FOOD_NAME = "name";
    public static final String FOOD_IMG_URL = "url";

    private long placeId;

    @InjectView(R.id.image)
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fooddetail);
        mIndicator = new Indicator(this);

        findViewById(R.id.like_fab).setOnClickListener(this);

        ButterKnife.inject(this);
        mBackground = mImageView;

        gridView = (ExpandableHeightGridView) findViewById(R.id.food_grid);
        gridView.setExpanded(true);
        foodDetailGridAdapter = new FoodDetailGridAdapter(this, R.layout.item_food, foodArray);
        gridView.setAdapter(foodDetailGridAdapter);

        placeId = getIntent().getExtras().getLong(FOOD_ID);
        String name = getIntent().getExtras().getString(FOOD_NAME);
        String imageUrl = getIntent().getExtras().getString(FOOD_IMG_URL);

        new loadPlaceAsyncTask().execute(placeId);

        Picasso.with(this).load(imageUrl).into((ImageView) findViewById(R.id.image), new Callback() {
            @Override
            public void onSuccess() { moveBackground(); }

            @Override
            public void onError() { }
        });

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
                    for (int i = 0; i < dataArr.length(); i++) {
                        JSONObject data = (JSONObject) dataArr.get(i);
                        String row_images_url = data.getJSONObject("images").getJSONObject("low_resolution").getString("url");
                        new loadBitmapAsyncTask().execute(row_images_url);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable e) {
                if (mIndicator.isShowing())
                    mIndicator.hide();
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.like_fab :
                Intent intent = new Intent(FoodDetailActivity.this, LikeListActivity.class);
                intent.putExtra("placeId", placeId);
                startActivity(intent);
                break;
        }
    }

    class loadPlaceAsyncTask extends AsyncTask<Long, Void, PlaceRecord>{

        @Override
        protected PlaceRecord doInBackground(Long... params) {
            if(matnamApi==null){
                matnamApi = CloudEndpointBuildHelper.getEndpoints();
            }

            PlaceRecord placeRecord = null;
            try {
                placeRecord = matnamApi.getPlace(params[0]).execute();
            } catch (IOException e) {
                Log.e("API", "Error"+e.getMessage());
                e.printStackTrace();
            }
            return placeRecord;
        }

        @Override
        protected void onPostExecute(PlaceRecord result) {
            super.onPostExecute(result);

            TextView nameTextView = (TextView) findViewById(R.id.food_name);
            nameTextView.setText(result.getName());

            TextView categoryTextView = (TextView) findViewById(R.id.food_category);
            categoryTextView.setText(result.getCategory());

            TextView descriptionTextView = (TextView) findViewById(R.id.food_description);
            descriptionTextView.setText("소개 : " + result.getDescription());

            TextView locationTextView = (TextView) findViewById(R.id.food_location);
            locationTextView.setText("위치 : " + result.getLocation());

            TextView priceTextView = (TextView) findViewById(R.id.food_price);
            priceTextView.setText("가격대 : " + result.getPrice());

            TextView openingTextView = (TextView) findViewById(R.id.food_opening);
            openingTextView.setText("영업시간 : " + result.getOpening());

            TextView telTextView = (TextView) findViewById(R.id.food_tel);
            telTextView.setText("전화번호 : " + result.getTel());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if ( !mIndicator.isShowing())
                mIndicator.show();
        }
    }

    class loadBitmapAsyncTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            return loadBitmap(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            foodDetailGridAdapter.add(new Food(result));
            if(foodDetailGridAdapter.getCount() > 5) {
                foodDetailGridAdapter.notifyDataSetChanged();
                if (mIndicator.isShowing())
                    mIndicator.hide();
            }
        }
    }
}


