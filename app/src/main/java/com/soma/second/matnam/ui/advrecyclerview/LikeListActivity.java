/*
 *    Copyright (C) 2015 Haruki Hasegawa
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.soma.second.matnam.ui.advrecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.soma.second.matnam.R;
import com.soma.second.matnam.ui.InviteFriendActivity;
import com.soma.second.matnam.ui.advrecyclerview.data.AbstractExpandableDataProvider;
import com.soma.second.matnam.ui.advrecyclerview.fragment.ExampleExpandableDataProviderFragment;
import com.soma.second.matnam.ui.advrecyclerview.fragment.ExpandableItemPinnedMessageDialogFragment;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LikeListActivity extends AppCompatActivity implements ExpandableItemPinnedMessageDialogFragment.EventListener, DatePickerDialog.OnDateSetListener, View.OnClickListener {
    private static final String FRAGMENT_TAG_DATA_PROVIDER = "data provider";
    private static final String FRAGMENT_LIST_VIEW = "list view";
    private static final String FRAGMENT_TAG_ITEM_PINNED_DIALOG = "item pinned dialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(new ExampleExpandableDataProviderFragment(), FRAGMENT_TAG_DATA_PROVIDER)
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new RecyclerListViewFragment(), FRAGMENT_LIST_VIEW)
                    .commit();
        }
        findViewById(R.id.like_add_fab).setOnClickListener(this);
    }

    /**
     * This method will be called when a group item is removed
     *
     * @param groupPosition The position of the group item within data set
     */
    public void onGroupItemRemoved(int groupPosition) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("화면에 다시 나타나지 않습니다")
                .setContentText("다른 방에서 좋은 맛남을 가지시길 바래요!")
                .show();

        Snackbar snackbar = Snackbar.make(
                findViewById(R.id.container),
                "잘못누르셨다면 여기를 눌러주세요.",
                Snackbar.LENGTH_LONG);

        snackbar.setAction(R.string.app_name, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemUndoActionClicked();
            }
        });
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.deep_orange_500));
        snackbar.show();
    }

    /**
     * This method will be called when a child item is removed
     *
     * @param groupPosition The group position of the child item within data set
     * @param childPosition The position of the child item within the group
     */
    public void onChildItemRemoved(int groupPosition, int childPosition) {
        Snackbar snackbar = Snackbar.make(
                findViewById(R.id.container),
                R.string.app_name,
                Snackbar.LENGTH_LONG);

        snackbar.setAction(R.string.app_name, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemUndoActionClicked();
            }
        });
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.deep_orange_500));
        snackbar.show();
    }

    /**
     * This method will be called when a group item is pinned
     *
     * @param groupPosition The position of the group item within data set
     */
    public void onGroupItemPinned(int groupPosition) {

        Intent intent = new Intent(LikeListActivity.this, InviteFriendActivity.class);
        startActivity(intent);
    }

    /**
     * This method will be called when a child item is pinned
     *
     * @param groupPosition The group position of the child item within data set
     * @param childPosition The position of the child item within the group
     */
    public void onChildItemPinned(int groupPosition, int childPosition) {
        final DialogFragment dialog = ExpandableItemPinnedMessageDialogFragment.newInstance(groupPosition, childPosition);

        getSupportFragmentManager()
                .beginTransaction()
                .add(dialog, FRAGMENT_TAG_ITEM_PINNED_DIALOG)
                .commit();
    }

    public void onGroupItemClicked(int groupPosition) {
        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_LIST_VIEW);
        AbstractExpandableDataProvider.GroupData data = getDataProvider().getGroupItem(groupPosition);

        if (data.isPinned()) {
            // unpin if tapped the pinned item
            data.setPinned(false);
            ((RecyclerListViewFragment) fragment).notifyGroupItemChanged(groupPosition);
        }
    }

    public void onChildItemClicked(int groupPosition, int childPosition) {
        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_LIST_VIEW);
        AbstractExpandableDataProvider.ChildData data = getDataProvider().getChildItem(groupPosition, childPosition);

        if (data.isPinned()) {
            // unpin if tapped the pinned item
            data.setPinned(false);
            ((RecyclerListViewFragment) fragment).notifyChildItemChanged(groupPosition, childPosition);
        }
    }

    private void onItemUndoActionClicked() {
        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_LIST_VIEW);
        final long result = getDataProvider().undoLastRemoval();

        if (result == RecyclerViewExpandableItemManager.NO_EXPANDABLE_POSITION) {
            return;
        }

        final int groupPosition = RecyclerViewExpandableItemManager.getPackedPositionGroup(result);
        final int childPosition = RecyclerViewExpandableItemManager.getPackedPositionChild(result);

        if (childPosition == RecyclerView.NO_POSITION) {
            // group item
            ((RecyclerListViewFragment) fragment).notifyGroupItemRestored(groupPosition);
        } else {
            // child item
            ((RecyclerListViewFragment) fragment).notifyChildItemRestored(groupPosition, childPosition);
        }
    }

    // implements ExpandableItemPinnedMessageDialogFragment.EventListener
    @Override
    public void onNotifyExpandableItemPinnedDialogDismissed(int groupPosition, int childPosition, boolean ok) {
        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_LIST_VIEW);

        if (childPosition == RecyclerView.NO_POSITION) {
            // group item
            getDataProvider().getGroupItem(groupPosition).setPinned(ok);
            ((RecyclerListViewFragment) fragment).notifyGroupItemChanged(groupPosition);
        } else {
            // child item
            getDataProvider().getChildItem(groupPosition, childPosition).setPinned(ok);
            ((RecyclerListViewFragment) fragment).notifyChildItemChanged(groupPosition, childPosition);
        }
    }

    public AbstractExpandableDataProvider getDataProvider() {
        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_DATA_PROVIDER);
        return ((ExampleExpandableDataProviderFragment) fragment).getDataProvider();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        TextView datePickTextview = (TextView) findViewById(R.id.pick_date_textview);
        datePickTextview.setText(year + ". " + monthOfYear + ". " + dayOfMonth );
    }

    @Override
    public void onClick(View view) {

        DialogPlus dialog = DialogPlus.newDialog(LikeListActivity.this)
                .setHeader(R.layout.dialogplus_header_text)
                .setContentHolder(new ViewHolder(R.layout.dialogplus_add_content))
                .setCancelable(true)
                .setContentHeight(650)
                .create();

        switch (view.getId()) {
            case R.id.like_add_fab:
                dialog.show();

                findViewById(R.id.pick_date_textview).setOnClickListener(this);
                findViewById(R.id.pick_friend_textview).setOnClickListener(this);
                findViewById(R.id.dialog_ok_textview).setOnClickListener(this);
                break;

            case R.id.pick_date_textview :
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        LikeListActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
                break;

            case R.id.pick_friend_textview :
                Intent intent = new Intent(LikeListActivity.this, InviteFriendActivity.class);
                startActivity(intent);
                break;

            case R.id.dialog_ok_textview :
                dialog.dismiss();
                Toast.makeText(LikeListActivity.this, "방을 만들었습니다.", Toast.LENGTH_LONG).show();
                break;
        }

    }
}
