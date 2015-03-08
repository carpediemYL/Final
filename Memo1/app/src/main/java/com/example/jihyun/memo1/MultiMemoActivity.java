package com.example.jihyun.memo1;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Date;
import java.util.Locale;


import com.example.jihyun.memo1.common.TitleBitmapButton;
import com.example.jihyun.memo1.db.MemoDatabase;

public class MultiMemoActivity extends Activity {

    public static final String TAG = "MultiMemoActivity";

    ListView mMemoListView;
    MemoListAdapter mMemoListAdapter;

    int mMemoCount = 0;

    TextView itemCount;

    public static MemoDatabase mDatabase = null;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multimemo);


        Locale curLocale = getResources().getConfiguration().locale;
        BasicInfo.language = curLocale.getLanguage();
        Log.d(TAG, "current language : " + BasicInfo.language);


        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, R.string.no_sdcard_message, Toast.LENGTH_LONG).show();
            return;
        } else {
            String externalPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            if (!BasicInfo.ExternalChecked && externalPath != null) {
                BasicInfo.ExternalPath = externalPath + File.separator;
                Log.d(TAG, "ExternalPath : " + BasicInfo.ExternalPath);

                BasicInfo.DATABASE_NAME = BasicInfo.ExternalPath + BasicInfo.DATABASE_NAME;
                BasicInfo.ExternalChecked = true;
            }
        }


        mMemoListView = (ListView)findViewById(R.id.memoList);
        mMemoListAdapter = new MemoListAdapter(this);
        mMemoListView.setAdapter(mMemoListAdapter);
        mMemoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                viewMemo(position);
            }
        });


        TitleBitmapButton newMemoBtn = (TitleBitmapButton)findViewById(R.id.newMemoBtn);
        newMemoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "newMemoBtn clicked.");

                Intent intent = new Intent(getApplicationContext(), MemoInsertActivity.class);
                intent.putExtra(BasicInfo.KEY_MEMO_MODE, BasicInfo.MODE_INSERT);
                startActivityForResult(intent, BasicInfo.REQ_INSERT_ACTIVITY);
            }
        });

        // 닫기 버튼 설정
        TitleBitmapButton closeBtn = (TitleBitmapButton)findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        itemCount = (TextView)findViewById(R.id.itemCount);

    }


    protected void onStart() {

        openDatabase();
        loadMemoListData();

        super.onStart();
    }


    /**
     * 데이터베이스 열기 (데이터베이스가 없을 때는 만들기)
     */
    public void openDatabase() {
        // open database
        if (mDatabase != null) {
            mDatabase.close();
            mDatabase = null;
        }

        mDatabase = MemoDatabase.getInstance(this);
        boolean isOpen = mDatabase.open();
        if (isOpen) {
            Log.d(TAG, "Memo database is open.");
        } else {
            Log.d(TAG, "Memo database is not open.");
        }
    }


    public int loadMemoListData() {
        String SQL = "select _id, INPUT_DATE, CONTENT_TEXT from MEMO order by INPUT_DATE desc";

        int recordCount = -1;
        if (MultiMemoActivity.mDatabase != null) {
            Cursor outCursor = MultiMemoActivity.mDatabase.rawQuery(SQL);

            recordCount = outCursor.getCount();
            Log.d(TAG, "cursor count : " + recordCount + "\n");

            mMemoListAdapter.clear();

            for (int i = 0; i < recordCount; i++) {
                outCursor.moveToNext();

                String memoId = outCursor.getString(0);

                String dateStr = outCursor.getString(1);
                if (dateStr != null && dateStr.length() > 10) {
                    try {
                        Date inDate = BasicInfo.dateFormat.parse(dateStr);

                        if (BasicInfo.language.equals("ko")) {
                            dateStr = BasicInfo.dateNameFormat2.format(inDate);
                        } else {
                            dateStr = BasicInfo.dateNameFormat3.format(inDate);
                        }
                    } catch(Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    dateStr = "";
                }

                String memoStr = outCursor.getString(2);

                mMemoListAdapter.addItem(new MemoListItem(memoId, dateStr, memoStr));
            }

            outCursor.close();

            mMemoListAdapter.notifyDataSetChanged();

            itemCount.setText(recordCount + " " + getResources().getString(R.string.item_count));
            itemCount.invalidate();
        }

        return recordCount;
    }



    private void viewMemo(int position) {
        MemoListItem item = (MemoListItem)mMemoListAdapter.getItem(position);

        Intent intent = new Intent(getApplicationContext(), MemoInsertActivity.class);
        intent.putExtra(BasicInfo.KEY_MEMO_MODE, BasicInfo.MODE_VIEW);
        intent.putExtra(BasicInfo.KEY_MEMO_ID, item.getId());
        intent.putExtra(BasicInfo.KEY_MEMO_DATE, item.getData(0));
        intent.putExtra(BasicInfo.KEY_MEMO_TEXT, item.getData(1));

        startActivityForResult(intent, BasicInfo.REQ_VIEW_ACTIVITY);
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case BasicInfo.REQ_INSERT_ACTIVITY:
                if(resultCode == RESULT_OK) {
                    loadMemoListData();
                }

                break;

            case BasicInfo.REQ_VIEW_ACTIVITY:
                loadMemoListData();
                break;
        }
    }
}