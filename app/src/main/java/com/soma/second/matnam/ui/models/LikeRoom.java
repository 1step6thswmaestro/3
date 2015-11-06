package com.soma.second.matnam.ui.models;

import com.soma.second.matnam.ui.advrecyclerview.data.AbstractExpandableDataProvider;

/**
 * Created by Dongjun on 15. 11. 7..
 */
public class LikeRoom {
    public static final class GroupData extends AbstractExpandableDataProvider.GroupData {

        private long mId;
        private long mRoom_Id;
        private long mPlace_Id;
        private String mTitle;
        private String mDate;
        private String mMembers_Id;
        private int mMember_Count;
        private boolean mPinned;
        private long mNextChildId;

        public GroupData(long _id, long _room_id, long _place_id, String _title, String _date, String _members_id, int _member_count) {
            mId = _id;
            mRoom_Id = _room_id;
            mPlace_Id = _place_id;
            mTitle = _title;
            mDate = _date;
            mMembers_Id = _members_id;
            mMember_Count = _member_count;
            mNextChildId = 0;
        }

        @Override
        public String getTitle() {
            return mTitle;
        }

        @Override
        public String getDate() {
            return mDate;
        }

        @Override
        public int getMemberCount() {
            return mMember_Count;
        }

        @Override
        public long getGroupId() {
            return mId;
        }

        @Override
        public void setPinned(boolean pinnedToSwipeLeft) {
            mPinned = pinnedToSwipeLeft;
        }

        @Override
        public boolean isPinned() {
            return mPinned;
        }

        public long generateNewChildId() {
            final long id = mNextChildId;
            mNextChildId += 1;
            return id;
        }
    }

    public static final class ChildData extends AbstractExpandableDataProvider.ChildData {

        private long mId;
        private final String mText;
        private boolean mPinned;

        public ChildData(long id, String text) {
            mId = id;
            mText = text;
        }

        @Override
        public String getName() {
            return mText;
        }

        @Override
        public long getChildId() {
            return mId;
        }

        @Override
        public void setPinned(boolean pinned) {
            mPinned = pinned;
        }

        @Override
        public boolean isPinned() {
            return mPinned;
        }

        public void setChildId(long id) {
            this.mId = id;
        }
    }
}
