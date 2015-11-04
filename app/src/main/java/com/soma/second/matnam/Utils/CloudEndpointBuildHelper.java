package com.soma.second.matnam.Utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.kimyoungjoon.myapplication.backend.matnamApi.MatnamApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.soma.second.matnam.Constants;

import java.io.IOException;

/**
 * Created by kimyoungjoon on 2015. 11. 4..
 */
public class CloudEndpointBuildHelper {

//    static SharedPreferences settings = null;
//    static GoogleAccountCredential credential = null;

    public CloudEndpointBuildHelper() {
    }

    public static MatnamApi getEndpoints() {

//        settings = activity.getSharedPreferences("Matnam", 0);
//        credential = GoogleAccountCredential.usingAudience(activity,
//                "server:client_id:" + Constants.WEB_CLIENT_ID);

        // Create API handler
        MatnamApi.Builder builder = new MatnamApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                .setRootUrl("https://swmaestro1-2.appspot.com/_ah/api/");

        return builder.build();
    }

    // setSelectedAccountName definition
//    private void setSelectedAccountName(String accountName) {
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putString("PREF_ACCOUNT_NAME", accountName);
//        editor.commit();
//        credential.setSelectedAccountName(accountName);
//        this.accountName = accountName;
//    }
}

