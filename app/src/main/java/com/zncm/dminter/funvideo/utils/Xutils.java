package com.zncm.dminter.funvideo.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.widget.Toast;


import com.zncm.dminter.funvideo.MyApp;
import com.zncm.dminter.funvideo.R;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by jiaomx on 2017/7/20.
 */

public class Xutils {



    public static String getDateHM(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String dt = sdf.format(time);
        return dt;
    }
    public static void debug(Object object) {
        Log.e("QiqiBabyMusic", String.valueOf(object));
    }


    public static boolean isEmptyOrNull(String string) {

        return string == null || string.trim().length() == 0 || string.equals("null");


    }

    public static boolean isNotEmptyOrNull(String string) {
        return !isEmptyOrNull(string);

    }
    /**
     * tab 样式统一初始化
     */
    public static void initTabLayout(Context ctx, TabLayout mTabLayout) {
        mTabLayout.setBackgroundColor(ctx.getResources().getColor(R.color.colorPrimary));
        mTabLayout.setSelectedTabIndicatorColor(ctx.getResources().getColor(R.color.material_light_white));
        mTabLayout.setTabTextColors(ColorStateList.valueOf(ctx.getResources().getColor(R.color.material_light_white)));
    }

    public static void tShort(String msg) {
        if (isEmptyOrNull(msg)) {
            return;
        }
        Toast.makeText(MyApp.getInstance().ctx, msg, Toast.LENGTH_SHORT).show();
    }

    public static void tLong(String msg) {
        if (isEmptyOrNull(msg)) {
            return;
        }
        Toast.makeText(MyApp.getInstance().ctx, msg, Toast.LENGTH_LONG).show();
    }


    public static <T> boolean listNotNull(List<T> t) {
        if (t != null && t.size() > 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 100秒-》01:40
     */
    public static String secToTime(int time) {
        int hour = time / 3600;
        int minute = time / 60 % 60;
        int second = time % 60;
        StringBuffer sbInfo = new StringBuffer();
        if (hour > 0) {
            if (hour < 10) {
                sbInfo.append("0");
            }
            sbInfo.append(hour).append(":");
        }
        if (minute < 10) {
            sbInfo.append("0");
        }
        sbInfo.append(minute).append(":");
        if (second < 10) {
            sbInfo.append("0");
        }
        sbInfo.append(second);
        return sbInfo.toString();
    }

}
