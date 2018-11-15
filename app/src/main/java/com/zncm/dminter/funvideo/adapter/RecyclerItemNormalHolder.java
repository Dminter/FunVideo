package com.zncm.dminter.funvideo.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.zncm.dminter.funvideo.R;
import com.zncm.dminter.funvideo.listener.SampleListener;
import com.zncm.dminter.funvideo.utils.Xutils;
import com.zncm.dminter.funvideo.data.VideoModel;
import com.zncm.dminter.funvideo.dbhelper.DbHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guoshuyu on 2017/1/9.
 */

public class RecyclerItemNormalHolder extends RecyclerItemBaseHolder {

    public final static String TAG = "RecyclerView2List";

    protected Context context = null;

    @BindView(R.id.video_item_player)
    StandardGSYVideoPlayer gsyVideoPlayer;

    ImageView imageView;

    public RecyclerItemNormalHolder(Context context, View v) {
        super(v);
        this.context = context;
        ButterKnife.bind(this, v);
        imageView = new ImageView(context);
    }

    public void onBind(final int position, final VideoModel videoModel) {

        //增加封面
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        if (position % 2 == 0) {
//            imageView.setImageResource(R.mipmap.xxx1);
//        } else {
//            imageView.setImageResource(R.mipmap.xxx2);
//        }
        if (imageView.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) imageView.getParent();
            viewGroup.removeView(imageView);
        }
        gsyVideoPlayer.setThumbImageView(imageView);

        String url = "";
        String title = "";
        String logo = "";
        if (videoModel != null) {
            if (Xutils.isNotEmptyOrNull(videoModel.getUrl())) {
                url = videoModel.getUrl();
            }
            if (Xutils.isNotEmptyOrNull(videoModel.getName())) {
                title = videoModel.getName();
            }
            if (Xutils.isNotEmptyOrNull(videoModel.getTag())) {
                logo = videoModel.getTag();
            }
        }

        if (Xutils.isNotEmptyOrNull(logo)) {
            Glide.with(context).load(logo).into(imageView);
        }


        gsyVideoPlayer.setUp(url, true, null, title);

        //增加title
        gsyVideoPlayer.getTitleTextView().setVisibility(View.VISIBLE);

        //设置返回键
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);

        //设置全屏按键功能
        gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolveFullBtn(gsyVideoPlayer);
            }
        });
        gsyVideoPlayer.setRotateViewAuto(false);
        gsyVideoPlayer.setLockLand(false);
        gsyVideoPlayer.setPlayTag(TAG);
        gsyVideoPlayer.setShowFullAnimation(true);
        //循环
        //gsyVideoPlayer.setLooping(true);
        gsyVideoPlayer.setNeedLockFull(true);

        //gsyVideoPlayer.setSpeed(2);

        gsyVideoPlayer.setPlayPosition(position);



        gsyVideoPlayer.getTitleTextView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Xutils.debug("setOnLongClickListener");
                final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog
                        .setTitle("删除节目")
                        .setMessage("是否删除节目？")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (videoModel.getId() == null) {
                                            return;
                                        }
                                        DbHelper.getVideoModelDao().delete(videoModel);
                                        Xutils.tShort("已删除");


                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                dialog.show();


                return true;
            }
        });
        gsyVideoPlayer.setStandardVideoAllCallBack(new SampleListener() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                if (!gsyVideoPlayer.isIfCurrentIsFullscreen()) {
                    //静音
                    GSYVideoManager.instance().setNeedMute(true);
                }

            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
                //全屏不静音
                GSYVideoManager.instance().setNeedMute(true);
            }

            @Override
            public void onEnterFullscreen(String url, Object... objects) {
                super.onEnterFullscreen(url, objects);
                GSYVideoManager.instance().setNeedMute(false);
            }
        });
    }

    /**
     * 全屏幕按键处理
     */
    private void resolveFullBtn(final StandardGSYVideoPlayer standardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen(context, true, true);
    }




}