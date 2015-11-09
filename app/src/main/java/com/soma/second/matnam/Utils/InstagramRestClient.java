package com.soma.second.matnam.Utils;

import android.media.MediaActionSound;

import com.loopj.android.http.*;
/**
 * Created by Dongjun on 15. 11. 5..
 */
public class InstagramRestClient {

    private static final String BASE_URL = "https://api.instagram.com/v1/";
    private static final String ACCESS_TOKEN

            = "?access_token=2175148622.0d3d2b4.c70813bc5e184aaf8bf03c17e0f97ab5";

    private static final String TAG = "tags/";
    private static final String MEDIA_RECENT = "/media/recent";

    private static final String USER = "users/";
    private static final String FOLLOWS = "/follows";
    private static final String FOLLOWD_BY = "/followed-by";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static String userInfo(String id) {
        return USER + id + ACCESS_TOKEN;
    }

    public static String tagMediaRecent(String tag_name) {
        return TAG + tag_name + MEDIA_RECENT + ACCESS_TOKEN;
    }

    public static String userFollows(String user_id) {
        return USER + user_id + FOLLOWS + ACCESS_TOKEN;
    }

    public static String userPhotos(String user_id) {
        return USER + user_id + MEDIA_RECENT + "/" + ACCESS_TOKEN;
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}