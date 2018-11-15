package com.zncm.dminter.funvideo.data;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by jiaomx on 2017/7/20.
 */

public class Base implements Serializable {
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
