package com.example.delhivery.newsapp;

import android.app.Application;

import com.example.delhivery.newsapp.DB.DaoMaster;
import com.example.delhivery.newsapp.DB.DaoSession;

public class NewsApp extends Application {

    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mDaoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this,"greendao_demo.db").getWritableDb()).newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

}
