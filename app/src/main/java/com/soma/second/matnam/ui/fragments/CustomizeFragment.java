package com.soma.second.matnam.ui.fragments;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.jpardogo.listbuddies.lib.provider.ScrollConfigOptions;
import com.jpardogo.listbuddies.lib.views.ListBuddiesLayout;
import com.soma.second.matnam.R;
import com.soma.second.matnam.Utils.SharePreferences;
import com.soma.second.matnam.adapters.CustomizeSpinnersAdapter;
import com.soma.second.matnam.models.KeyValuePair;
import com.soma.second.matnam.provider.FragmentTags;
import com.soma.second.matnam.provider.SharedPrefKeys;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by jpardogo on 22/02/2014.
 */
public class CustomizeFragment extends Fragment {
    private static final String TAG = CustomizeFragment.class.getSimpleName();

    @InjectView(R.id.seekBarSpeed)
    SeekBar mSeekBarSpeed;
    @InjectView(R.id.seekBarSpeedValue)
    TextView seekBarSpeedValue;

    private OnCustomizeListener mOnCustomizeListener;
    private CustomizeSpinnersAdapter mSpinnerAdapter;
    private List<KeyValuePair> mColorSpinnerSections;
    private List<KeyValuePair> mScrollScrollSpinnerSections;
    private int[] mScrollSpinnerValues;

    private int mFillGapSpinnerPosition;
    private int mAutoScrollSpinnerPosition;
    private int mManualScrollSpinnerPosition;
    private int mDividerSpinnerPosition;

    private int mGapSeekBarProgress;
    private int mSpeedSeekBarProgress;
    private int mDivHeightSeekBarProgress;

    public static Fragment newInstance() {
        return new CustomizeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScrollSpinnerValues = getActivity().getResources().getIntArray(R.attr.scrollFaster);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customize, container, false);
        ButterKnife.inject(this, rootView);
        startConfig();

        Button settingCloseButton = (Button) rootView.findViewById(R.id.setting_close_button);
        settingCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(CustomizeFragment.this).commit();
            }
        });

        return rootView;
    }

    private void startConfig() {
        restartLastConfig();
        setProgressText();
        initSeekBars();
    }

    private void initSeekBars() {
        mSeekBarSpeed.setProgress(mSpeedSeekBarProgress);
        mSeekBarSpeed.setOnSeekBarChangeListener(mSeekBarListener);
    }

    private void setProgressText() {
        seekBarSpeedValue.setText(String.valueOf(mSpeedSeekBarProgress));
    }

    private void restartLastConfig() {
        mGapSeekBarProgress = SharePreferences.getValue(SharedPrefKeys.GAP_PROGRESS);
        mSpeedSeekBarProgress = SharePreferences.getValue(SharedPrefKeys.SPEED_PROGRESS);
        mDivHeightSeekBarProgress = SharePreferences.getValue(SharedPrefKeys.DIV_HEIGHT_PROGRESS);
    }

    private List<KeyValuePair> getScrollItems() {
        return new ArrayList<KeyValuePair>() {{
            add(new KeyValuePair(getString(R.string.right), mScrollSpinnerValues[ScrollConfigOptions.RIGHT.getConfigValue()]));
            add(new KeyValuePair(getString(R.string.left), mScrollSpinnerValues[ScrollConfigOptions.LEFT.getConfigValue()]));
        }};
    }

    private List<KeyValuePair> getFillGapSpinnerItems() {
        return new ArrayList<KeyValuePair>() {{
            add(new KeyValuePair(getString(R.string.black), CustomizeSpinnersAdapter.OptionTypes.BLACK));
            add(new KeyValuePair(getString(R.string.transparency), CustomizeSpinnersAdapter.OptionTypes.INSET));
            add(new KeyValuePair(getString(R.string.empty), CustomizeSpinnersAdapter.OptionTypes.EMPTY));

        }};
    }

    private SeekBar.OnSeekBarChangeListener mSeekBarListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.seekBarSpeed:
                    mSpeedSeekBarProgress = progress;
                    seekBarSpeedValue.setText(String.valueOf(progress));
                    mOnCustomizeListener.setSpeed(progress);
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mOnCustomizeListener = (OnCustomizeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + OnCustomizeListener.class.getSimpleName());
        }

    }

    public void reset() {
        startConfig();
    }

    public interface OnCustomizeListener {
        void setSpeed(int value);
        void setDivider(Drawable drawable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharePreferences.saveCustomization(SharedPrefKeys.GAP_PROGRESS, mGapSeekBarProgress);
        SharePreferences.saveCustomization(SharedPrefKeys.SPEED_PROGRESS, mSpeedSeekBarProgress);
        SharePreferences.saveCustomization(SharedPrefKeys.DIV_HEIGHT_PROGRESS, mDivHeightSeekBarProgress);
        SharePreferences.saveCustomization(SharedPrefKeys.FILL_GAP_POSITION, mFillGapSpinnerPosition);
        SharePreferences.saveCustomization(SharedPrefKeys.MANUAL_SCROLL_POSITION, mManualScrollSpinnerPosition);
        SharePreferences.saveCustomization(SharedPrefKeys.AUTO_SCROLL_POSITION, mAutoScrollSpinnerPosition);
        SharePreferences.saveCustomization(SharedPrefKeys.DIVIDERS_POSITION, mDividerSpinnerPosition);
    }

}
