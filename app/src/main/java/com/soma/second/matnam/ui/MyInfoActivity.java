package com.soma.second.matnam.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.soma.second.matnam.R;
import com.soma.second.matnam.Utils.InstagramRestClient;
import com.soma.second.matnam.ui.models.User;
import com.soma.second.matnam.ui.widget.Indicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.msebera.android.httpclient.Header;

import static com.soma.second.matnam.Utils.Utils.loadBitmap;

public class MyInfoActivity extends AppCompatActivity {

    Indicator mIndicator;
    ViewGroup mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        mIndicator = new Indicator(this);
        mLayout = (ViewGroup) findViewById(R.id.container);

        TextView fullNameTextView = (TextView) findViewById(R.id.myinfo_full_name);
        fullNameTextView.setText(User.getFullName());

        ImageView profileImgView = (ImageView) findViewById(R.id.myinfo_profile_img);
        profileImgView.setImageBitmap(User.getProfileImg());
    }
}
