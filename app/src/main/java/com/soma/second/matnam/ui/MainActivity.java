package com.soma.second.matnam.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.soma.second.matnam.R;
import com.soma.second.matnam.Utils.BackPressCloseHandler;
import com.soma.second.matnam.Utils.SharePreferences;
import com.soma.second.matnam.listdubbies.provider.FragmentTags;
import com.soma.second.matnam.ui.adapters.LocationListAdapter;
import com.soma.second.matnam.ui.advrecyclerview.LikeListActivity;
import com.soma.second.matnam.ui.fragments.ContentFragment;
import com.soma.second.matnam.ui.fragments.CustomizeFragment;
import com.soma.second.matnam.ui.fragments.ListBuddiesFragment;
import com.soma.second.matnam.ui.models.Location;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;


public class MainActivity extends ActionBarActivity implements CustomizeFragment.OnCustomizeListener, ViewAnimator.ViewAnimatorListener {

    private BackPressCloseHandler backPressCloseHandler;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ContentFragment contentFragment;
    private ViewAnimator viewAnimator;
    private int res = R.drawable.icn_1;
    private LinearLayout linearLayout;

    private boolean isOpenActivitiesActivated = true;
    private static final long RIPPLE_DURATION = 250;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backPressCloseHandler = new BackPressCloseHandler(this);

        contentFragment = ContentFragment.newInstance(R.drawable.ic_heart_48);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, contentFragment)
                .commit();

        if (savedInstanceState == null) {
            manageFragment(ListBuddiesFragment.newInstance(isOpenActivitiesActivated), FragmentTags.LIST_BUDDIES, false);
        }

        setSpeed(5);
        ButterKnife.inject(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, contentFragment, drawerLayout, this);

        LinearLayout locationLayout = (LinearLayout) findViewById(R.id.location_layout);
        locationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectLocation();
            }
        });

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
    public void setDivider(Drawable drawable) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setDivider(drawable);
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

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(ContentFragment.CLOSE, R.drawable.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem1 = new SlideMenuItem(ContentFragment.MYINFO, R.drawable.ic_user_64);
        list.add(menuItem1);
        SlideMenuItem menuItem2 = new SlideMenuItem(ContentFragment.ROOM, R.drawable.ic_likelist_64);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(ContentFragment.SEARCH, R.drawable.ic_search_64);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(ContentFragment.MAP, R.drawable.ic_map_64);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(ContentFragment.SETTING, R.drawable.ic_setting_64);
        list.add(menuItem5);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {

        Intent intent;

        switch (slideMenuItem.getName()) {
            case ContentFragment.CLOSE:
                return screenShotable;

            case ContentFragment.MYINFO:
                intent = new Intent(MainActivity.this, MyInfoActivity.class);
                startActivity(intent);
                return screenShotable;

            case ContentFragment.ROOM:
                intent = new Intent(MainActivity.this, LikeListActivity.class);
                startActivity(intent);
                return screenShotable;

            case ContentFragment.SEARCH:
                Toast.makeText(this, "이미지 검색이 들어갈 메뉴입니다", Toast.LENGTH_LONG).show();
                return screenShotable;
            case ContentFragment.MAP:
                selectLocation();
                return screenShotable;

            case ContentFragment.SETTING:
                manageFragment(CustomizeFragment.newInstance(), FragmentTags.CUSTOMIZE, true);
                return screenShotable;

            default: return screenShotable;
        }
    }

    public void selectLocation() {
        ArrayList<Location> locationArray = new ArrayList<Location>();
        locationArray.add(new Location("강남"));
        locationArray.add(new Location("신촌"));
        locationArray.add(new Location("선릉"));

        LocationListAdapter locationAdapter = new LocationListAdapter(this, R.layout.item_location, locationArray);

        DialogPlus dialog = DialogPlus.newDialog(this)
                .setAdapter(locationAdapter)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        Toast.makeText(MainActivity.this, ((Location) item).getName(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                })
                .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                .setHeader(R.layout.dialogplus_header_location)
                .create();

        dialog.show();
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();
    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }

    @Override
    public void onBackPressed() { backPressCloseHandler.onBackPressed(0); }
}
