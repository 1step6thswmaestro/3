package com.soma.second.matnam.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import com.example.kimyoungjoon.myapplication.backend.matnamApi.model.PlaceRecord;
import com.example.kimyoungjoon.myapplication.backend.matnamApi.model.PlaceRecordCollection;
import com.example.kimyoungjoon.myapplication.backend.matnamApi.model.UserRecord;
import com.faradaj.blurbehind.BlurBehind;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.soma.second.matnam.Constants;
import com.soma.second.matnam.R;

import com.example.kimyoungjoon.myapplication.backend.matnamApi.MatnamApi;
import com.soma.second.matnam.Utils.CloudEndpointBuildHelper;
import com.soma.second.matnam.ui.widget.Indicator;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class LoginActivity extends Activity implements View.OnClickListener {

	Indicator mIndicator;
	MatnamApi matnamApi = null;
	List<PlaceRecord> places;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		blurBehindBackAcitivity();

		mIndicator = new Indicator(this);

		Button loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.setOnClickListener(this);

		new TestAsyncTask().execute("testID");
	}

	public void blurBehindBackAcitivity() {
		BlurBehind.getInstance()
				.withAlpha(150)
				.withFilterColor(Color.parseColor("#696969"))
				.setBackground(this);
	}


	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.loginButton :
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
				break;
		}
	}

	class TestAsyncTask extends AsyncTask<String, Void, String>{


		@Override
		protected String doInBackground(String... params) {
			if(matnamApi==null){
				matnamApi = CloudEndpointBuildHelper.getEndpoints();
			}

			UserRecord newUser = new UserRecord();

			newUser.setAge(1);
			newUser.setGender(true);
			newUser.setId(params[0]);
			newUser.setName("testUser");

			try {
				matnamApi.addUser(newUser).execute();
				places = matnamApi.getPlaces().execute().getItems();
			} catch (IOException e) {
				Log.e("API", "Error"+e.getMessage());
				e.printStackTrace();
			}


			for(Iterator<PlaceRecord> iter = places.iterator();iter.hasNext();){
				PlaceRecord place = iter.next();
				Log.i("Places", "name : " + place.getImgUrl());
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (mIndicator.isShowing())
				mIndicator.hide();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if ( !mIndicator.isShowing())
				mIndicator.show();
		}
	}
}
