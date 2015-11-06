package com.soma.second.matnam.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.kimyoungjoon.myapplication.backend.matnamApi.model.LikeRoomRecord;
import com.example.kimyoungjoon.myapplication.backend.matnamApi.model.PlaceRecord;
import com.example.kimyoungjoon.myapplication.backend.matnamApi.model.UserRecord;
import com.faradaj.blurbehind.BlurBehind;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.soma.second.matnam.R;

import com.example.kimyoungjoon.myapplication.backend.matnamApi.MatnamApi;
import com.soma.second.matnam.Utils.CloudEndpointBuildHelper;
import com.soma.second.matnam.Utils.InstagramRestClient;
import com.soma.second.matnam.ui.models.Food;
import com.soma.second.matnam.ui.models.InstagramFollwer;
import com.soma.second.matnam.ui.models.User;
import com.soma.second.matnam.ui.widget.Indicator;
import com.soma.second.matnam.listdubbies.provider.DataProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.soma.second.matnam.Utils.Utils.loadBitmap;
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

		if (mApp.hasAccessToken()) {
			loginButton.setVisibility(View.INVISIBLE);
			User.setId(mApp.getId());
			new initLoadingAsyncTask().execute(mApp.getUserName());
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
			case R.id.loginButton:
				new initLoadingAsyncTask().execute("testID");

				if (mApp.hasAccessToken()) {
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
				break;
		}
	}

	OAuthAuthenticationListener listener = new OAuthAuthenticationListener() {

		@Override
		public void onSuccess() {
			// TODO Auto-generated method stub
			//Go To NextActivity
			User.setId(mApp.getId());
			new initLoadingAsyncTask().execute(mApp.getId());
		}

		@Override
		public void onFail(String error) {
			// TODO Auto-generated method stub
			Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
		}

	};

	class initLoadingAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			if (matnamApi == null) {
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
				Log.e("API", "Error" + e.getMessage());
				e.printStackTrace();
			}

			int index = 0;
			for (Iterator<PlaceRecord> iter = places.iterator(); iter.hasNext(); ) {
				PlaceRecord place = iter.next();

				long foodId = place.getId();
				String foodName = place.getName();
				String imgUrl = place.getImgUrl();

				if (index < 10) {
					DataProvider.foodId_left[index] = foodId;
					DataProvider.foodName_left[index] = foodName;
					DataProvider.foodImgUrl_left[index] = imgUrl;
				} else if (index < 20 && index >= 10) {
					DataProvider.foodId_right[index - 10] = foodId;
					DataProvider.foodName_right[index - 10] = foodName;
					DataProvider.foodImgUrl_right[index - 10] = imgUrl;
				}
				index++;
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			String UserUrl = InstagramRestClient.userInfo(User.getId());
			InstagramRestClient.get(UserUrl, null, new JsonHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

					try {
						JSONObject data = response.getJSONObject("data");
						String id = data.getString("id");
						String fullName = data.getString("full_name");
						String userName = data.getString("username");
						String profileImgUrl = data.getString("profile_picture");

						User.setFullName(fullName);
						User.setUserName(userName);

						new setUserProfileImgAsyncTask().execute(profileImgUrl);
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}

			});

			String FollowerUrl = InstagramRestClient.userFollows(User.getId());
			InstagramRestClient.get(FollowerUrl, null, new JsonHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

					try {
						JSONArray dataArr = response.getJSONArray("data");
						for (int i = 0; i < dataArr.length(); i++) {
							JSONObject data = (JSONObject) dataArr.get(i);
							String id = data.getString("id");
							String fullName = data.getString("full_name");
							String userName = data.getString("username");
							String profileImgUrl = data.getString("profile_picture");

							new saveInstaFollowerAsyncTask(id, fullName, userName).execute(profileImgUrl);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}

			});

			new loadLikeRoomDataAsyncTask().execute();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (!mIndicator.isShowing())
				mIndicator.show();
		}
	}

	class setUserProfileImgAsyncTask extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
			return loadBitmap(params[0]);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			User.setProfileImg(result);
		}
	}

	class saveInstaFollowerAsyncTask extends AsyncTask<String, Void, Bitmap> {

		String id, fullName, userName;

		saveInstaFollowerAsyncTask(String _id, String _fullName, String _userName) {
			this.id = _id;
			this.fullName = _fullName;
			this.userName = _userName;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			return loadBitmap(params[0]);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			DataProvider.instagramFollwerList.add(new InstagramFollwer(id, fullName, userName, result));
		}
	}

	class loadLikeRoomDataAsyncTask extends AsyncTask<String, Void, List<LikeRoomRecord>> {

		List<LikeRoomRecord> likeRoomRecordList;

		@Override
		protected List<LikeRoomRecord> doInBackground(String... params) {
			if (matnamApi == null) {
				matnamApi = CloudEndpointBuildHelper.getEndpoints();
			}

			try {
				likeRoomRecordList = matnamApi.getLikeRooms().execute().getItems();
			} catch (IOException e) {
				Log.e("API", "Error" + e.getMessage());
				e.printStackTrace();
			}
			return likeRoomRecordList;
		}

		@Override
		protected void onPostExecute(List<LikeRoomRecord> resultList) {
			super.onPostExecute(resultList);
			DataProvider.likeRoomRecordList = resultList;

			for(LikeRoomRecord data : resultList) {
				new loadFoodImgAsyncTask().execute(data.getPlaceId());
			}

			Handler mHandler = new Handler();
			mHandler.postDelayed(new Runnable() {
				//Do Something
				@Override
				public void run() {
					if (mIndicator.isShowing())
						mIndicator.hide();

					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				}
			}, 1000 * 3);

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}

	class loadFoodImgAsyncTask extends AsyncTask<Long, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(Long... params) {

			PlaceRecord place = null;
			try {
				long place_id = params[0];
				place = matnamApi.getPlace(place_id).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return loadBitmap(place.getImgUrl());
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			DataProvider.likeRoomFoodImgList.add(result);
		}
	}

}
