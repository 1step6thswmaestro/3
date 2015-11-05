package com.soma.second.matnam.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.soma.second.matnam.R;
import com.soma.second.matnam.Utils.InstagramRestClient;
import com.soma.second.matnam.ui.models.Food;
import com.soma.second.matnam.ui.widget.Indicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.msebera.android.httpclient.Header;

public class MyInfoActivity extends AppCompatActivity {

    Indicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        mIndicator = new Indicator(this);

        String myInfoUrl = InstagramRestClient.userInfo(InstagramRestClient.getInstagramId());
        InstagramRestClient.get(myInfoUrl, null, new JsonHttpResponseHandler() {

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
                    JSONObject data = response.getJSONObject("data");

                    TextView full_name = (TextView) findViewById(R.id.myinfo_full_name);
                    full_name.setText(data.getString("full_name"));
                    new loadBitmapAsyncTask().execute(data.getString("profile_picture"));

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

    class loadBitmapAsyncTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally{
                if(connection!=null)connection.disconnect();
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            ImageView profile_img = (ImageView) findViewById(R.id.myinfo_profile_img);
            profile_img.setImageBitmap(result);
        }
    }
}
