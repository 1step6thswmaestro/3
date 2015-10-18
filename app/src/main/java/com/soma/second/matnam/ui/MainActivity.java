package com.soma.second.matnam.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.soma.second.matnam.R;
import com.soma.second.matnam.Utils.SharePreferences;
import com.soma.second.matnam.provider.FragmentTags;
import com.soma.second.matnam.ui.fragments.CustomizeFragment;
import com.soma.second.matnam.ui.fragments.ListBuddiesFragment;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity implements CustomizeFragment.OnCustomizeListener {

    private boolean isOpenActivitiesActivated = true;
    private static final long RIPPLE_DURATION = 250;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.root)
    FrameLayout root;
    @InjectView(R.id.content_hamburger)
    View contentHamburger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            manageFragment(ListBuddiesFragment.newInstance(isOpenActivitiesActivated), FragmentTags.LIST_BUDDIES, false);
        }

        ButterKnife.inject(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.menu_guillotine, null);
        root.addView(guillotineMenu);

        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = new MenuInflater(this);
//        menuInflater.inflate(R.menu.main, menu);
//        MenuItem openActivities = menu.findItem(R.id.action_open_activities);
//        openActivities.setChecked(isOpenActivitiesActivated);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_activities:
                onOpenActivitiesClick(item);
                break;
            case R.id.action_reset:
                resetLayout();
                break;
            case R.id.action_customize:
                manageFragment(CustomizeFragment.newInstance(), FragmentTags.CUSTOMIZE, true);
                break;
            case R.id.action_about:
                startActivityWith(AboutActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startActivityWith(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    private void manageFragment(Fragment newInstanceFragment, FragmentTags tag, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment currentIntanceFragment = findFragmentByTag(tag);
        if (currentIntanceFragment == null || (currentIntanceFragment != null && currentIntanceFragment.isHidden())) {
            if (currentIntanceFragment != null) {
                ft.show(currentIntanceFragment);
            } else {
                currentIntanceFragment = newInstanceFragment;
                ft.add(R.id.container, currentIntanceFragment, tag.toString());
                if (addToBackStack) {
                    ft.addToBackStack(null);
                }
            }
        } else {
            ft.hide(currentIntanceFragment);
            fm.popBackStack();
        }
        ft.commit();
    }

    private Fragment findFragmentByTag(FragmentTags tag) {
        return getSupportFragmentManager().findFragmentByTag(tag.toString());
    }

    @Override
    public void setSpeed(int value) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setSpeed(value);
        }
    }

    @Override
    public void setGap(int value) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setGap(value);
        }
    }

    @Override
    public void setGapColor(int color) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setGapColor(color);
        }
    }

    @Override
    public void setDivider(Drawable drawable) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setDivider(drawable);
        }
    }

    @Override
    public void setDividerHeight(int value) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setDividerHeight(value);
        }
    }

    @Override
    public void setAutoScrollFaster(int option) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setAutoScrollFaster(option);
        }
    }

    @Override
    public void setScrollFaster(int option) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setScrollFaster(option);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        reset();
    }

    private void resetLayout() {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.resetLayout();
            reset();
            CustomizeFragment customizeFragment = (CustomizeFragment) findFragmentByTag(FragmentTags.CUSTOMIZE);
            if (customizeFragment != null) {
                customizeFragment.reset();
            }
        }
    }

    private void reset() {
        SharePreferences.reset();
    }

    public boolean onOpenActivitiesClick(MenuItem menuItem) {
        isOpenActivitiesActivated = !menuItem.isChecked();
        menuItem.setChecked(isOpenActivitiesActivated);
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setOpenActivities(isOpenActivitiesActivated);
        }

        return false;
    }

    private ListBuddiesFragment getListBuddiesFragment() {
        return (ListBuddiesFragment) findFragmentByTag(FragmentTags.LIST_BUDDIES);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}
