package com.example.jihyun.memo1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.example.jihyun.memo1.common.TitleBackgroundButton;
import com.example.jihyun.memo1.common.TitleBitmapButton;

import java.util.Calendar;


public class MemoInsertActivity extends Activity {

	public static final String TAG = "MemoInsertActivity";


	EditText mMemoEdit;
	String mMemoMode;
	String mMemoId;
	String mMemoDate;
	String mDateStr;
	String mMemoStr;

	Calendar mCalendar = Calendar.getInstance();
	TitleBitmapButton insertDateButton;
	TitleBitmapButton insertTimeButton;

	int mSelectdContentArray;
	int mChoicedArrayItem;

	TitleBackgroundButton titleBackgroundBtn;
	TitleBitmapButton insertSaveBtn;
	TitleBitmapButton insertCancelBtn;
	TitleBitmapButton insert_textBtn;

	TitleBitmapButton deleteBtn;

	int textViewMode = 0;
	EditText insert_memoEdit;

	Animation translateLeftAnim;
	Animation translateRightAnim;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.memo_insert);

		titleBackgroundBtn = (TitleBackgroundButton)findViewById(R.id.titleBackgroundBtn);
    	mMemoEdit = (EditText) findViewById(R.id.insert_memoEdit);

    	insert_textBtn = (TitleBitmapButton)findViewById(R.id.insert_textBtn);

    	insert_memoEdit = (EditText)findViewById(R.id.insert_memoEdit);

    	deleteBtn = (TitleBitmapButton)findViewById(R.id.deleteBtn);



    	translateLeftAnim = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        translateRightAnim = AnimationUtils.loadAnimation(this, R.anim.translate_right);

        SlidingPageAnimationListener animListener = new SlidingPageAnimationListener();
        translateLeftAnim.setAnimationListener(animListener);
        translateRightAnim.setAnimationListener(animListener);

        insert_textBtn.setSelected(true);


    	insert_textBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (textViewMode == 1) {

					insert_memoEdit.setVisibility(View.VISIBLE);
					insert_memoEdit.startAnimation(translateLeftAnim);

					textViewMode = 0;
					insert_textBtn.setSelected(true);

				}
			}
		});


    	deleteBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog(BasicInfo.CONFIRM_DELETE);
			}
		});


		setBottomButtons();


		Intent intent = getIntent();
		mMemoMode = intent.getStringExtra(BasicInfo.KEY_MEMO_MODE);
		if(mMemoMode.equals(BasicInfo.MODE_MODIFY) || mMemoMode.equals(BasicInfo.MODE_VIEW)) {
			processIntent(intent);

			titleBackgroundBtn.setText(R.string.view_title);
			insertSaveBtn.setText(R.string.modify_btn);

			deleteBtn.setVisibility(View.VISIBLE);
		} else {
			titleBackgroundBtn.setText(R.string.new_title);
			insertSaveBtn.setText(R.string.save_btn);

			deleteBtn.setVisibility(View.GONE);
		}
	}

    private class SlidingPageAnimationListener implements AnimationListener {

		public void onAnimationEnd(Animation animation) {

		}

		public void onAnimationRepeat(Animation animation) {

		}

		public void onAnimationStart(Animation animation) {

		}

    }



	public void processIntent(Intent intent) {
		mMemoId = intent.getStringExtra(BasicInfo.KEY_MEMO_ID);
		mMemoDate = intent.getStringExtra(BasicInfo.KEY_MEMO_DATE);
		String curMemoText = intent.getStringExtra(BasicInfo.KEY_MEMO_TEXT);
		mMemoEdit.setText(curMemoText);

		if (curMemoText != null && !curMemoText.equals("")) {
			textViewMode  = 0;

			insert_memoEdit.setVisibility(View.VISIBLE);

			insert_textBtn.setSelected(true);

		} else {
			textViewMode  = 1;

			insert_memoEdit.setVisibility(View.GONE);

			insert_textBtn.setSelected(false);

		}

    }



    public void setBottomButtons() {
    	insertSaveBtn = (TitleBitmapButton)findViewById(R.id.insert_saveBtn);
    	insertCancelBtn = (TitleBitmapButton)findViewById(R.id.insert_cancelBtn);

    	// 저장 버튼
    	insertSaveBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				boolean isParsed = parseValues();
                if (isParsed) {
                	if(mMemoMode.equals(BasicInfo.MODE_INSERT)) {
                		//saveInput();
                	} else if(mMemoMode.equals(BasicInfo.MODE_MODIFY) || mMemoMode.equals(BasicInfo.MODE_VIEW)) {
                		//modifyInput();
                	}
                }
			}
		});

    	// 닫기 버튼
    	insertCancelBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}

    private boolean parseValues() {

    	mMemoStr = mMemoEdit.getText().toString();

	    if (mMemoStr.trim().length() < 1) {
	    	showDialog(BasicInfo.CONFIRM_TEXT_INPUT);
	    	return false;
	    }
    	return true;
    }


	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder = null;

		switch(id) {
			case BasicInfo.CONFIRM_TEXT_INPUT:
				builder = new AlertDialog.Builder(this);
				builder.setTitle(R.string.memo_title);
				builder.setMessage(R.string.text_input_message);
				builder.setPositiveButton(R.string.confirm_btn, new DialogInterface.OnClickListener() {
	        	    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

				break;

			case BasicInfo.CONFIRM_DELETE:
				builder = new AlertDialog.Builder(this);
				builder.setTitle(R.string.memo_title);
				builder.setMessage(R.string.memo_delete_question);
				builder.setPositiveButton(R.string.yes_btn, new DialogInterface.OnClickListener() {
	        	    public void onClick(DialogInterface dialog, int whichButton) {
	        	    	deleteMemo();
                    }
                });
				builder.setNegativeButton(R.string.no_btn, new DialogInterface.OnClickListener() {
		             public void onClick(DialogInterface dialog, int whichButton) {
		            	 dismissDialog(BasicInfo.CONFIRM_DELETE);
		             }
				});

				break;
			default:
				break;
		}
		return builder.create();
	}

    private void deleteMemo() {
    	setResult(RESULT_OK);
		finish();
    }
}
