/*
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.kimyoungjoon.myapplication.backend;

/**
 * API Keys, Client Ids and Audience Ids for accessing APIs and configuring
 * Cloud Endpoints.
 * When you deploy your solution, you need to use your own API Keys and IDs.
 * Please refer to the documentation for this sample for more details.
 */
public final class Constants {
    // User: Update keys

    /**
     * Google Cloud Messaging API key.
     */
    public static final String GCM_API_KEY = "YOUR-GCM-API-KEY";


    /**
     * Web client ID from Google Cloud console.
     */
    public static final String WEB_CLIENT_ID = "185452657224-k5ld8p3cb1udtpb4152i11fr1vprl44q.apps.googleusercontent.com";

    /**
     * Android client ID from Google Cloud console.
     */
    public static final String ANDROID_CLIENT_ID = "185452657224-jqu0us0pl3aqtohvhk30tenafbqthn9e.apps.googleusercontent.com";

    /**
     * API package name.
     */
    public static final String API_OWNER =
            "backend.myapplication.kimyoungjoon.example.com";

    /**
     * API package path.
     */
    public static final String API_PACKAGE_PATH = "";

    /**
     * Default constrictor, never called.
     */
    private Constants() { }
}
