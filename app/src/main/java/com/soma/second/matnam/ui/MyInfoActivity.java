package com.soma.second.matnam.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.kimyoungjoon.myapplication.backend.matnamApi.MatnamApi;
import com.example.kimyoungjoon.myapplication.backend.matnamApi.model.LikeRoomRecord;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.soma.second.matnam.R;
import com.soma.second.matnam.Utils.CloudEndpointBuildHelper;
import com.soma.second.matnam.Utils.InstagramRestClient;
import com.soma.second.matnam.listdubbies.provider.DataProvider;
import com.soma.second.matnam.ui.adapters.MyRoomListAdapter;
import com.soma.second.matnam.ui.advrecyclerview.data.AbstractExpandableDataProvider;
import com.soma.second.matnam.ui.advrecyclerview.data.MyRoomDataProvider;
import com.soma.second.matnam.ui.models.LikeRoom;
import com.soma.second.matnam.ui.models.MyRoom;
import com.soma.second.matnam.ui.models.User;
import com.soma.second.matnam.ui.widget.Indicator;
import com.soma.second.matnam.ui.widget.JoinRequestListDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.widget.ListView;
import android.view.View;

import cz.msebera.android.httpclient.Header;

import static com.soma.second.matnam.Utils.Utils.loadBitmap;
import android.widget.AdapterView;
import android.widget.Toast;

public class MyInfoActivity extends AppCompatActivity {

    Indicator mIndicator;
    ViewGroup mLayout;
    ListView myRoomList;
    MatnamApi matnamApi = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        mIndicator = new Indicator(this);
        mLayout = (ViewGroup) findViewById(R.id.container);

        TextView fullNameTextView = (TextView) findViewById(R.id.myinfo_full_name);
        fullNameTextView.setText(User.getFullName());

        myRoomList = (ListView) findViewById(R.id.myinfo_myroom_list);
        /*if(User.getFullName()=="" || User.getFullName()==null) {
            fullNameTextView.setText(User.getUserName());
        } else {
            fullNameTextView.setText(User.getFullName());
        }*/
        ImageView profileImgView = (ImageView) findViewById(R.id.myinfo_profile_img);
        Glide.with(this).load(User.getProfileImgUrl()).into(profileImgView);

        if (matnamApi == null) {
            matnamApi = CloudEndpointBuildHelper.getEndpoints();
        }

        List<LikeRoomRecord> likeRoomRecordList = DataProvider.likeRoomRecordList;
        Log.v("likeRoomRecordList",likeRoomRecordList.toString());
        ArrayList<LikeRoomRecord> mMyRoom = new ArrayList<LikeRoomRecord>();
        /*ArrayList<String> mRoomIdList  = MyRoom.mRoom_Id_List;
        String temp;
        for(int i=0; i<mRoomIdList.size(); i++) {
            try {
                mMyRoom.add(matnamApi.getLikeRoom(mRoomIdList.get(i)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        /*for (int i = 0; i < likeRoomRecordList.size(); i++) {

            LikeRoomRecord likeRoomRecord = likeRoomRecordList.get(i);
            Log.v("List.get(i)",likeRoomRecord.toString());
            String membersId = likeRoomRecord.getMembersId();
            Log.v("membersId",membersId);
            String[] memberIdArr = membersId.split(",");
            Log.v("memberIdArr[]",memberIdArr.toString());

            if(memberIdArr[0].equals(User.getId())) {
                Log.v("memberIdArr[0]", memberIdArr[0]);
                Log.v("User.getId()", User.getId());
                mMyRoom.add(likeRoomRecord);
            }
        }*/

        MyRoomDataProvider myRoomDataProvider = new MyRoomDataProvider();
        mMyRoom = myRoomDataProvider.getmMyRoom();

        //mMyRoom = MyRoom.mMyRoom_List;
        //if(mMyRoom==null)Log.v("mMyRoomList", "null");
        myRoomList.setAdapter(new MyRoomListAdapter(this, mMyRoom));

        myRoomList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long l_position) {
                //Toast.makeText(getApplicationContext(), mMyRoom.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                JoinRequestListDialog joinRequestListDialog = new JoinRequestListDialog(MyInfoActivity.this, position);
                joinRequestListDialog.show();
            }

        });
    }
}
