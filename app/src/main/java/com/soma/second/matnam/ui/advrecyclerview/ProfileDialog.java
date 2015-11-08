package com.soma.second.matnam.ui.advrecyclerview;

import java.util.ArrayList;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.ImageView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.soma.second.matnam.R;
import com.soma.second.matnam.Utils.InstagramRestClient;
import com.soma.second.matnam.ui.InstagramSession;
import com.soma.second.matnam.ui.adapters.ProfileDialogGridAdapter;
import com.soma.second.matnam.ui.advrecyclerview.data.LikeRoomDataProvider;
import java.net.URL;
import com.soma.second.matnam.ui.models.Photo;
import android.util.Log;
import static com.soma.second.matnam.Utils.Utils.loadBitmap;

/**
 * Created by youngjoosuh on 2015. 11. 7..
 */
public class ProfileDialog extends Dialog {
    static final float[] DIMENSIONS_LANDSCAPE = { 460, 260 };
    static final float[] DIMENSIONS_PORTRAIT = { 320, 420 };
    static final FrameLayout.LayoutParams FILL = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.FILL_PARENT,
            ViewGroup.LayoutParams.FILL_PARENT);
    static final int MARGIN = 4;
    static final int PADDING = 2;

    private ProgressDialog mSpinner;
    private LinearLayout mContent;
    private TextView mTitle;
    private ImageView childUserProfilePicture;
    private TextView childUserName;
    private GridView childUserPhotos;
    private ProfileDialogGridAdapter photoAdapter;
    ArrayList<Photo> photoArray = new ArrayList<Photo>();
    //private ArrayList<String> photoUrlList;

    private InstagramSession mSession;

    private int groupPosition;
    private int childPosition;

    private String imgUrl = "";

    LikeRoomDataProvider likeRoomDataProvider;
    String childName;
    long childId;

    private static final String TAG = "Profile-Dialog";
    private static final String API_URL = "https://api.instagram.com/v1";

    public ProfileDialog(Context context, int groupPosition, int childPosition) {
        super(context);
        // TODO Auto-generated constructor stub
        this.groupPosition = groupPosition;
        this.childPosition = childPosition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSpinner = new ProgressDialog(getContext());
        mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSpinner.setMessage("Loading...");
        mContent = new LinearLayout(getContext());
        mContent.setOrientation(LinearLayout.VERTICAL);
        mSession = new InstagramSession(getContext());
        photoAdapter = new ProfileDialogGridAdapter(this, photoArray);
        //photoUrlList = new ArrayList<String>();
        likeRoomDataProvider = new LikeRoomDataProvider();
        childName = likeRoomDataProvider.getChildItem(groupPosition, childPosition).getName();
        childId = likeRoomDataProvider.getChildItem(groupPosition, childPosition).getChildId();
        setUpTitle();
        setUpProfileView();

        Display display = getWindow().getWindowManager().getDefaultDisplay();
        final float scale = getContext().getResources().getDisplayMetrics().density;
        float[] dimensions = (display.getWidth() < display.getHeight()) ? DIMENSIONS_PORTRAIT
                : DIMENSIONS_LANDSCAPE;

        addContentView(mContent, new FrameLayout.LayoutParams(
                (int) (dimensions[0] * scale + 0.5f), (int) (dimensions[1]
                * scale + 0.5f)));
    }

    private void setUpTitle() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mTitle = new TextView(getContext());
        mTitle.setText("Instagram Profile");
        mTitle.setTextColor(Color.WHITE);
        mTitle.setTypeface(Typeface.DEFAULT_BOLD);
        mTitle.setBackgroundColor(Color.BLACK);
        mTitle.setPadding(MARGIN + PADDING, MARGIN, MARGIN, MARGIN);
        mContent.addView(mTitle);
    }

    private void setUpProfileView() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(params);

        childUserName = new TextView(getContext());
        childUserName.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        childUserName.setText(childName);
        linearLayout.addView(childUserName);

        childUserProfilePicture = new ImageView(getContext());
        childUserProfilePicture.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        String profilePictureUrl = "";
        CustomThread thread = new CustomThread(getContext(), childName);
        thread.start();
        try {
            thread.join();
        } catch(Exception e) {
            e.printStackTrace();
        }
        profilePictureUrl = thread.getImgUrl();
        new loadBitmapAsyncTask().execute(profilePictureUrl);
        linearLayout.addView(childUserProfilePicture);

        childUserPhotos = new GridView(getContext());
        childUserPhotos.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        childUserPhotos.setNumColumns(GridView.AUTO_FIT);

        new loadJSONDataAsyncTask().execute(API_URL + "/users/" + childName + "/media/recent?access_token=" + mSession.getAccessToken());
        linearLayout.addView(childUserPhotos);

        mContent.addView(linearLayout);
    }

    private String streamToString(InputStream is) throws IOException {
        String str = "";
        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is));
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
            } finally {
                is.close();
            }
            str = sb.toString();
        }
        return str;
    }

    public class CustomThread extends Thread {
        String imgUrl = "";
        String user_id;
        Context context;

        public CustomThread(Context context, String user_id) {
            this.context = context;
            this.user_id = user_id;
        }
        public void run() {
            try {
                URL url = new URL(API_URL + "/users/" + user_id + "/?access_token=" + mSession.getAccessToken());

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.connect();
                String response = streamToString(urlConnection.getInputStream());
                System.out.println(response);
                JSONObject jsonObj = (JSONObject) new JSONTokener(response).nextValue();
                imgUrl = jsonObj.getJSONObject("data").getString("profile_picture");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public String getImgUrl() {
            if(imgUrl.equals("") || imgUrl.equals(null)) return null;
            else return imgUrl;
        }
    }

    class loadBitmapAsyncTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            /*HttpURLConnection connection = null;
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
            }*/
            return loadBitmap(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            childUserProfilePicture.setImageBitmap(result);
        }
    }

    class loadJSONDataAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            // TODO Auto-generated method stub
            HttpURLConnection connection = null;
            String imgUrl = null;
            JSONObject imgJsonObj = null;
            ArrayList<String> photoList = new ArrayList<String>();
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                String response = streamToString(input);
                JSONObject jsonObj = (JSONObject) new JSONTokener(response).nextValue();
                JSONArray jsonData = jsonObj.getJSONArray("data");

                int length = jsonData.length();
                Log.v("length", ""+length);
                if(length > 0) {
                    for(int i=0; i<length; i++) {
                        imgJsonObj = jsonData.getJSONObject(i).getJSONObject("images").getJSONObject("low_resolution");
                        imgUrl = imgJsonObj.getString("url");
                        //new loadUserPhotosAsyncTask().execute(imgUrl);
                        photoList.add(imgUrl);
                    }
                }
                return photoList;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally{
                if(connection!=null) connection.disconnect();
            }
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            //mAdapter = new GridAdapter(getApplicationContext(), result);

            for(int i=0; i<result.size(); i++) {
                Log.v(i+"th URL", result.get(i));
                new loadUserPhotosAsyncTask().execute(result.get(i));
            }
        }
    }

    class loadUserPhotosAsyncTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            return loadBitmap(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            Log.v("result", "" + result);
            photoArray.add(new Photo(result));
            childUserPhotos.setAdapter(photoAdapter);
            //photoAdapter.notifyDataSetChanged();
            /*photoAdapter.add(new Food(result));
            if(photoAdapter.getCount() > 5) {
                photoAdapter.notifyDataSetChanged();
            }*/
        }
    }

}
