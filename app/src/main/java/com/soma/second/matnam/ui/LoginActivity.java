package com.soma.second.matnam.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.kimyoungjoon.myapplication.backend.matnamApi.model.PlaceRecord;
import com.example.kimyoungjoon.myapplication.backend.matnamApi.model.UserRecord;
import com.faradaj.blurbehind.BlurBehind;
import com.soma.second.matnam.R;

import com.example.kimyoungjoon.myapplication.backend.matnamApi.MatnamApi;
import com.soma.second.matnam.Utils.CloudEndpointBuildHelper;
import com.soma.second.matnam.ui.widget.Indicator;
import com.soma.second.matnam.listdubbies.provider.FoodImgUrls;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.soma.second.matnam.ui.InstagramApp.OAuthAuthenticationListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

public class LoginActivity extends Activity implements View.OnClickListener {

	Indicator mIndicator;
	MatnamApi matnamApi = null;
	List<PlaceRecord> places;

	private InstagramApp mApp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		blurBehindBackAcitivity();

		mIndicator = new Indicator(this);

		mApp = new InstagramApp(this, ApplicationData.CLIENT_ID, ApplicationData.CLIENT_SECRET, ApplicationData.CALLBACK_URL);
		mApp.setListener(listener);

		Button loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.setOnClickListener(this);

		if(mApp.hasAccessToken()) {
			loginButton.setVisibility(View.INVISIBLE);
			new TestAsyncTask().execute(mApp.getUserName());
		} else {
			loginButton.setVisibility(View.VISIBLE);
		}
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
				if(mApp.hasAccessToken()) {
					final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
					builder.setMessage("Disconnect from Instagram?")
							.setCancelable(false)
							.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									mApp.resetAccessToken();
								}
							})
							.setNegativeButton("No", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							});

					final AlertDialog alert = builder.create();
					alert.show();
				} else {
					mApp.authorize();
				}

				//new TestAsyncTask().execute("testID");
				break;
		}
	}

	OAuthAuthenticationListener listener = new OAuthAuthenticationListener() {

		@Override
		public void onSuccess() {
			// TODO Auto-generated method stub
			//Go To NextActivity
			new TestAsyncTask().execute(mApp.getUserName());
		}

		@Override
		public void onFail(String error) {
			// TODO Auto-generated method stub
			Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
		}

	};

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

			int index = 0;
			for(Iterator<PlaceRecord> iter = places.iterator();iter.hasNext();){
				PlaceRecord place = iter.next();

				long foodId = place.getId();
				String foodName = place.getName();
				String imgUrl = place.getImgUrl();

				if (index < 10) {
					FoodImgUrls.foodId_left[index] = foodId;
					FoodImgUrls.foodName_left[index] = foodName;
					FoodImgUrls.foodImgUrl_left[index] = imgUrl;
				} else if (index < 20 && index >= 10) {
					FoodImgUrls.foodId_right[index-10] = foodId;
					FoodImgUrls.foodName_right[index-10] = foodName;
					FoodImgUrls.foodImgUrl_right[index-10] = imgUrl;
				}

				index++;
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (mIndicator.isShowing())
				mIndicator.hide();

			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if ( !mIndicator.isShowing())
				mIndicator.show();
		}
	}
}
