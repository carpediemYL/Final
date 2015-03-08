package com.example.jihyun.memo1;

import java.text.SimpleDateFormat;

public class BasicInfo {

    public static String language = "";


    /**
     * 외장 메모리 패스
     */
    public static String ExternalPath = "/mnt/sdcard/";

    /**
     * 외장 메모리 패스 체크 여부
     */
    public static boolean ExternalChecked = false;



    /**
     * 데이터베이스 이름
     */
    public static String DATABASE_NAME = "memo/memo.db";


    //========== 인텐트 부가정보 전달을 위한 키값 ==========//
    public static final String KEY_MEMO_MODE = "MEMO_MODE";
    public static final String KEY_MEMO_TEXT = "MEMO_TEXT";
    public static final String KEY_MEMO_ID = "MEMO_ID";
    public static final String KEY_MEMO_DATE = "MEMO_DATE";


    //========== 메모 모드 상수 ==========//
    public static final String MODE_INSERT = "MODE_INSERT";
    public static final String MODE_MODIFY = "MODE_MODIFY";
    public static final String MODE_VIEW = "MODE_VIEW";


    //========== 액티비티 요청 코드  ==========//
    public static final int REQ_VIEW_ACTIVITY = 1001;
    public static final int REQ_INSERT_ACTIVITY = 1002;


    //========== 날짜 포맷  ==========//
    public static SimpleDateFormat dateDayNameFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
    public static SimpleDateFormat dateDayFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat dateNameFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
    public static SimpleDateFormat dateNameFormat2 = new SimpleDateFormat("yyyy-MM-dd HH시 mm분");
    public static SimpleDateFormat dateNameFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static SimpleDateFormat dateTimeNameFormat = new SimpleDateFormat("HH시 mm분");
    public static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("HH:mm");

    //========== 대화상자 키값  ==========//
    public static final int WARNING_INSERT_SDCARD = 1001;
    public static final int CONFIRM_DELETE = 3001;
    public static final int CONFIRM_TEXT_INPUT = 3002;

}