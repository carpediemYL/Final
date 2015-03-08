package com.example.jihyun.memo1;

public class MemoListItem {

    private String[] mData;
    private String mId;
    private boolean mSelectable = true;

    public MemoListItem(String itemId, String[] obj) {
        mId = itemId;
        mData = obj;
    }

    public MemoListItem(String memoId, String memoDate, String memoText)
    {
        mId = memoId;
        mData = new String[2];
        mData[0] = memoDate;
        mData[1] = memoText;
    }

    public boolean isSelectable() {
        return mSelectable;
    }

    public void setSelectable(boolean selectable) {
        mSelectable = selectable;
    }

    public String getId() {
        return mId;
    }

    public void setId(String itemId) {
        mId = itemId;
    }

    public String[] getData() {
        return mData;
    }

    public String getData(int index) {
        if (mData == null || index >= mData.length) {
            return null;
        }

        return mData[index];
    }

    public void setData(String[] obj) {
        mData = obj;
    }


    public int compareTo(MemoListItem other) {
        if (mData != null) {
            Object[] otherData = other.getData();
            if (mData.length == otherData.length) {
                for (int i = 0; i < mData.length; i++) {
                    if (!mData[i].equals(otherData[i])) {
                        return -1;
                    }
                }
            } else {
                return -1;
            }
        } else {
            throw new IllegalArgumentException();
        }
        return 0;
    }
}
