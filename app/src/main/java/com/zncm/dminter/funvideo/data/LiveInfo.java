package com.zncm.dminter.funvideo.data;

/**
 * Created by jiaomx on 2017/7/20.
 */

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * {
 {@mc用户109187|@tphttp://www.zuyiwa.cn/data/upload/avatar/59c88dcf88a40.png|@dzrtmp://rtmp.momojian.cn/taiyanglive/you5ykdxiu39rr1mdz6f7vf4rcuvh7eg|
 * }
 */
public class LiveInfo extends Base {
    private String name;
    private String img;
    private String url;

    public LiveInfo() {
    }

    public LiveInfo(String name, String img, String url) {
        this.name = name;
        this.img = img;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
