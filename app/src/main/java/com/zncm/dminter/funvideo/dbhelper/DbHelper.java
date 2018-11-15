package com.zncm.dminter.funvideo.dbhelper;

import android.database.sqlite.SQLiteDatabase;

import com.zncm.dminter.funvideo.MyApp;
import com.zncm.dminter.funvideo.data.Live;
import com.zncm.dminter.funvideo.db.DaoMaster;
import com.zncm.dminter.funvideo.db.DaoSession;
import com.zncm.dminter.funvideo.db.LiveDao;
import com.zncm.dminter.funvideo.db.VideoModelDao;


/**
 * Created by jiaomx on 2017/7/25.
 */

public class DbHelper {
    private static VideoModelDao videoModelDao;
    private static LiveDao liveDao;
    private static DaoSession daoSession;

    public static DaoSession getDaoSession() {
        if (daoSession == null) {
            synchronized (DaoSession.class) {
                daoSession = initDaoSession();
            }
        }
        return daoSession;
    }

    public static VideoModelDao getVideoModelDao() {
        if (videoModelDao == null) {
            synchronized (VideoModelDao.class) {
                videoModelDao = getDaoSession().getVideoModelDao();
            }
        }
        return videoModelDao;
    }
    public static LiveDao getLiveDao() {
        if (liveDao == null) {
            synchronized (LiveDao.class) {
                liveDao = getDaoSession().getLiveDao();
            }
        }
        return liveDao;
    }

    private static DaoSession initDaoSession() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(MyApp.getInstance().ctx, "funvideo-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }

}
