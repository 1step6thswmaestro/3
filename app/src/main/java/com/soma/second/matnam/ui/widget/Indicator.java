package com.soma.second.matnam.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.victor.loading.rotate.RotateLoading;

import com.soma.second.matnam.R;


public class Indicator implements OnTouchListener
{
    private Activity xActivity = null;
    private View xView = null;
    private RotateLoading xLoading = null;
    private ViewGroup xLayout = null;

    private boolean isFirst = true;
    private boolean isShow = false;

    private final int ACTIVITY = 1;
    private final int LAYOUT = 2;
    private final int HIDE = 3;

    private int iMode = 0;

    public Indicator(Activity activity)
    {
        iMode = ACTIVITY;

        xView = View.inflate(activity, R.layout.indicator, null);
        xView.setOnTouchListener(this);
        xActivity = activity;
        xLoading = (RotateLoading) xView.findViewById(R.id.progressBar);
    }

    public Indicator(Context context, View layout)
    {
        iMode = LAYOUT;

        xView = View.inflate(context, R.layout.indicator, null);
        xView.setOnTouchListener(this);
        xLayout = (ViewGroup)layout;
        xLoading = (RotateLoading) xLayout.findViewById(R.id.progressBar);
    }

    public void show()
    {
        if(!isShow)
        {
            isShow = true;
            xLoading.start();
            xHandler.sendEmptyMessage(iMode);
        }
    }

    public void hide()
    {
        xLoading.stop();
        xHandler.sendEmptyMessage(HIDE);
    }

    public boolean isShowing()
    {
        return isShow;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        return true;
    }

    private Handler xHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
                case ACTIVITY :
                {
                    xView.setFocusable(true);

                    if(isFirst)
                    {
                        xActivity.addContentView(xView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
                        isFirst = false;
                    }
                    else
                    {
                        xView.setVisibility(View.VISIBLE);
                    }

                    break;
                }

                case LAYOUT :
                {
                    xView.setFocusable(true);

                    if(isFirst)
                    {
                        xLayout.addView(xView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
                        isFirst = false;
                    }
                    else
                    {
                        xView.setVisibility(View.VISIBLE);
                    }

                    break;
                }

                case HIDE :
                {
                    xView.setVisibility(View.GONE);
                    xView.setFocusable(false);

                    isShow = false;

                    break;
                }
            }
        }
    };
}