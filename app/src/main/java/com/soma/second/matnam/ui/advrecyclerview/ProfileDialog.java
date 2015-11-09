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
import com.soma.second.matnam.ui.adapters.ProfileDialogGridAdapter;

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

    private static final String TAG = "Profile-Dialog";

    public ProfileDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSpinner = new ProgressDialog(getContext());
        mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSpinner.setMessage("Loading...");
        mContent = new LinearLayout(getContext());
        mContent.setOrientation(LinearLayout.VERTICAL);
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

        TextView tv = new TextView(getContext());
        tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        tv.setText("UserName");
        linearLayout.addView(tv);

        GridView gv = new GridView(getContext());
        gv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        gv.setNumColumns(GridView.AUTO_FIT);
        //gv.setHorizontalSpacing(horizontalSpacing);

        ArrayList<String> a = new ArrayList<String>();
        a.add("abc");
        a.add("def");
        a.add("ghi");

        gv.setAdapter(new ProfileDialogGridAdapter(this, a));
        //gv.setBackgroundColor(android.R.color);
        linearLayout.addView(gv);

        mContent.addView(linearLayout);
    }


}
