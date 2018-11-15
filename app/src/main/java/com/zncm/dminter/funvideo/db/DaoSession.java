package com.zncm.dminter.funvideo.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.zncm.dminter.funvideo.data.Live;
import com.zncm.dminter.funvideo.data.VideoModel;

import com.zncm.dminter.funvideo.db.LiveDao;
import com.zncm.dminter.funvideo.db.VideoModelDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig liveDaoConfig;
    private final DaoConfig videoModelDaoConfig;

    private final LiveDao liveDao;
    private final VideoModelDao videoModelDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        liveDaoConfig = daoConfigMap.get(LiveDao.class).clone();
        liveDaoConfig.initIdentityScope(type);

        videoModelDaoConfig = daoConfigMap.get(VideoModelDao.class).clone();
        videoModelDaoConfig.initIdentityScope(type);

        liveDao = new LiveDao(liveDaoConfig, this);
        videoModelDao = new VideoModelDao(videoModelDaoConfig, this);

        registerDao(Live.class, liveDao);
        registerDao(VideoModel.class, videoModelDao);
    }
    
    public void clear() {
        liveDaoConfig.clearIdentityScope();
        videoModelDaoConfig.clearIdentityScope();
    }

    public LiveDao getLiveDao() {
        return liveDao;
    }

    public VideoModelDao getVideoModelDao() {
        return videoModelDao;
    }

}