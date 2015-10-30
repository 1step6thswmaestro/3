package com.soma.second.matnam.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.faradaj.blurbehind.BlurBehind;
import com.faradaj.blurbehind.OnBlurCompleteListener;
import com.soma.second.matnam.R;

public class IntroActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                LinearLayout introLayout = (LinearLayout) findViewById(R.id.intro_layout);
                introLayout.setVisibility(View.INVISIBLE);

                ImageView blurbgImageView = (ImageView) findViewById(R.id.blurbg_image);
                blurbgImageView.setVisibility(View.VISIBLE);
            }
        }, 3000);

        handler.postDelayed(new Runnable() {
            public void run() {
            BlurBehind.getInstance().execute(IntroActivity.this, new OnBlurCompleteListener() {
                @Override
                public void onBlurComplete() {
                    Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }
            });
            }
        }, 3000);


    }
}
